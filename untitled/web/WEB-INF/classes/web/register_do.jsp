<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/3/19
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
<%
    //String user_name=request.getParameter("username");
    //String pass_word=request.getParameter("password");
    //out.println("注册"+user_name+":"+pass_word);
    request.setAttribute("message","注册成功，请登录！");
    request.getRequestDispatcher("login.jsp").forward(request,response); //注册后跳转到登录界面
%>