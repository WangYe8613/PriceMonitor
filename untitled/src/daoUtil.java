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
        sql = "select * from tb_user where name = \"" + user_name + "\" and pass_word = \"" + pass_word + "\"" + " limit 1";
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
        if (IsExist("tb_user", "name", user_name)) {
            return false;
        }
        // 执行插入
        String sql;
        LocalDateTime time = LocalDateTime.now();
        sql = "insert into tb_user (name,pass_word,create_time)values (\"" + user_name + "\",\"" + pass_word + "\",\"" + time + "\")";
        int result = statement.executeUpdate(sql);

        if (result > 0) {   //插入成功，开始注册对应的monitor
            String re = GetColumnData("tb_user", "name", user_name, "id");
            int user_id = Integer.parseInt(re);
            sql = "insert into tb_monitor (user_id,create_time) values (\"" + user_id + "\",\"" + time + "\")";
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

    //删除指定url，附带着把该url对应的price也删除掉
    protected Boolean deleteUrl(String url) throws SQLException, ServletException, IOException {

        String sql_selectUrl = "select * from tb_url where url = \"" + url + "\"";
        ResultSet result = statement.executeQuery(sql_selectUrl);

        String url_name = null;
        String company = null;
        if (result.next()) {
            url_name = result.getString("url_name");
            company = result.getString("company");
        }
        String sql_deleteUrl = "delete  from tb_url where url = \"" + url + "\"";
        int resultSet = statement.executeUpdate(sql_deleteUrl);
        if (resultSet > 0) {
            String sql_deletePrice = "delete  from tb_price where url_name = \"" + url_name + "\" and company = " + company;
            resultSet = statement.executeUpdate(sql_deletePrice);
            if (resultSet > 0) {
                return true;
            }
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

    //申请提升Url添加数量上限，实际就是修改monitor中的url_max_application字段
    protected Boolean updateUrlMaxApplication(String user_id, String newUrlMaxApplication) throws SQLException, ServletException, IOException {

        String sql_selectMonitor = "select * from tb_monitor where user_id = " + user_id;
        ResultSet resultSet = statement.executeQuery(sql_selectMonitor);
        String oldUrlMaxApplication = null;
        if (resultSet.next()) {
            oldUrlMaxApplication = resultSet.getString("url_max_application");
        }
        String sql_updateUrlMax = "update tb_monitor set url_max_application = " + newUrlMaxApplication + " where url_max_application = " + oldUrlMaxApplication +" and user_id ="+user_id;
        int result = statement.executeUpdate(sql_updateUrlMax);
        if (result > 0) {
            return true;
        }
        return false;
    }

    //提升Url添加数量上限，实际就是修改monitor中的url_max字段，并将url_max_application重新置为0
    protected Boolean updateUrlMax(String user_id, String newUrlMax) throws SQLException, ServletException, IOException {

        String sql_selectMonitor = "select * from tb_monitor where user_id = " + user_id;
        ResultSet resultSet = statement.executeQuery(sql_selectMonitor);
        String oldUrlMax = null;
        String oldUrlMaxApplication = null;
        if (resultSet.next()) {
            oldUrlMax = resultSet.getString("url_max");
            oldUrlMaxApplication = resultSet.getString("url_max_application");
        }
        String sql_updateUrlMax = "update tb_monitor set url_max= " + newUrlMax + " where url_max = " + oldUrlMax;
        int result = statement.executeUpdate(sql_updateUrlMax);
        if (result > 0) {
            String sql_updateUrlMaxApplication = "update tb_monitor set url_max_application = 0 where url_max_application = " + oldUrlMaxApplication;
            result = statement.executeUpdate(sql_updateUrlMaxApplication);
            if (result > 0) {
                return true;
            }
        }
        return false;
    }

    //获取user信息，包括id、name、pass_word、url_max、url_max_application
    protected HashMap<String, List<String>> getUserDataMap() throws SQLException, ServletException, IOException {

        HashMap<String, List<String>> userDataMap = new HashMap<>();
        List<String> nameList = new LinkedList<>();
        List<String> idList = new LinkedList<>();
        List<String> passWordList = new LinkedList<>();
        List<String> urlMaxList = new LinkedList<>();
        List<String> urlMaxApplicationList = new LinkedList<>();

        String sql_userList = "select * from tb_user where role = 1";
        ResultSet resultSet = statement.executeQuery(sql_userList);
        while (resultSet.next()) {
            idList.add(resultSet.getString("id"));
            nameList.add(resultSet.getString("name"));
            passWordList.add(resultSet.getString("pass_word"));
        }
        for (String id : idList) {
            String sql_monitor = "select * from tb_monitor where user_id = " + id + " limit 1";
            resultSet = statement.executeQuery(sql_monitor);
            while (resultSet.next()) {
                urlMaxList.add(resultSet.getString("url_max"));
                urlMaxApplicationList.add(resultSet.getString("url_max_application"));
            }
        }
        userDataMap.put("id", idList);
        userDataMap.put("name", nameList);
        userDataMap.put("pass_word", passWordList);
        userDataMap.put("url_max", urlMaxList);
        userDataMap.put("url_max_application", urlMaxApplicationList);

        return userDataMap;
    }

    //删除指定user，附带着把该user_id对应的monitor、url、price也删除掉
    protected Boolean deleteUser(String user_id) throws SQLException, ServletException, IOException {

        String sql_deleteUser = "delete  from tb_user where id = \"" + user_id + "\"";
        int resultSet = statement.executeUpdate(sql_deleteUser);
        if (resultSet > 0) {
            String sql_deleteMonitor = "delete  from tb_monitor where user_id = \"" + user_id + "\"";
            resultSet = statement.executeUpdate(sql_deleteMonitor);
            if (resultSet > 0) {
                return true;
            }
        }
        return false;
    }

}
