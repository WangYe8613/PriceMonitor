import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@WebServlet("/userServlet")
public class userServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String[] array = username_password.split("&");
        String url = req.getParameter("url");
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
            String user_id = daoUtilbo.GetColumnData(connection, stmt, "user", "name", array[0], "id");
            if (daoUtilbo.InsertUrl(connection, stmt, user_id, url)) {
                req.setAttribute("message_url", "添加成功！");
            } else {
                req.setAttribute("message_url", "添加失败,链接已存在！");
            }
            req.setAttribute("user_name", array[0]);
            req.setAttribute("pass_word", array[1]);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("user.jsp");//通过request获取转发器，转发请求到user.jsp页面
            requestDispatcher.forward(req, resp);//将数据传给user.jsp
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
        doGet(req, resp);
    }
}
