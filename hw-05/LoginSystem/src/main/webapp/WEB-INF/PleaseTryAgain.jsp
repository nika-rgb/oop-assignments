<html>
    <head>
        <title>Welcome</title>
    </head>
    <body>
    <h1>Please Try Again</h1>
    <h2>Either your user name or password is incorrect. Please try again.</h2>
    <form action="HomePageServlet" method="post">
        User Name:
        <input type = "text" name = "Login" value = "" maxlength="100" required/>
        </br>
        </br>
        Password:
        <input type = "text" name = "Password" value = "" maxlength="100" required/>
        <input type = "submit" value = "Login">
        </br>
    </form>
    <a href = "\register">Create New Account</a>
    </body>
</html>