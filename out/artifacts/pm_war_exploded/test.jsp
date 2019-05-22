<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/3/27
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="materialize/css/materialize.min.css" media="screen,projection"/>
    <!-- Bootstrap Styles-->
    <link href="css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="css/font-awesome.css" rel="stylesheet"/>
    <!-- Morris Chart Styles-->
    <link href="js/morris/morris-0.4.3.min.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="css/custom-styles.css" rel="stylesheet"/>

    <link rel="stylesheet" href="/s/Lightweight-Chart/cssCharts.css">

    <title>管理员界面</title>

    <!-- 表格部分 -->
    <style type="text/css">
        table {
            border-left: 1px solid #000;
            border-top: 1px solid #000;
            text-align: center;
        }

        table tr td {
            border-right: 1px solid #000;
            border-bottom: 1px solid #000;
        }
    </style>

</head>

<body>
<!-- 检测是否通过正确登录方式访问该页面 -->
<%
    Object userName = request.getAttribute("user_name");
    Object passWord = request.getAttribute("pass_word");
    if (userName == null || passWord == null) {
        response.sendRedirect("401.html");
    }

    Object back = request.getAttribute("back");
    if (back == null) {
        request.setAttribute("user_name", userName);
        request.setAttribute("pass_word", passWord);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("userDataServlet");
        requestDispatcher.forward(request, response); //如果back为空，则去userServlet获取数据，即保证先从userServlet走到user.jsp
    }

    String username_password = userName.toString() + "&" + passWord;
    //Object userId = request.getAttribute("user_id");
%>



<!-- jsp通过jQuery提交表单给Servlet -->
<script>
    function jqSubmit() {
        $("#to_userDataServlet").submit();
    }
</script>

<!-- jQuery Js -->
<script src="js/jquery-1.10.2.js"></script>

<!-- Bootstrap Js -->
<script src="js/bootstrap.min.js"></script>

<script src="materialize/js/materialize.min.js"></script>

<!-- Metis Menu Js -->
<script src="js/jquery.metisMenu.js"></script>
<!-- Morris Chart Js -->
<script src="js/morris/raphael-2.1.0.min.js"></script>
<script src="js/morris/morris.js"></script>


<script src="js/easypiechart.js"></script>
<script src="js/easypiechart-data.js"></script>

<script src="js/Lightweight-Chart/jquery.chart.js"></script>

<!-- Custom Js -->
<script src="js/custom-scripts.js"></script>


</body>
</html>
