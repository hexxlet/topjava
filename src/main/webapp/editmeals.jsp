<%--
  Created by IntelliJ IDEA.
  User: Костя
  Date: 17.03.2017
  Time: 6:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
    <title>Edit meal</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" type="text/css" />
</head>
<body>
<h1>Add or Edit meal</h1>
<form method="post" action="meals" name="edituser">
    Meal id : <input type="text" readonly="readonly" name="id" value="<c:out value="${meal.id}"/>">
    <br/>
    Meal time : <input type="text" class= "date" name = "dateTime" value = "<tags:localDate date="${meal.dateTime}" pattern="dd.MM.yyyy"/>"/>
    <br/>
    <%--Meal time : <input type="datetime" name="dateTime" value="${meal.dateTime}" pattern="dd.MM.yyyy">
    <br/>--%>
    Meal description : <input type="text" name="description" value="<c:out value="${meal.description}"/>">
    <br/>
    Meal calories : <input type="text" name="calories" value="<c:out value="${meal.calories}"/>">
    <br/>
    <input type="submit" value="Submit" />
</form>
</body>
</html>
