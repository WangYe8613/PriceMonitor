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

@WebServlet("/urlReductionServlet")
public class urlReductionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String userId = req.getParameter("user_id");
        String[] array = new String[2];

        if (username_password == null) {
            array[0] = req.getParameter("user_name");
            array[1] = req.getParameter("pass_word");
        } else {
            array = username_password.split("&");
        }

        HashMap<String, List<String>> deleteUrlData = null;
        List<String> urlNameList = null;

        String urlName = null;
        String company = null;

        String deleteUrl = req.getParameter("reduction_url");
        if (deleteUrl != null && deleteUrl.equals("true")) {
            urlName = req.getParameter("url_name");
            company = req.getParameter("company");
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
            if (deleteUrl != null && deleteUrl.equals("true")) {
                if (daoUtilbo.insertReduction(userId,urlName,company,array[0])) {
                    req.setAttribute("message_reduction_url", "还原成功！");
                } else {
                    req.setAttribute("message_reduction_url", "还原失败！");
                }
            }
            userId = daoUtilbo.GetColumnData("tb_user", "name", array[0], "id");
            deleteUrlData = daoUtilbo.getUrlData(userId,"1");
            urlNameList=daoUtilbo.getUrlNameList(userId);

        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        req.setAttribute("back_urlReductionServlet", "随便写什么，只是为了检测是否已经走过这个Servlet");
        req.setAttribute("delete_url_data", deleteUrlData);
        req.setAttribute("url_name_list", urlNameList);
        req.setAttribute("user_name", array[0]);
        req.setAttribute("pass_word", array[1]);
        req.setAttribute("user_id", userId);
        //req.setAttribute("role", role);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("urlReduction.jsp");//通过request获取转发器，转发请求到urlReduction.jsp页面
        requestDispatcher.forward(req, resp);//将数据传给urlReduction.jsp
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
