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

    Object back = request.getAttribute("back_managerServlet");
    if (back == null) {
        request.setAttribute("user_name", userName);
        request.setAttribute("pass_word", passWord);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("managerServlet");
        requestDispatcher.forward(request, response); //如果back为空，则去userServlet获取数据，即保证先从userServlet走到user.jsp
    }

    String username_password = userName.toString() + "&" + passWord;
    //Object userId = request.getAttribute("user_id");
%>

<div id="wrapper">
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
                <strong>价格监视器</strong></a>

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
                    <a href="managerServlet?user_name=<%=userName%>&pass_word=<%=passWord%>"
                       class="waves-effect waves-dark"><i class="fa fa-sitemap"></i> 用户信息<span
                            class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <%
                                HashMap<String, List<String>> userDataMap = (HashMap<String, List<String>>) request.getAttribute("user_data_map");
                                if (userDataMap != null) {
                                    List<String> nameList = userDataMap.get("name");
                                    Set<String> nameSet = new LinkedHashSet<String>();
                                    nameSet.addAll(nameList);
                                    for (String name : nameSet) {
                            %>
                            <!-- 提交表单的第二种方式-->
                            <form id="to_userDataServlet" action="userDataServlet" method="post">
                                <%--<input type="hidden"  value="<%=urlName%>">--%>
                                <input type="hidden" name="username_password" value="<%=username_password%>">
                                <i class="fa fa-bar-chart-o"> </i><!-- 图标 -->

                                <input type="submit" class="waves-effect waves-dark" name="name"
                                       value="<%=name%>"
                                       onclick="jqSubmit()">
                            </form>
                            <%
                                    }
                                }
                            %>
                        </li>
                    </ul>
                </li>
                <li>
                    <a class="active-menu waves-effect waves-dark"
                       href="#">
                        <i class="fa fa-dashboard"></i>
                        处理数据还原请求</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- 右侧界面 -->
    <div id="page-wrapper">
        <!-- 右侧界面：输入框和单选按钮  -->
        <div class="header">
            <h1 class="page-header">
                数据还原请求列表
            </h1>
        </div>

        <!-- url列表展示部分-->
        <table width="900" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td>用户ID</td>
                <td>商品名</td>
                <td>所属电商</td>
                <td>用户名</td>
                <td>操作</td>
            </tr>
            <%
                Object message_update_user = request.getAttribute("message_reduction_url");
                if (message_update_user != null) {
                    out.println(message_update_user);
                }
                HashMap<String, List<String>> reductionDataMap = (HashMap<String, List<String>>) request.getAttribute("reduction_data_map");

                if (reductionDataMap != null) {
                    List<String> userIdList = reductionDataMap.get("user_id");
                    List<String> urlNameList = reductionDataMap.get("url_name");
                    List<String> companyList = reductionDataMap.get("company");
                    List<String> userNameList = reductionDataMap.get("user_name");

                    if (userIdList != null && urlNameList != null && companyList != null && userNameList != null) {
                        int index = 0;
                        String user_id = null;
                        String url_name = null;
                        String company = null;
                        String user_name = null;

                        for (int i = 0; i < userIdList.size(); ++i) {
                            user_id = userIdList.get(i);
                            url_name = urlNameList.get(i);
                            company = companyList.get(i);
                            user_name = userNameList.get(i);

            %>
            <tr>
                <td><%=user_id %>
                </td>
                <td><%=url_name %>
                </td>
                <td><%=company %>
                </td>
                <td><%=user_name %>
                </td>
                <td>
                    <form action="approvalReductionServlet" method="post">
                        <input type="hidden" name="username_password" value="<%=username_password%>">
                        <input type="hidden" name="user_id" value="<%=user_id%>">
                        <input type="hidden" name="url_name" value="<%=url_name%>">
                        <input type="hidden" name="company" value="<%=company%>">
                        <input type="hidden" name="reduction" value="true">
                        <input type="submit" name="operation" value="还 原">
                    </form>
                </td>
            </tr>
            <%
                        }
                    }
                }
            %>
        </table>
    </div>


</div>

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
