import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user_name = req.getParameter("username_register");
        String pass_word = req.getParameter("password_register");
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
            if(!daoUtilbo.Insert(connection, stmt,user_name, pass_word)){
                req.setAttribute("message_register", "注册用户已存在！");
            }
            else {
                req.setAttribute("message_register", "注册成功，请登录！");
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");//通过request获取转发器，转发请求到index.jsp页面
            requestDispatcher.forward(req, resp);//将数据传给index.jsp
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("888888");
    }
}
