<%--
  Created by IntelliJ IDEA.
  User: Jarek
  Date: 14.12.13
  Time: 08:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery-1.4.4.min.js" />"></script>
    <title></title>
</head>
<body>



<table id="gradient-style" summary="Meeting Results">
    <thead>
    <tr>
        <th scope="col">Segment</th>
        <th scope="col">Miejsce</th>
        <th scope="col">Stan</th>
        <th scope="col">Czujnik</th>
    </tr>
    </thead>
    <tfoot>
    <tr>
        <td colspan="4">Cos mozna tu wpisac</td>
    </tr>
    </tfoot>
    <tbody>

    <c:forEach items="${spaces}" var="s">
        <tr>
            <td>${s.segment_id}</td>
            <td>${s.place}</td>
            <td>${s.state}</td>
            <td>${s.sensor}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>


</body>
</html>
