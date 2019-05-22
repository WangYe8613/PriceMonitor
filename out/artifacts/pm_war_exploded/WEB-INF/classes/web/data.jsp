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

    <title>用户界面</title>

    <!-- 折线图部分 -->
    <style type="text/css">
        #container {
            max-width: 800px;
            height: 400px;
            margin: 1em auto;
        }

        caption {
            padding-bottom: 5px;
            font-family: 'Verdana';
            font-size: 14pt;
            font-weight: bold;
            color: #555555;
        }

        table {
            font-family: 'Verdana';
            font-size: 12pt;
            color: #555555;
            border-collapse: collapse;
            border: 3px solid #CCCCCC;
            margin: 2px auto;
        }

        tr {
            border-bottom: 2px solid #EEEEEE;
        }

        th {
            border-left: 2px solid #EEEEEE;
            border-right: 2px solid #EEEEEE;
            padding: 12px 15px;
            border-collapse: collapse;
        }

        th[scope="col"] {
            background-color: #ffffee;
        }

        th[scope="row"] {
            background-color: #f0fcff;
            text-align: left;
        }

        td {
            border-left: 2px solid #EEEEEE;
            border-right: 2px solid #EEEEEE;
            padding: 12px 15px;
            border-collapse: collapse;
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
    String username_password = userName.toString() + "&" + passWord;
    Object userId = request.getAttribute("user_id");
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
                                    for (String url_name : urlNameSet) {
                            %>
                            <!-- 提交表单的第二种方式-->
                            <form id="to_dataServlet" action="dataServlet" method="post">
                                <%--<input type="hidden" name="user_id" value="<%=userId%>">--%>
                                <input type="hidden" name="username_password" value="<%=username_password%>">
                                <i class="fa fa-bar-chart-o"> </i><!-- 图标 -->

                                <input type="button" class="waves-effect waves-dark" name="url_name"
                                       value=" <%=url_name%>"
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
                       href="urlMaxServlet?user_name=<%=userName%>&pass_word=<%=passWord%>&user_id=<%=userId%>">
                        <i class="fa fa-dashboard"></i>
                        申请提升url数量上限</a>
                </li>
            </ul>
        </div>
    </nav>

    <!-- 右侧界面 -->
    <div id="page-wrapper">
        <div class="header">
            <h1 class="page-header">各大电商同一商品价格变化对比折线图</h1>
            <!-- 折线图部分 -->
            <script src="js/highcharts.js"></script>
            <script src="js/exporting.js"></script>
            <script src="js/export-data.js"></script>
            <script src="js/accessibility.js"></script>
            <div id="container"></div>

            <%
                HashMap<String, HashMap<String,String>> priceDataMap=(HashMap<String, HashMap<String,String>>)request.getAttribute("price_data_map");
                HashMap<String,String> map_0=priceDataMap.get("0");
                HashMap<String,String> map_1=priceDataMap.get("1");
                HashMap<String,String> map_2=priceDataMap.get("2");
                HashMap<String,String> map_3=priceDataMap.get("3");
            %>

            <script type="text/javascript">
                Highcharts.chart('container', {
                        chart: {
                            type: 'spline'
                        },

                        accessibility: {
                            description: 'Most commonly used desktop screen readers from January 2009 to July 2015 as reported in the Webaim Survey. JAWS remains the most used screen reader, but is steadily declining. ZoomText and WindowEyes are both displaying large growth from 2014 to 2015.'
                        },

                        legend: {
                            symbolWidth: 40
                        },

                        title: {
                            text: '各大电商同一商品价格变化对比'
                        },

                        subtitle: {
                            <%
                                Object urlName=request.getAttribute("url_name");
                            %>
                            text: '<%=urlName%>'
                        },

                        yAxis: {
                            title: {
                                text: 'RMB'
                            }
                        },

                        xAxis: {
                            title: {
                                text: 'Time'
                            },
                            accessibility: {
                                description: 'Time from January 2009 to July 2015'
                            },
                            categories: [
                                <%--<%--%>
                                <%--String date=null;--%>
                                <%--for (Map.Entry<String, String> entry : map_0.entrySet()) {--%>
	                                <%--date+=entry.getKey()+",";--%>
                                <%--}--%>
                                <%--%>--%>
                                'January 2009', 'December 2010', 'May 2012', 'January 2014', 'July 2015'
                            ]
                        },

                        tooltip: {
                            split: true
                        },

                        plotOptions: {
                            series: {
                                point: {
                                    events: {
                                        click: function () {
                                            window.location.href = this.series.options.website;
                                        }
                                    }
                                },
                                cursor: 'pointer'
                            }
                        },
                        series: [
                            <%
                            if(map_0.size()!=0){
                            %>
                            {
                                name: '天猫',
                                data: [
                                    <%
                                    Object price=null;
                                    for(Map.Entry<String,String> entry:map_0.entrySet()){
                                        price=entry.getValue();
                                    %>
                                    <%=price%>,
                                    <%
                                    }
                                    %>
                                    0.00
                                ],
                                website: 'https://www.freedomscientific.com/Products/Blindness/JAWS'
                            }
                            <%
                            if(map_1.size()!=0 || map_2.size()!=0 || map_3.size()!=0){
                             %>
                            ,
                            <%
                            }
                            %>
                        <%
                        }
                        %>

                            <%
                            if(map_1.size()!=0){
                            %>
                            {
                                name: '淘宝',
                                data: [
                                    <%
                                    Object price=null;
                                    for(Map.Entry<String,String> entry:map_1.entrySet()){
                                        price=entry.getValue();
                                    %>
                                    <%=price%>,
                                    <%
                                    }
                                    %>
                                    0.00
                                ],
                                website: 'https://www.freedomscientific.com/Products/Blindness/JAWS'
                            }
                            <%
                            if(map_2.size()!=0 || map_3.size()!=0){
                             %>
                            ,
                            <%
                            }
                            %>
                            <%
                            }
                            %>

                            <%
                            if(map_2.size()!=0){
                            %>
                            {
                                name: '京东',
                                data: [
                                    <%
                                    Object price=null;
                                    for(Map.Entry<String,String> entry:map_2.entrySet()){
                                        price=entry.getValue();
                                    %>
                                    <%=price%>,
                                    <%
                                    }
                                    %>
                                    0.00
                                ],
                                website: 'https://www.freedomscientific.com/Products/Blindness/JAWS'
                            }
                            <%
                            if(map_3.size()!=0){
                             %>
                            ,
                            <%
                            }
                            %>
                            <%
                            }
                            %>

                            <%
                            if(map_3.size()!=0){
                            %>
                            {
                                name: '唯品会',
                                data: [
                                    <%
                                    Object price=null;
                                    for(Map.Entry<String,String> entry:map_3.entrySet()){
                                        price=entry.getValue();
                                    %>
                                    <%=price%>,
                                    <%
                                    }
                                    %>
                                    0.00
                                ],
                                website: 'https://www.freedomscientific.com/Products/Blindness/JAWS'
                            }
                            <%
                            }
                            %>
                ],

                responsive: {
                    rules: [{
                        condition: {
                            maxWidth: 500
                        },
                        chartOptions: {
                            legend: {
                                itemWidth: 150
                            }
                        }
                    }]
                }
                })
                ;

            </script>
        </div>
    </div>
</div>

<!-- jsp通过jQuery提交表单给Servlet -->
<script>
    function jqSubmit() {
        $("#to_dataServlet").submit();
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
