<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: WY
  Date: 2019/4/12
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加url</title>

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

        .box_select {
            width: 100px;
            height: 50px;
            background: #FFFFFF;
            border-radius: 5px;
            position: absolute;
            left: -20px;
            top: -60px;
        }

        .box_input {
            width: 70px;
            height: 50px;
            background: #FFFFFF;
            border-radius: 5px;
            position: absolute;
            left: -20px;
            top: -60px;
        }

        .inputBox {
            width: 700px;
            height: 50px;
            background: #222222;
            border-radius: 5px;
            position: relative;
            left: 80px;
            top: -200px;
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

        .iInput {
            position: absolute;
            width: 99px;
            height: 16px;
            left: 1px;
            top: 2px;
            border-bottom: 0px;
            border-right: 0px;
            border-left: 0px;
            border-top: 0px;
        }

    </style>

</head>
<body>
<%
    Object userName = request.getAttribute("user_name");
    Object passWord = request.getAttribute("pass_word");
    if (userName == null || passWord == null) {
        response.sendRedirect("401.html");
    }
    Object userId = request.getAttribute("user_id");
    String username_password = userName.toString() + "&" + passWord;
%>

<form class="box" action="urlServlet" method="post">

    <label class="radio-inline">
        <input type="radio" name="company" value="0"/>天猫
        <input type="radio" name="company" value="1"/>淘宝
        <input type="radio" name="company" value="2"/>京东
        <input type="radio" name="company" value="3"/>唯品会
    </label>

    <div style="position:relative;">
        <span style="margin-left:0px;width:18px;overflow:hidden;">
            <select id="select_id" class="box_select" onchange="tt(this.id)">
                <div style="display:none;">        
            <%
                        List<String> urlNameList = (List<String>) request.getAttribute("url_name_list");
                        if (urlNameList != null) {
                            for (String url_name:urlNameList) {
                    %>
                    <option name="url_id"><%=url_name%></option>
                    <%
                            }
                        }
                    %>
        </div>
            </select>
        </span>
        <input type="text" id="input_id" name="url_name" class="box_input"/>
    </div>

    <label class="inputBox">
        <input type="hidden" name="user_id" value="<%=userId%>">
        <input type="text" id="url" Name="url" placeholder="请输入商品链接" required="">
        <button type="submit" Name="username_password" value="<%=username_password%>">添加</button>
    </label>

</form>
<script>
    function tt(id) {
        var aa = document.getElementById(id);
        var i = aa.selectedIndex;
        var text = aa.options[i].text;
        var value = aa.options[i].value;
        if (value == 3) {

            text = "请手动填写原因";
            document.getElementById("input_id").disabled = false;
        }
        document.getElementById("input_id").value = text;
    }
</script>
<!-- jQuery Js -->
<script src="js/jquery-1.10.2.js"></script>

</body>
</html>
