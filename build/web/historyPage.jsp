<%-- 
    Document   : historyPage
    Created on : Jan 19, 2021, 10:07:51 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>History Page</title>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
            $(function () {
                $("#datepicker").datepicker();
            });
        </script>
    </head>
    <body>
        <c:choose>
            <c:when test="${sessionScope.user == null}">
                <c:redirect url="login.jsp"></c:redirect>
            </c:when>
            <c:otherwise>
                <c:if test="${sessionScope.user.roleID eq 'AD'}">
                    <c:redirect url="login.jsp"></c:redirect>
                </c:if>
                <a href="MainController?btnAction=Start">Continue Shopping</a>
                <a href="MainController?btnAction=Logout">Logout</a>
                

                <div>
                    <form action="MainController">
                        Name: <input type="text"  name="productName" value="${param.productName}" />
                        Date: <input type="text" value="${param.date}" name="date" id="datepicker" />
                        <input type="submit" name="btnAction" value="Search History" />
                    </form>
                </div>

                <c:choose>
                    <c:when test="${requestScope.HIS_LIST == null}">
                        <h2 style="color: red;">You don't have any purcharse history</h2>
                    </c:when>
                    <c:otherwise>
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>Product Name</th>
                                    <th>Image</th>
                                    <th>Quantity</th>
                                    <th>Price</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="dto" items="${requestScope.HIS_LIST}">
                                <tr>
                                    <td>${dto.productName}</td>
                                    <td>
                                        <img src="${dto.image}" style="width: 200px;height: 200px;">
                                    </td>
                                    <td>${dto.quantity}</td>
                                    <td>${dto.price}</td>
                                </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                       
                    </c:otherwise>    


                </c:choose>
            </c:otherwise>
        </c:choose>
    </body>
</html>
