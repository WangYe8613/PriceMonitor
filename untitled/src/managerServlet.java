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

@WebServlet("/managerServlet")
public class managerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String[] array = new String[2];
        if (username_password == null) {
            array[0] = req.getAttribute("user_name").toString();
            array[1] = req.getAttribute("pass_word").toString();
        } else {
            array = username_password.split("&");
        }
        HashMap<String, List<String>> userDataMap = null;

        String operation = req.getParameter("operation");
        if (operation != null) {
            operation = new String(operation.getBytes("iso-8859-1"), "utf-8");
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
            if (operation != null) {
                if (operation.equals("删 除")) {
                    String userId = req.getParameter("id");
                    if (daoUtilbo.deleteUser(userId)) {
                        req.setAttribute("message_delete_user", "删除成功！");
                    } else {
                        req.setAttribute("message_delete_user", "删除失败！");
                    }
                } else if (operation.equals("提 升")) {
                    String userId = req.getParameter("id");
                    String urlMaxApplication = req.getParameter("url_max_application");
                    if (daoUtilbo.updateUrlMax(userId,urlMaxApplication)) {
                        req.setAttribute("message_update_monitor", "删除成功！");
                    } else {
                        req.setAttribute("message_update_monitor", "删除失败！");
                    }
                }
            }
            userDataMap = daoUtilbo.getUserDataMap();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("back_managerServlet", "随便写什么，只是为了检测是否已经走过这个Servlet");
        req.setAttribute("user_data_map", userDataMap);
        req.setAttribute("user_name", array[0]);
        req.setAttribute("pass_word", array[1]);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("manager.jsp");//通过request获取转发器，转发请求到manager.jsp页面
        requestDispatcher.forward(req, resp);//将数据传给manager.jsp
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
