<%--
  Created by IntelliJ IDEA.
  User: Костя
  Date: 14.03.2017
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Meal list</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" type="text/css" />
</head>
<body>
<h2><a href="index.html">Home</a></h2>
<h2>Meal list</h2>
<table>
    <c:forEach var="meal" items="${requestScope.meals}">
        <c:set var="color" value="green" />
        <c:if test="${meal.exceed}">
            <c:set var="color" value="red" />
        </c:if>
    <tr class="${color}">
        <td ><tags:localDate date="${meal.dateTime}" pattern="dd.MM.yyyy" /></td>
        <td ><c:out value="${meal.description}"/></td>
        <td ><c:out value="${meal.calories}"/></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
