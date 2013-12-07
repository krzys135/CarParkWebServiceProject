<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>dupa</title>
</head>
<body>
<c:forEach items="${mapa}" var="object">
    <p>Miejsce: ${object.place}, stan: ${object.state}</p>
</c:forEach>
</body>
</html>
