<%-- 
    Document   : viewPage
    Created on : Jan 11, 2021, 12:59:20 PM
    Author     : ACER
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Page</title>
    </head>
    <body>
        <h1 style="text-align: center;color: red">HanaShop xin chao!</h1>
        <c:if test="${sessionScope.user.userID==null}">
            <a href="login.jsp">Login now</a>
        </c:if>
        <c:if test="${sessionScope.user.userID!=null}">
            <c:if test="${sessionScope.user.roleID eq 'AD'}">
                <c:redirect url="login.jsp"></c:redirect>
            </c:if>
            <a href="MainController?btnAction=Logout">Logout</a> <hr>
            <a href="MainController?btnAction=Start">Continue Shopping</a>
        </c:if>
        <c:if test="${requestScope.ERROR_UPDATE!=null}">
            <h2 style="color: darkcyan;">${requestScope.ERROR_UPDATE}</h2>
        </c:if>
        <c:if test="${requestScope.UPDATE_SUCESS!=null}">
            <h2 style="color: darkorchid;">${requestScope.UPDATE_SUCESS}</h2>
        </c:if>      
        <c:if test="${requestScope.DELETE_SUCCESS!=null}">
            <h2 style="color: blueviolet;">${requestScope.DELETE_SUCCESS}</h2>
        </c:if>
        <c:if test="${requestScope.EMPTY_ADD!=null}">
            <h2 style="color: darkorchid;">${requestScope.EMPTY_ADD}</h2>
        </c:if> 
        <c:if test="${requestScope.EMPTY_PHONE!=null}">
            <h2 style="color: darkorchid;">${requestScope.EMPTY_PHONE}</h2>
        </c:if> 


        <c:set var="total" value="${0}"></c:set>
        <c:if test="${sessionScope.CART !=null}">
            <c:if test="${not empty sessionScope.CART.cart}">
                <table border="1">
                    <thead>
                        <tr>
                            <th>ProductID</th>
                            <th>ProductName</th>
                            <th>Image</th>
                            <th>Quantity</th>
                            <th>Price</th>
                            <th>Update</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" varStatus="counter" items="${sessionScope.CART.cart}">
                        <form action="MainController">
                            <tr>
                                <td>
                                    <c:set var="total" value="${total+dto.value.price*dto.value.quantity}"></c:set>
                                    <input type="text"readonly="" name="productID" value="${dto.value.productID}" />
                                </td>
                                <td>
                                    <input type="text" name="productName" value="${dto.value.name}" readonly="" />
                                </td>
                                <td>
                                    <img src="${dto.value.image}" style="width: 200px;height: 200px;">
                                </td>
                                <td>
                                    <input type="number" min="1" required="" name="quantity" value="${dto.value.quantity}" />
                                </td>
                                <td>
                                    <input type="text" name="price" readonly=""  value="${dto.value.quantity*dto.value.price}" />
                                </td>
                                <td>
                                    <button type="submit" name="btnAction" value="DeleteCart" onclick='return confirm("ARE U SURE")'>Delete</button>                               
                                </td>
                                <td>
                                    <button type="submit" value="UpdateCart" name="btnAction">Update</button>
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>
            <h2 style="color: red">Total price:${total}$</h2>



            <fmt:parseNumber var = "i" integerOnly = "true" 
                             type = "number" value = "${total*23000}" />


            <form action="MainController">                      
                Address:<input type="text" name="address" required="" />
                Phone:<input type="text" required="" name="phone" minlength="9" maxlength="15">
                <input type="submit" name="btnAction" value="Payment" />
                <input type="submit" name="btnAction" value="VnPay">
                <input type="hidden" name="total" value="${i}">
            </form>

        </c:if>




    </c:if>
    <c:if test="${requestScope.PayError!=null}">
        <c:forEach var="error" items="${requestScope.PayError}">
            <h3 style="color: red;">${error}</h3>
        </c:forEach>
    </c:if>
    
</body>
</html>
