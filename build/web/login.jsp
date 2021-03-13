<%-- 
    Document   : login
    Created on : Jan 5, 2021, 11:13:41 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Hana Shop Ohayo</h1>
        <form action="MainController" method="post">
            User:<input type="text" name="userID" /> <br>
            Password:<input type="password" name="pw" /> <br>
            <input type="submit"name="btnAction" value="Login" />
            <input type="reset" value="Reset" />           
        </form>
        <div class="row">
            <div style="margin-top: 30px"class="col-md-12"><a class="btn btn-lg btn-google btn-block btn-outline" href=
                                                              "https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8084/HanaShop/LoginWithGgController&response_type=code
                                                              &client_id=182734766738-s0f33g55vdsmip2rvq6vr45c1jsb0h95.apps.googleusercontent.com&approval_prompt=force"><img 
                        src="https://img.icons8.com/color/16/000000/google-logo.png"> Login Using Google</a>
            </div>
        </div>
        <c:if  test="${not empty requestScope.error}">
            <h3>${requestScope.error}</h3>
        </c:if>
    </body>
</html>
