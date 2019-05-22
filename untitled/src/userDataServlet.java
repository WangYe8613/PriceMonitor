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

@WebServlet("/userDataServlet")
public class userDataServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username_password = req.getParameter("username_password");
        String[] array = new String[2];
        if (username_password != null) {
            array = username_password.split("&");
        }
        //String name = new String(.getBytes("iso-8859-1"), "utf-8");

        daoUtil daoUtilbo = null;

        try {
            daoUtilbo = new daoUtil();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            String userId=daoUtilbo.GetColumnData("tb_user","name",req.getParameter("name"),"id");
            String role=daoUtilbo.GetColumnData("tb_user","name",req.getParameter("name"),"role");
            //String urlName=daoUtilbo.GetColumnData("tb_url","user_id",userId,"url_name");

            //HashMap<String, HashMap<String,String>> priceDataMap = daoUtilbo.getPriceData(urlName);
            HashMap<String, List<String>> urlData=daoUtilbo.getUrlData(userId,"0");
            HashMap<String, List<String>> userDataMap = daoUtilbo.getUserDataMap();

            //req.setAttribute("price_data_map", priceDataMap);
            //req.setAttribute("back", "随便写什么，只是为了检测是否已经走过这个Servlet");
            req.setAttribute("url_data", urlData);
            req.setAttribute("user_data_map", userDataMap);

            req.setAttribute("role", role);
            //req.setAttribute("url_name", urlName);
            req.setAttribute("user_name", array[0]);
            req.setAttribute("pass_word", array[1]);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("user_data.jsp");//通过request获取转发器，转发请求到user_data.jsp页面
            requestDispatcher.forward(req, resp);//将数据传给user_data.jsp
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
