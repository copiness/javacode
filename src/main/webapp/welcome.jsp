<%@ page import="javax.servlet.http.HttpSession" %>
<%
    // Retrieve the existing session or create a new one if it doesn't exist
    HttpSession userSession = request.getSession(false);
    String username = null;

    if (userSession != null) {
        username = (String) userSession.getAttribute("username");
    }

    if (username == null) {
        response.sendRedirect("login.html");
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Welcome</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
        href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body class="container-fluid">
    <div class="card" id="div1">
        <h2 class="card-header text-center text-light bg-success">Welcome, <%= username %></h2>
        <div class="card-body">
            <p class="text-center">You have successfully logged in!</p>
            <form action="logout" method="post">
                <input type="submit" value="Logout" class="btn btn-outline-danger">
            </form>
        </div>
    </div>
</body>
</html>
