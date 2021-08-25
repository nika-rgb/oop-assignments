<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page import = "Product.Product" %>
<html>
    <head>
        <title> <% Product currentProduct = (Product) request.getAttribute("currentProduct"); %>
                    <%= currentProduct.getItemName() %> </title>
    </head>
    <body>
        <h1> <%= currentProduct.getItemName() %> </h1>
        <img src = "/store-images/<%=currentProduct.getImageFile()%>" title = "itemImage" width = "500"
        height = "600" >
        </br>
        <h4>  <%= currentProduct.getPrice() %>$ </h4>
        <form action = "/shopping-cart" method = "post" id = "product" >
            <input name="productId" type="hidden" value= "<%= currentProduct.getProductId() %>"/>
            <input name = "addToCart" type = "submit" value = "addToCart">
        </form>

    </body>
</html>