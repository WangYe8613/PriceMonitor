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
    public Boolean CheckLogin(Connection conn,Statement stmt,String user_name,String pass_word) throws SQLException, ServletException, IOException {
        // 执行查询
        String sql;
        sql = "select * from user where name = \"" + user_name + "\" and pass_word = \"" + pass_word + "\""+" limit 1";
        ResultSet result = stmt.executeQuery(sql);

        if (!result.next()) {
           return false;    //用户信息错误或不存在
        } else {
            return true;    //用户信息存在且正确
        }
    }

    public Boolean Insert(Connection conn,Statement stmt,String user_name,String pass_word) throws SQLException, ServletException, IOException {
        if(CheckLogin(conn,stmt,user_name,pass_word)){
            return false;
        }
        // 执行插入
        String sql;
        LocalDateTime time = LocalDateTime.now();
        sql = "insert into user (name,pass_word,create_time)values (\""+user_name+"\",\""+pass_word+"\",\""+time+"\")";
        int result = stmt.executeUpdate(sql);

        if (result > 0) {
            return true;    //插入成功
        } else {
            return false;   //插入失败

        }
    }
//    public Boolean Insert(Connection conn,Statement stmt,String user_name,String pass_word) throws SQLException, ServletException, IOException {
//        // 执行插入
//        String sql;
//        LocalDateTime time = LocalDateTime.now();
//        sql = "insert into user values (\""+user_name+"\",\""+pass_word+"\",\""+time+"\")";
//        ResultSet result = stmt.executeQuery(sql);
//
//        if (!result.next()) {
//            return false;   //插入失败
//        } else {
//            return true;    //插入成功
//        }
//    }
}
