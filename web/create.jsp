<%-- 
    Document   : create
    Created on : Jan 7, 2021, 10:39:12 AM
    Author     : ACER
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Product Page</title>
    </head>
    <body>
        <c:if test="${sessionScope.user==null }">
            <c:redirect url="home.jsp"></c:redirect>
        </c:if>
        <c:if test="${sessionScope.user!=null }" >
            <c:if test="${user.roleID eq 'US'}">
                <c:redirect url="login.jsp"></c:redirect>
            </c:if>
            <h1>Welcome ${sessionScope.user.fullName}</h1>
            <c:url var="Logout" value="MainController">
                <c:param name="btnAction" value="Logout"></c:param>
            </c:url>
            <a href="${Logout}">Logout</a> <br>
            <hr>
            <form action="MainController" method="post">

                ProductID:<input type="text" name="productID" required="" /> <br>
                Name:<input type="text" name="name" required="" /><br>
                Image:<img id="preview" width="200" height="200">
                <input type="file" id="file" name="image" accept="image/*" required="" onchange="previewImage();" value="" /><br>
                Description:<input type="text" name="description" required="" /><br>
                Price: <input type="number" step="0.1" name="price" required="" min="1" value="" /><br>
                Category: <select name="categoryID"><br>
                    <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                        <option value="${category.categoryID}">${category.categoryName}</option>
                    </c:forEach>
                </select><br>
                Quantity:<input type="number" name="quantity" value="" required="" min="1" /><br>
                <input type="submit" name="btnAction" value="Create" />

            </form>
            <hr>
            <c:if test="${requestScope.ERROR!=null}">
                ${requestScope.ERROR.productIDError}<br>
                ${requestScope.ERROR.nameError}<br>
                ${requestScope.ERROR.descriptionError}<br>
                <a href="MngController?">Back to list product</a>
            </c:if>
            <hr>    
            <c:if test="${requestScope.SUCCESS!=null}">
                <h3 style="color: red">${requestScope.SUCCESS}</h3>
                <a href="MainController?btnAction=GotoCreateController" >Create New </a> <br>              
            </c:if>
                
            <a href="MainController?btnAction=Manage">Back to list product</a>

        </c:if>
    </body>
    <script>
        function previewImage() {
            var file = document.getElementById("file").files;
            if (file.length > 0) {
                var fileReader = new FileReader();
                fileReader.onload = function (event) {
                    document.getElementById("preview").setAttribute("src", event.target.result);                  
                };
            }
            fileReader.readAsDataURL(file[0]);
        }
    </script>
</html>

