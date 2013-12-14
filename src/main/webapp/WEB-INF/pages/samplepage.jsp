<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>dupa</title>
    <link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"></head>
<body>
<%--<c:forEach items="${mapa}" var="object">--%>
    <%--<p>Miejsce: ${object.place}, stan: ${object.state}</p>--%>
<%--</c:forEach>--%>

<table id="gradient-style" summary="Meeting Results">
    <thead>
    <tr>
        <th scope="col">Employee</th>
        <th scope="col">Division</th>
        <th scope="col">Suggestions</th>
        <th scope="col">Rating</th>

    </tr>
    </thead>
    <tfoot>
    <tr>
        <td colspan="4">Give background color to the table cells to achieve seamless transition</td>
    </tr>
    </tfoot>
    <tbody>
    <tr>
        <td>Stephen C. Cox</td>
        <td>Marketing</td>
        <td>Make discount offers</td>
        <td>3/10</td>
    </tr>
    <tr>
        <td>Josephin Tan</td>
        <td>Advertising</td>
        <td>Give bonuses</td>
        <td>5/10</td>
    </tr>
    <tr>
        <td>Joyce Ming</td>
        <td>Marketing</td>
        <td>New designs</td>
        <td>8/10</td>
    </tr>
    <tr>
        <td>James A. Pentel</td>
        <td>Marketing</td>
        <td>Better Packaging</td>
        <td>8/10</td>
    </tr>
    <tr>
        <td>James A. Pentel</td>
        <td>Marketing</td>
        <td>Better Packaging</td>
        <td>8/10</td>
    </tr>
    </tbody>
</table>
</body>
</html>
