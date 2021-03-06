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
        if (user_name == null || pass_word == null) {
            return;
        }

        daoUtil daoUtilbo = null;
        try {
            daoUtilbo = new daoUtil();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (daoUtilbo.InsertUser(user_name, pass_word)) {
                req.setAttribute("message_register", "注册成功，请登录！");
            } else {
                req.setAttribute("message_register", "注册用户已存在！");
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("index.jsp");//通过request获取转发器，转发请求到index.jsp页面
            requestDispatcher.forward(req, resp);//将数据传给index.jsp
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().append("888888");
    }
}
