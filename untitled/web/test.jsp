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
    <title>Target Material Design Bootstrap Admin Template</title>

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

    <title>用户界面</title>

    <!-- 输入框部分 -->
    <style>
        .box {
            width: 900px;
            height: 500px;
            border-radius: 5px;
            box-shadow: 3px 3px 5px 0 rgba(0, 0, 0, 0.4);
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            margin: auto;
            background: linear-gradient(120deg, #FFFFFF, #111100);
            display: grid;
            justify-content: center;
            align-items: center;
        }

        .inputBox {
            width: 700px;
            height: 50px;
            background: #222222;
            border-radius: 5px;
            position: relative;
        }

        .inputBox input {
            width: 200px;
            height: 50px;
            border: none;
            padding: 0;
            padding-left: 5px;
            color: #fff;
            background: none;
            outline: none;
            position: absolute;
            bottom: 0;
        }

        .inputBox input:focus ~ span {
            transform: scale(0.6);
            opacity: 0.6;
        }

        .inputBox input:focus ~ button {
            opacity: 0.6;
        }

        .inputBox button {
            width: 70px;
            height: 50px;
            border-radius: 5px;
            position: absolute;
            right: 0;
            border: 0;
            padding: 0;
            background: #006ac3;
            color: #00427a;
            font-size: 18px;
            outline: none;
            cursor: pointer;
            opacity: 0;
            transition: 0.5s;
            z-index: 1;
        }

        .inputBox button:focus {
            width: 700px;
            opacity: 1;
            color: #fff;
        }

        .inputBox button:focus ~ span {
            transform: scale(0.6);
            opacity: 0.6;
        }
    </style>

    <!-- 表格部分 -->
    <style type="text/css">
        table {border-left:1px solid #000;border-top:1px solid #000; text-align:center;}
        table tr td { border-right:1px solid #000; border-bottom:1px solid #000;}
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
    String username_password = userName.toString() + "&" + passWord;
    Object back = request.getAttribute("back");
    if (back == null) {
        request.setAttribute("user_name", userName);
        request.setAttribute("pass_word", passWord);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("userServlet");
        requestDispatcher.forward(request, response); //如果back为空，则去userServlet获取数据，即保证先从userServlet走到user.jsp
    }
%>

<form id="wrapper" action="userServlet">
    <!-- 顶部界面：用户名 -->
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle waves-effect waves-dark" data-toggle="collapse"
                    data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand waves-effect waves-dark" href="index.html"><i class="large material-icons">track_changes</i>
                <strong>target</strong></a>

            <div id="sideNav" href=""><i class="material-icons dp48">toc</i></div>
        </div>

        <ul class="nav navbar-top-links navbar-right">
            <li>
                <a class="dropdown-button waves-effect waves-dark" href="#!" data-activates="dropdown1">
                    <i class="fa fa-user fa-fw"></i>
                    <b><%out.print(userName);%></b>
                    <i class="material-icons right">arrow_drop_down</i>
                </a>
            </li>
        </ul>
    </nav>

    <!-- 顶部界面：下拉列表 -->
    <ul id="dropdown1" class="dropdown-content">
        <li><a href="#"><i class="fa fa-gear fa-fw"></i> 修改个人信息</a>
        </li>
        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
        </li>
    </ul>

    <!-- 左侧界面：下拉列表 -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <li>
                    <a class="active-menu waves-effect waves-dark" href="index.html"><i class="fa fa-dashboard"></i>
                        Dashboard</a>
                </li>


                <li>
                    <a href="#" class="waves-effect waves-dark"><i class="fa fa-sitemap"></i> 商品<span
                            class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <%
                                HashMap<String,String> urlMap = (HashMap<String,String>) request.getAttribute("url_map");
                                if (urlMap != null) {
                                    Iterator it = urlMap.entrySet().iterator();
                                    while (it.hasNext()) {
                                        HashMap.Entry entry = (java.util.Map.Entry)it.next();
                                        Object url_id=entry.getKey();
                            %>
                            <!-- 提交表单的第二种方式-->
                            <form id="form1" method="post" action="dataServlet">
                                <input type="hidden" name="url_id" value="<%=url_id%>">
                                <input type="hidden" name="username_password" value="<%=username_password%>">
                                <i class="fa fa-bar-chart-o" >  </i><!-- 图标 -->
                                <input type="button" class="waves-effect waves-dark" value="商品<%=url_id%>" onclick="jqSubmit()">
                            </form>
                            <%
                                    }
                                }
                            %>
                        </li>
                    </ul>
                </li>
                <li>
                    <a href="empty.html" class="waves-effect waves-dark"><i class="fa fa-fw fa-file"></i> Empty Page</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- 右侧界面 -->
    <div id="page-wrapper">
        <!-- 右侧界面：输入框和单选按钮  -->
        <div class="header">
            <h1 class="page-header">
                在输入框内添加商品链接
            </h1>
        </div>

        <div>
            <input type="radio" name="company" value="0" />天猫
            <input type="radio" name="company" value="1" />淘宝
            <input type="radio" name="company" value="2" />京东
            <input type="radio" name="company" value="3" />唯品会
        </div>

        <div class="box">
            <label class="inputBox">
                <input type="text" id="url" Name="url" placeholder="请输入商品链接" required="">
                <button type="submit" Name="username_password" value="<%=username_password%>">添加</button>
            </label>
        </div>

        <!-- url列表展示部分-->
        <table width="900" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>天猫</td>
            </tr>
            <%
                Object message_url = request.getAttribute("message_url");
                if (message_url != null) {
                    out.println(message_url);
                }
                if (urlMap != null) {
                    Iterator it = urlMap.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry entry = (java.util.Map.Entry)it.next();
                        Object url=entry.getValue();
            %>
            <tr>
                <td><%=url %></td>
            </tr>
            <%
                    }
                }
            %>
        </table>
    </div>



</form>

<!-- jsp通过jQuery提交表单给Servlet -->
<script>
    function jqSubmit(){
        $("#form1").submit();
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
