import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class daoUtil {
    private Connection connection;
    private Statement statement;

    protected daoUtil() throws SQLException, ClassNotFoundException {
        connection = Connect();
        statement = connection.createStatement();
    }

    //连接数据库
    public Connection Connect() throws ClassNotFoundException, SQLException {
        // JDBC 驱动名及数据库 URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/pricemonitor";

        // 数据库的用户名与密码，需要根据自己的设置
        String USER = "root";
        String PASS = "1234";

        Connection conn = null;

        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return conn;
    }

    //登录检测用户名和密码是否正确
    public int CheckLogin(String user_name, String pass_word) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from user where name = \"" + user_name + "\" and pass_word = \"" + pass_word + "\"" + " limit 1";
        ResultSet result = statement.executeQuery(sql);
        if (!result.next()) {
            return -1;    //用户信息错误或不存在
        } else {
            return result.getInt("role");    //用户信息存在且正确，返回role列的值，用于判断是“管理员”还是“用户”
        }
    }

    //检测指定表中指定行是否已存在
    public Boolean IsExist(String table_name, String column_name, String column_data) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from " + table_name + " where " + column_name + " = \"" + column_data + "\" limit 1";
        ResultSet result = statement.executeQuery(sql);

        if (!result.next()) {
            return false;    //不存在
        } else {
            return true;    //存在
        }
    }

    //查找指定表中column_name列的值为column_data的行，返回该行中列名为find_column_name的值
    public String GetColumnData(String table_name, String column_name, String column_data, String find_column_name) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from " + table_name + " where " + column_name + " = \"" + column_data + "\" limit 1";
        ResultSet result = statement.executeQuery(sql);

        if (result.next()) {
            String find_column_data = result.getString(find_column_name);
            return find_column_data;
        } else {
            return null;
        }
    }

    //注册用户信息
    public Boolean InsertUser(String user_name, String pass_word) throws SQLException, ServletException, IOException {
        if (IsExist("user", "name", user_name)) {
            return false;
        }
        // 执行插入
        String sql;
        LocalDateTime time = LocalDateTime.now();
        sql = "insert into user (name,pass_word,create_time)values (\"" + user_name + "\",\"" + pass_word + "\",\"" + time + "\")";
        int result = statement.executeUpdate(sql);

        if (result > 0) {   //插入成功，开始注册对应的monitor
            String re = GetColumnData("user", "name", user_name, "id");
            int user_id = Integer.parseInt(re);
            sql = "insert into monitor (user_id,create_time) values (\"" + user_id + "\",\"" + time + "\")";
            result = statement.executeUpdate(sql);
            if (result > 0) { //注册monitor成功
                return true;
            } else {
                return false;
            }
        } else {
            return false;   //插入失败
        }
    }

    //注册url信息
    public Boolean InsertUrl(String user_id, String company, String url_name, String url) throws SQLException, ServletException, IOException {
        if (IsExist("tb_url", "url", url)) {
            return false;
        }
        // 执行插入
        String sql;
        LocalDateTime time = LocalDateTime.now();
        sql = "insert into tb_url (user_id,url_name,company,url,create_time) values (" + user_id + ",\"" + url_name + "\"," + company + ",\"" + url + "\",\"" + time + "\")";
        int result = statement.executeUpdate(sql);

        if (result > 0) {
            return true;   //插入成功
        } else {
            return false;    //插入失败
        }
    }

    //获取指定user_id对应的url的url_name、company、url
    protected HashMap<String, List<String>> getUrlData(String user_id) throws SQLException, ServletException, IOException {

        HashMap<String, List<String>> urlDataMap = new HashMap<>();
        List<String> urlNameList = new LinkedList<>();
        List<String> companyList = new LinkedList<>();
        List<String> urlList = new LinkedList<>();
        String sql_dataList = "select * from tb_url where user_id = \"" + user_id + "\"";
        ResultSet resultSet = statement.executeQuery(sql_dataList);
        while (resultSet.next()) {
            urlNameList.add(resultSet.getString("url_name"));
            companyList.add(resultSet.getString("company"));
            urlList.add(resultSet.getString("url"));
        }
        urlDataMap.put("url_name", urlNameList);
        urlDataMap.put("company", companyList);
        urlDataMap.put("url", urlList);
        return urlDataMap;
    }

    //获取指定url的type（价格或销量）和日期
    protected HashMap<String, String> getUrlDataMap(String url_id, String type) throws SQLException, ServletException, IOException {

        HashMap<String, String> urlMap = new HashMap<>();
        String table_name = type + "data";
        String sql_dataList = "select * from " + table_name + " where url_id = \"" + url_id + "\"";
        ResultSet resultSet = statement.executeQuery(sql_dataList);
        while (resultSet.next()) {
            urlMap.put(resultSet.getString(type), resultSet.getString("date"));
        }
        return urlMap;
    }

    //删除指定url
    protected Boolean deleteUrl(String url) throws SQLException, ServletException, IOException {

        String sql_dataList = "delete  from tb_url where url = \"" + url + "\"";
        int resultSet = statement.executeUpdate(sql_dataList);
        if (resultSet > 0) {
            return true;
        }
        return false;
    }

    //获取指定url_name对应的的company、price、date
    protected HashMap<String, HashMap<String, String>> getPriceData(String url_name) throws SQLException, ServletException, IOException {
        HashMap<String, HashMap<String, String>> dataMap = new HashMap<>();
        List<String> priceList = new LinkedList<>();
        List<String> companyList = new LinkedList<>();
        List<String> dateList = new LinkedList<>();
        HashMap<String, String> map_0 = new HashMap<>();
        HashMap<String, String> map_1 = new HashMap<>();
        HashMap<String, String> map_2 = new HashMap<>();
        HashMap<String, String> map_3 = new HashMap<>();

        String sql_dataList = "select * from tb_price where url_name =\"" + url_name + "\"";
        ResultSet resultSet = statement.executeQuery(sql_dataList);
        while (resultSet.next()) {
            priceList.add(resultSet.getString("price"));
            companyList.add(resultSet.getString("company"));
            dateList.add(resultSet.getString("date"));
        }
        for (int i = 0; i < companyList.size(); ++i) {
            switch (companyList.get(i)) {
                case "0":
                    map_0.put(dateList.get(i), priceList.get(i));
                    break;
                case "1":
                    map_1.put(dateList.get(i), priceList.get(i));
                    break;
                case "2":
                    map_2.put(dateList.get(i), priceList.get(i));
                    break;
                case "3":
                    map_3.put(dateList.get(i), priceList.get(i));
                    break;
                default:
                    break;
            }
        }
        dataMap.put("0", map_0);
        dataMap.put("1", map_1);
        dataMap.put("2", map_2);
        dataMap.put("3", map_3);
        return dataMap;
    }

}
