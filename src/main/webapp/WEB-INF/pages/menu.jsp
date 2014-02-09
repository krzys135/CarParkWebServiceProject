<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <ul id="menu">
        <li><button id="userlist" onclick="location.href='/main/userdetails/'">User list</button></li>
        <li><button id="floor" onclick="location.href='/main/floorstatus/'">Car park</button></li>
        <li><button onclick="location.href='/main/ticketsdetails'">Tickets</button> </li>
        <li><button id="paynemt" onclick="location.href='/main/paymentdetails'">Payments</button> </li>
        <li><button onclick="location.href='<c:url value="/j_spring_security_logout" />'">Logout</button></li>
    </ul>
</div><br>