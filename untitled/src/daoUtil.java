import java.sql.*;

public class daoUtil {
    public Connection daoConnect() throws ClassNotFoundException, SQLException {
        String driver = "com.mysql.jdbc.Driver";
        /*这个链接包含主机，库名“newcapec“，其他乱七八糟的都是模版*/
        String url = "jdbc:mysql://localhost:3306/pricemonitor?sueSSL=false";

        String user = "root"; //用户名root，一般都是这个
        String password = "1234"; //自己数据库密码
        Class.forName(driver); //这上面可以封装起来一个方法
        return (Connection) DriverManager.getConnection(url, user, password);//连接数据库
    }
}
