import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class daoUtil {
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
    public int CheckLogin(Connection conn, Statement stmt, String user_name, String pass_word) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from user where name = \"" + user_name + "\" and pass_word = \"" + pass_word + "\"" + " limit 1";
        ResultSet result = stmt.executeQuery(sql);
        if (!result.next()) {
            return -1;    //用户信息错误或不存在
        } else {
            return result.getInt("role");    //用户信息存在且正确，返回role列的值，用于判断是“管理员”还是“用户”
        }
    }

    //检测指定表中指定行是否已存在
    public Boolean IsExist(Connection conn, Statement stmt, String table_name, String column_name, String column_data) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from " + table_name + " where " + column_name + " = \"" + column_data + "\" limit 1";
        ResultSet result = stmt.executeQuery(sql);

        if (!result.next()) {
            return false;    //不存在
        } else {
            return true;    //存在
        }
    }

    //查找指定表中column_name列的值为column_data的行，返回该行中列名为find_column_name的值
    public String GetColumnData(Connection conn, Statement stmt, String table_name, String column_name, String column_data, String find_column_name) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from " + table_name + " where " + column_name + " = \"" + column_data + "\" limit 1";
        ResultSet result = stmt.executeQuery(sql);

        if (result.next()) {
            String find_column_data = result.getString(find_column_name);
            return find_column_data;
        } else {
            return null;
        }
    }

    //注册用户信息
    public Boolean InsertUser(Connection conn, Statement stmt, String user_name, String pass_word) throws SQLException, ServletException, IOException {
        if (IsExist(conn, stmt, "user", "name", user_name)) {
            return false;
        }
        // 执行插入
        String sql;
        LocalDateTime time = LocalDateTime.now();
        sql = "insert into user (name,pass_word,create_time)values (\"" + user_name + "\",\"" + pass_word + "\",\"" + time + "\")";
        int result = stmt.executeUpdate(sql);

        if (result > 0) {   //插入成功，开始注册对应的monitor
            String re = GetColumnData(conn, stmt, "user", "name", user_name, "id");
            int user_id = Integer.parseInt(re);
            sql = "insert into monitor (user_id,create_time) values (\"" + user_id + "\",\"" + time + "\")";
            result = stmt.executeUpdate(sql);
            if (result > 0) { //注册monitor成功
                return true;
            } else {
                return false;
            }
        } else {
            return false;   //插入失败
        }
    }

    public Boolean InsertUrl(Connection conn, Statement stmt, String user_id, String url) throws SQLException, ServletException, IOException {
        if (IsExist(conn, stmt, "url", "url", url)) {
            return false;
        }
        // 执行插入
        String sql;
        LocalDateTime time = LocalDateTime.now();
        sql = "insert into url (user_id,url,create_time) values (" + user_id + ",\"" + url + "\",\"" + time + "\")";
        int result = stmt.executeUpdate(sql);

        if (result > 0) {
            return true;   //插入失败
        } else {
            return false;    //插入成功
        }
    }
}
