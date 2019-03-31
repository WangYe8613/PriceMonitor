<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %><%--
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

</head>

<body>
<%
    Object user_name = request.getAttribute("user_name");
    Object pass_word = request.getAttribute("pass_word");
    if (user_name == null || pass_word == null) {
        response.sendRedirect("401.html");
    }
%>

<form id="wrapper" action="userServlet">
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
                    <b><%out.print(user_name);%></b>
                    <i class="material-icons right">arrow_drop_down</i>
                </a>
            </li>
        </ul>
    </nav>

    <!-- Dropdown Structure -->
    <ul id="dropdown1" class="dropdown-content">
        <li><a href="#"><i class="fa fa-gear fa-fw"></i> 修改个人信息</a>
        </li>
        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
        </li>
    </ul>

    <!--/. NAV TOP  -->
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
                                for (int i = 0; i < 3; ++i) {
                            %>
                            <a href="data.jsp" class="waves-effect waves-dark"><i class="fa fa-bar-chart-o"></i> Charts</a>
                            <%
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

    <!-- /. NAV SIDE  -->

    <div id="page-wrapper">
        <div class="header">
            <h1 class="page-header">
                在输入框内添加商品链接
            </h1>
        </div>
        <div class="box">
            <label class="inputBox">
                <input type="text" id="url" Name="url" placeholder="请输入商品链接" required="">
                <%
                    String username_password = user_name.toString() + "&" + pass_word;
                %>
                <button type="submit" Name="username_password" value="<%=username_password%>">添加</button>
            </label>
        </div>
        <%
            Object message_url = request.getAttribute("message_url");
            if (message_url != null) {
                out.println(message_url);
            }
        %>
    </div>
</form>
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
