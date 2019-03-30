import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_name = req.getParameter("username_login");
        String pass_word = req.getParameter("password_login");
        daoUtil daoUtilbo = new daoUtil();
        Connection connection = null;
        try {
            connection = daoUtilbo.Connect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if(!daoUtilbo.CheckLogin(connection, stmt,user_name, pass_word)){
                req.setAttribute("message_login", "用户名或密码不正确");
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");//通过request获取转发器，转发请求到index.jsp页面
                requestDispatcher.forward(req, resp);//将数据传给index.jsp
            }
            else {
                req.setAttribute("user_name", user_name);
                req.setAttribute("pass_word", pass_word);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("user.jsp");//通过request获取转发器，转发请求到user.jsp页面
                requestDispatcher.forward(req, resp);//将数据传给user.jsp
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 关闭资源
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException se2) {
        }// 什么都不做
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(req, resp);
    }
}
