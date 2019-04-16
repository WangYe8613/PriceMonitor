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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/userServlet")
public class userServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String[] array = new String[2];
        String url = null;
        if (username_password == null) {
            array[0] = req.getAttribute("user_name").toString();
            array[1] = req.getAttribute("pass_word").toString();
        } else {
            array = username_password.split("&");
        }
        HashMap<String, List<String>> urlData = null;

        String userId = null;

        String deleteUrl = req.getParameter("delete_url");
        if (deleteUrl != null && deleteUrl.equals("true")) {
            url = req.getParameter("url");
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
            if (url != null) {
                if (daoUtilbo.deleteUrl(url)) {
                    req.setAttribute("message_url", "删除成功！");
                } else {
                    req.setAttribute("message_url", "删除失败！");
                }
            }
            userId = daoUtilbo.GetColumnData("user", "name", array[0], "id");
            urlData = daoUtilbo.getUrlData(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("back", "随便写什么，只是为了检测是否已经走过这个Servlet");
        req.setAttribute("url_data", urlData);
        req.setAttribute("user_name", array[0]);
        req.setAttribute("pass_word", array[1]);
        req.setAttribute("user_id", userId);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("user.jsp");//通过request获取转发器，转发请求到user.jsp页面
        requestDispatcher.forward(req, resp);//将数据传给user.jsp
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
