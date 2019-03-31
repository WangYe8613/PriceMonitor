<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/3/19
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
      <title>登录表单</title>

      <!-- Meta-Tags -->
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

      <script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
      <!-- //Meta-Tags -->

      <!-- Style --> <link rel="stylesheet" href="css/style.css" type="text/css" media="all">
  </head>
  <body>
  <h1>登录表单</h1>

  <div class="container w3layouts agileits">

      <div class="login w3layouts agileits">
          <h2>登 录</h2>

          <form class="send-button w3layouts agileits" action="loginServlet" method="post">
              <input type="text" id="username_login" Name="username_login" placeholder="用户名" required="">
              <input type="password" id="password_login" Name="password_login" placeholder="密码" required="">
              <input type="submit" value="登 录">
          </form>
          <span style="color:white">
              <%
                  Object msg_login = request.getAttribute("message_login");
                  if(msg_login!=null)
                      out.println(msg_login);
//
//                  request.setAttribute("username_login",request.getParameter("username_login"));
//                  request.setAttribute("password_login",request.getParameter("password_login"));
              %>
          </span>
      </div>

      <div class="register w3layouts agileits">
          <h2>注 册</h2>
          <form class="send-button w3layouts agileits" action="registerServlet" method="post">
              <input type="text" id="username_register" Name="username_register" placeholder="用户名" required="">
              <input type="password" id="password_register" Name="password_register" placeholder="密码" required="">
              <input type="submit" value="注册">
          </form>
          <span style="color:white">
              <%
                  Object msg_register = request.getAttribute("message_register");
                  if(msg_register!=null)
                      out.println(msg_register);
//
//                  request.setAttribute("username_register",request.getParameter("username_register"));
//                  request.setAttribute("password_register",request.getParameter("password_register"));
              %>
          </span>
      </div>
  </div>

  <div class="footer w3layouts agileits">
      <p>Copyright &copy; More Templates</p>
  </div>
  <div style="text-align:center;">
  </div>
  </body>>
</html>
