import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/dataServlet")
public class dataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId=req.getParameter("user_id");
        String username_password = req.getParameter("username_password");
        String[] array = new String[2];
        if (username_password != null) {
            array = username_password.split("&");
        }
        String urlName = new String(req.getParameter("url_name").getBytes("iso-8859-1"), "utf-8");
        daoUtil daoUtilbo = null;

        try {
            daoUtilbo = new daoUtil();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            HashMap<String, HashMap<String,String>> priceDataMap = daoUtilbo.getPriceData(urlName);
            HashMap<String, List<String>> urlData=daoUtilbo.getUrlData(userId,"0");
            req.setAttribute("price_data_map", priceDataMap);
            req.setAttribute("user_id", userId);
            req.setAttribute("url_data", urlData);
            req.setAttribute("url_name", urlName);
            req.setAttribute("user_name", array[0]);
            req.setAttribute("pass_word", array[1]);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("data.jsp");//通过request获取转发器，转发请求到data.jsp页面
            requestDispatcher.forward(req, resp);//将数据传给data.jsp
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
