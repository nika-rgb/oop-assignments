<html>
    <head>
        <title>Create Account</title>
    </head>
    <body>
        <h1>The Name <%= request.getParameter("userName") %> Is Already In Use.</h1>
        <h2>Please Enter Another User Name</h2>
        <form action = "register" method = "post">
            User Name: <input type = "text" name = "userName" value = "" maxLength = "" required/>
            </br>
            </br>
            Password:
            <input type = "password" name = "password" value = "" maxLength = "" required/>
            <input type = "submit" value = "Register">
        </form>
    </body>

</html>