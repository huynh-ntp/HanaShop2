<%-- 
    Document   : successPay
    Created on : Jan 19, 2021, 10:54:37 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Successful Payment Page</title>
    </head>
    <body>
        <c:choose>
            <c:when test="${sessionScope.user!=null}">
                <c:if test="${sessionScope.user.roleID eq 'AD'}">
                    <c:redirect url="login.jsp"></c:redirect>
                </c:if>
                <div style="text-align: center">
                    <h3 style="color: blueviolet">Thanks for shopping!</h3>
                    <h2>The goods will be delivered to you by the shipper as soon as possible</h2>
                    <a href="MainController?btnAction=Start">Continue Shopping</a>
                </div>
            </c:when>
            <c:otherwise>              
                <c:redirect url="login.jsp"></c:redirect>
            </c:otherwise>
        </c:choose>  

    </body>
</html>
