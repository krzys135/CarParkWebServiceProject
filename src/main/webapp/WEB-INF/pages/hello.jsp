<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>

    <link href="<c:url value="resources/css/adder_style.css" />" rel="stylesheet">
    <script src="<c:url value="resources/js/jquery-1.4.4.min.js" />" type="text/javascript"></script>

    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>

    <script>
        jq(function() {
            jq("#userlist").click(function(){
                jq("#msg").empty().load("/main/userdetails/");
            });
            jq("#floor").click(function(){
                jq("#msg").empty().load("/main/floorstatus/");
            });
        });
    </script>
</head>

<body>

<div>
    <ul id="menu">
        <li><button id="userlist" <%--onclick="location.href='/main/userdetails/'"--%>>User list</button></li>
        <li><button id="floor" <%--onclick="location.href='/main/floorstatus/'"--%>>Car park</button></li>
    </ul>
</div>

<div id="msg"></div>

<button onclick="location.href='<c:url value="/j_spring_security_logout" />'">Logout</button>

</body>

</html>