<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>Shopping Cart</title>
    </head>

    <body>
        <h1>Shopping cart</h1>
        <form action = "/update" method = "post" id = "update-cart">
            <ul>
                <c:forEach var = "product" items = "${alreadyBought}">
                    <li>
                        <input type = "text" value = '<c:out value = "${product.value}" />' name = '<c:out value = "${product.key.productId}" />' />
                        <c:out value = "${product.key.itemName}" />
                        ,
                        <c:out value = "${product.key.price}$" />
                    </li>
                </c:forEach>
            </ul>
            Total : <c:out value = "${spentMoney}$" />

            <input type ="submit" value = "update Cart" id = "updateCart">
        </form>
        <a href = "/">Continue Shopping</a>
        </ul>
    </body>

</html>