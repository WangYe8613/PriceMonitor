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

    <title>申请界面</title>

    <%--<!-- 表格部分 -->--%>
    <%--<style type="text/css">--%>
        <%--table {--%>
            <%--border-left: 1px solid #000;--%>
            <%--border-top: 1px solid #000;--%>
            <%--text-align: center;--%>
        <%--}--%>

        <%--table tr td {--%>
            <%--border-right: 1px solid #000;--%>
            <%--border-bottom: 1px solid #000;--%>
        <%--}--%>
    <%--</style>--%>

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
    Object userId = request.getAttribute("user_id");
    Object oldUrlMax = request.getAttribute("old_url_max");
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
                    <a class="active-menu waves-effect waves-dark"
                       href="urlServlet?user_name=<%=userName%>&pass_word=<%=passWord%>&user_id=<%=userId%>"><i
                            class="fa fa-dashboard"></i>
                        添加新连接</a>
                </li>


                <li>
                    <a href="#" class="waves-effect waves-dark"><i class="fa fa-sitemap"></i> 商品<span
                            class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <%
                                HashMap<String, List<String>> urlData = (HashMap<String, List<String>>) request.getAttribute("url_data");
                                if (urlData != null) {
                                    List<String> urlNameList = urlData.get("url_name");
                                    Set<String> urlNameSet = new LinkedHashSet<String>();
                                    urlNameSet.addAll(urlNameList);
                                    for (String urlName : urlNameSet) {
                            %>
                            <!-- 提交表单的第二种方式-->
                            <form id="to_dataServlet" action="dataServlet" method="post">
                                <input type="hidden" name="user_id" value="<%=userId%>">
                                <%--<input type="hidden"  value="<%=urlName%>">--%>
                                <input type="hidden" name="username_password" value="<%=username_password%>">
                                <i class="fa fa-bar-chart-o"> </i><!-- 图标 -->

                                <input type="submit" class="waves-effect waves-dark" name="url_name"
                                       value="<%=urlName%>"
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
                        申请提升url数量上限</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- 右侧界面 -->
    <div id="page-wrapper">
        <!-- 右侧界面：输入框和单选按钮  -->
        <div class="header">
            <h1 class="page-header">
                申请提升Url数量上限
            </h1>
        </div>

        <%
            Object messageUrlMax=request.getAttribute("message_url_max");
            if(messageUrlMax!=null){
        %>
        <a><%=messageUrlMax%>></a>
        <%
            }
        %>
        <form id="to_urlMaxServlet" action="urlMaxServlet" method="post">
            <div>
                <label for="">    当前url添加数量上限</label>
                <table width="10" border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td><%=oldUrlMax%></td>
                    </tr>
                </table>>
                <label for="">    申请提升上限</label>
                <input type="hidden" name="user_id" value="<%=userId%>">
                <input type="text" width="10" name="url_max" id="url_max" class="form-control" placeholder="请输入想要申请的上限数字" required="">
                <button type="submit" Name="username_password" value="<%=username_password%>" class="btn btn-primary" id="btn-reg">申请</button>
                <%--<a href="login.jsp" class="btn btn-default" id="btn-reg">返回登录</a>--%>
            </div>
        </form>

    </div>


</div>

<!-- jsp通过jQuery提交表单给Servlet -->
<script>
    function jqSubmit() {
        $("#to_urlMaxServlet").submit();
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
