<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>Student Store</title>
    </head>

    <body>
        <h1>Student Store</h1>
        <h2>Items available: </h2>
        <ul>
            <c:forEach items = "${prod}" var = "p" >
               <c:url value="/show-product" var="showUrl">
                 <c:param name="id"   value="${p.productId}" />
               </c:url>
               <a href = '<c:out value = "${showUrl}" />' > <li><c:out value = "${p.itemName}"/></li> </a> </br>
            </c:forEach>
        </ul>
    </body>
</html>