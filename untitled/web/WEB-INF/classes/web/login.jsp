<%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/3/19
  Time: 12:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<%
Object msg = request.getAttribute("message");
if(msg!=null)
    out.println(msg);
%>
登录
<form action="register_do.jsp" method="post" >
    用户名：<input type="text"name="username"/><br/>
    密码：<input type="text"name="password"/><br/>
    <input type="submit" value="登录"/>
</form>
</body>
</html>
