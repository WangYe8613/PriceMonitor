import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.*;
import java.sql.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // JDBC 驱动名及数据库 URL
        String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        String DB_URL = "jdbc:mysql://localhost:3306/pricemonitor";

        // 数据库的用户名与密码，需要根据自己的设置
        String USER = "root";
        String PASS = "1234";

        Connection conn = null;
        Statement stmt = null;

        String user_name = req.getParameter("username");
        String pass_word = req.getParameter("password");

        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // 执行查询
            stmt = conn.createStatement();
            String sql;
            sql = "select * from user where name = \"" + user_name + "\" and pass_wrod = \"" + pass_word + "\"";
            ResultSet result = stmt.executeQuery(sql);
            if (result != null) {
                //跳转到价格数据分析页面
            } else {
                //返回登录界面，并提示“用户名或密码不正确”
                HttpServletRequest request = null;
                HttpServletResponse response = null;
                request.setAttribute("msg", "用户名或密码不正确");
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");//通过request获取转发器，转发请求到index.jsp页面
                requestDispatcher.forward(request, response);//将数据传给index.jsp
            }

        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException se2) {
            }// 什么都不做
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }
}
