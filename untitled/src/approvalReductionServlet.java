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

@WebServlet("/approvalReductionServlet")
public class approvalReductionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String[] array = new String[2];
        if (username_password == null) {
            array[0] = req.getParameter("user_name");
            array[1] = req.getParameter("pass_word");
        } else {
            array = username_password.split("&");
        }

        HashMap<String, List<String>> reductionDataMap = null;
        HashMap<String, List<String>> userDataMap = null;

        String reduction = req.getParameter("reduction");

        daoUtil daoUtilbo = null;
        try {
            daoUtilbo = new daoUtil();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (reduction != null) {
                if (reduction.equals("true")) {
                    String userId = req.getParameter("user_id");
                    String urlName = req.getParameter("url_name");
                    String company = req.getParameter("company");
                    if (daoUtilbo.reductionUrl(userId,urlName,company)) {
                        req.setAttribute("message_reduction_url", "还原成功！");
                    } else {
                        req.setAttribute("message_reduction_url", "还原失败！");
                    }
                }
            }
            reductionDataMap = daoUtilbo.getReductionData();
            userDataMap = daoUtilbo.getUserDataMap();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("back_managerServlet", "随便写什么，只是为了检测是否已经走过这个Servlet");
        req.setAttribute("reduction_data_map", reductionDataMap);
        req.setAttribute("user_data_map", userDataMap);
        req.setAttribute("user_name", array[0]);
        req.setAttribute("pass_word", array[1]);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("approval_reduction.jsp");//通过request获取转发器，转发请求到manager.jsp页面
        requestDispatcher.forward(req, resp);//将数据传给manager.jsp
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
