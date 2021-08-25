<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>Welcome <%= request.getParameter("userName") %></title>
    </head>
    <body>
        <h1>Welcome <%= request.getParameter("userName") %></h1>
    </body>

</html>