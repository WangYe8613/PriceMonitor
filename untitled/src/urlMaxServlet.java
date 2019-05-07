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
import java.util.HashMap;
import java.util.List;

@WebServlet("/urlMaxServlet")
public class urlMaxServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String userId = userId = req.getParameter("user_id");
        String[] array = new String[2];
        String urlMax = null;
        if (username_password == null) {
            array[0] = req.getParameter("user_name");
            array[1] = req.getParameter("pass_word");
        } else {
            array = username_password.split("&");
            urlMax = req.getParameter("url_max");
        }
        String oldUrlMax = null;

        daoUtil daoUtilbo = null;
        try {
            daoUtilbo = new daoUtil();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            oldUrlMax = daoUtilbo.GetColumnData("tb_monitor", "user_id", userId, "url_max");

            if (urlMax != null) {
                if (daoUtilbo.updateUrlMaxApplication(userId, urlMax)) {
                    req.setAttribute("message_url_max", "申请已提交！");
                } else {
                    req.setAttribute("message_url_max", "申请失败！");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //req.setAttribute("back", "随便写什么，只是为了检测是否已经走过这个Servlet");
        req.setAttribute("user_name", array[0]);
        req.setAttribute("pass_word", array[1]);
        req.setAttribute("user_id", userId);
        req.setAttribute("old_url_max", oldUrlMax);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("url_max.jsp");//通过request获取转发器，转发请求到url_max.jsp页面
        requestDispatcher.forward(req, resp);//将数据传给url_max.jsp
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
