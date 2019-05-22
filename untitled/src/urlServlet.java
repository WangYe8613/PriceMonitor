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

@WebServlet("/urlServlet")
public class urlServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String[] array = new String[2];
        String url = null;
        if (username_password == null) {
            array[0] = req.getParameter("user_name");
            array[1] = req.getParameter("pass_word");
        } else {
            array = username_password.split("&");
            url = req.getParameter("url");
        }
        HashMap<String, List<String>> urlData = null;
        String userId = null;
        String path = null;

        daoUtil daoUtilbo = null;
        try {
            daoUtilbo = new daoUtil();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (url == null) {
                userId = req.getParameter("user_id");
                path = "url.jsp";
            } else {
                userId = req.getParameter("user_id");
                String company = req.getParameter("company");
                String urlName = new String(req.getParameter("url_name").getBytes("iso-8859-1"), "utf-8");

                String isExist = daoUtilbo.IsExist("tb_url", "url", url);
                if (isExist != null && isExist.equals("1")) {
                    req.setAttribute("message_url", "该url曾经添加过，但已删除，如需还原请申请还原数据！");
                } else {
                    if (daoUtilbo.InsertUrl(userId, company, urlName, url)) {
                        req.setAttribute("message_url", "添加成功！");
                    } else {
                        req.setAttribute("message_url", "添加失败,链接已存在！");
                    }
                }
                path = "user.jsp";
            }
            urlData = daoUtilbo.getUrlData(userId, "0");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //req.setAttribute("back", "随便写什么，只是为了检测是否已经走过这个Servlet");
        req.setAttribute("url_data", urlData);
        req.setAttribute("user_name", array[0]);
        req.setAttribute("pass_word", array[1]);
        req.setAttribute("user_id", userId);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher(path);//通过request获取转发器，转发请求到path页面
        requestDispatcher.forward(req, resp);//将数据传给path
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
