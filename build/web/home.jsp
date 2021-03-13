<%-- 
    Document   : home
    Created on : Jan 5, 2021, 3:05:59 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        
    </head>
    <body>
        <h1 style="text-align: center;color: red">HanaShop xin chao!</h1>
        <c:if test="${sessionScope.user.userID==null}">
            <a href="login.jsp">Login now</a>
        </c:if>
        <a href="viewPage.jsp">View</a>
        <c:if test="${sessionScope.user.userID!=null}">
            <c:if test="${sessionScope.user.roleID eq 'AD'}">
                <c:redirect url="login.jsp"></c:redirect>
            </c:if>
            <h1>
                Welcome ${sessionScope.user.fullName}
            </h1>
            <a href="MainController?btnAction=Logout">Logout</a>
            <a href="MainController?btnAction=History">Purchase History</a>


        </c:if>
        <c:if test="${requestScope.MESSAGE!=null}">
            <h4 style="color: darkcyan;">${requestScope.MESSAGE}</h4>
        </c:if>
        <c:if test="${requestScope.SOLD_OUT!=null}">
            <h4 style="color: darkorchid;">${requestScope.SOLD_OUT}</h4>
        </c:if>

        <%-- ----------------------------------------Search form---------------------------------- --%>

        <div id="searchForm" style="text-align: center; margin-bottom: 30px;margin-top: 15px;">
            <form action="MainController">
                Name: <input type="text" name="nameSearch" value="${param.nameSearch}" style="margin-right: 20px;" />
                Price <input type="number" name="priceSearch" min="1" value="${param.priceSearch}" style="margin-right: 20px;" />
                <c:if test="${requestScope.CATEGORY_LIST!=null}">             
                    Category:   <select name="categoryIDSearch">
                        <option value="">Select category</option>
                        <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                            <c:if test="${not empty param.categoryIDSearch}">
                                <c:if test="${category.categoryID eq param.categoryIDSearch}">
                                    <option value="${category.categoryID}" selected="">${category.categoryName}</option>                                                       
                                </c:if>
                                <c:if test="${category.categoryID != param.categoryIDSearch}">
                                    <option value="${category.categoryID}"  >${category.categoryName}</option>                                                       
                                </c:if>
                            </c:if>
                            <c:if test="${empty param.categoryIDSearch}">
                                <option value="${category.categoryID}">${category.categoryName}</option>
                            </c:if>
                        </c:forEach>
                    </select>
                    <input type="submit" name="btnAction" value="Search" />
                </c:if>
            </form>
        </div>

        <%-- ------------------------Ogirinal list product------------------------- --%>

        <h1 style="color: red">List product</h1>

        <c:if test="${requestScope.LIST!=null}">
            <c:if test="${not empty requestScope.LIST}">
                <div style="text-align: center; color: red;">
                    <c:forEach var="page" begin="1" end="${requestScope.TOTAL_PAGE}">
                        <a href="MainController?btnAction=Search&currentPage=${page}&priceSearch=${param.priceSearch}&nameSearch=${param.nameSearch}&categoryIDSearch=${param.categoryIDSearch}">${page}</a>
                    </c:forEach>
                </div>
                <hr>
                <table border="1" style="float: left">
                    <thead>
                        <tr>
                            <th>Product Name</th>
                            <th>Image</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Add</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="dto" varStatus="counter" items="${requestScope.LIST}">
                        <form action="MainController" method="post">
                            <tr>
                                <td>
                                    <input type="hidden" name="productID" value="${dto.productID}" />
                                    <input readonly="" type="text" name="productName" value="${dto.name}" />
                                </td>
                                <td>
                                    <img style="width: 200px;height: 200px;"  src="${dto.image}">
                                    <input type="hidden" name="image" value="${dto.image}" />
                                </td>
                                <td>
                                    <input type="text" name="description" readonly="" value="${dto.description}" />
                                </td>
                                <td> 
                                    <input type="text" name="price" value="${dto.price}" readonly="" />
                                    $        
                                </td>
                                <td>
                                    <input type="hidden" name="currentPage" value="${param.currentPage}" />
                                    <input type="hidden" name="nameSearch" value="${param.nameSearch}" />
                                    <input type="hidden" name="priceSearch" value="${param.priceSearch}" />
                                    <input type="hidden" name="categoryIDSearch" value="${param.categoryIDSearch}" />
                                    <input type="submit" name="btnAction" value="Add" />                      
                                </td>
                            </tr>
                        </form>
                    </c:forEach>
                </tbody>
            </table>

            <%--            <div style="text-align: center; color: red; margin-bottom: 50px;margin-top: 30px;">
                            <c:forEach var="page" begin="1" end="${requestScope.TOTAL_PAGE}">
                                <a href="">${page}</a>
                            </c:forEach>
            </div>--%>
        </c:if>
    </c:if>   


    <%--   ---------------------------------------Recommendation List---------------------------------------%>
    <c:if test="${requestScope.REC_LIST!=null}">
        <c:if test="${not empty requestScope.REC_LIST}">
            <hr>
            <%--            <div style="text-align: center; color: red;">
            <c:forEach var="page" begin="1" end="${requestScope.TOTAL_PAGE}">
                <a href="SearchController?currentPage=${page}&priceSearch=${param.priceSearch}&nameSearch=${param.nameSearch}&categoryIDSearch=${param.categoryIDSearch}">${page}</a>
            </c:forEach>
        </div>--%>
            <hr>

            <%--    <c:set var="recommendationList" value="${requestScope.REC_LIST.values}"></c:set>
            <c:forEach var="dto" varStatus="counter" items="${requestScope.REC_LIST}">
                <form action="MainController" method="post">
                    <input type="hidden" name="productID" value="${dto.value.productID}" />
                    <input readonly="" type="text" name="productName" value="${dto.value.name}" />
                    <img style="width: 200px;height: 200px;"  src="${dto.value.image}">
                    <input type="hidden" name="image" value="${dto.value.image}" />
                    <input type="text" name="description" readonly="" value="${dto.value.description}" />
                    <input type="text" name="price" value="${dto.value.price}" readonly="" />
                    $        
                    <input type="hidden" name="currentPage" value="${param.currentPage}" />
                    <input type="hidden" name="nameSearch" value="${param.nameSearch}" />
                    <input type="hidden" name="priceSearch" value="${param.priceSearch}" />
                    <input type="hidden" name="categoryIDSearch" value="${param.categoryIDSearch}" />
                    <input type="submit" name="btnAction" value="Add" />                      
                </form>
            </c:forEach>
            --%>

            <table border="1" style="float: right">
                <thead>
                    <tr>
                        <th>Product Name</th>
                        <th>Image</th>
                        <th>Description</th>
                        <th>Price</th>
                        <th>Add</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="dto" varStatus="counter" items="${requestScope.REC_LIST}">
                    <form action="MainController" method="post">
                        <tr>
                            <td>
                                <input type="hidden" name="productID" value="${dto.value.productID}" />
                                <input readonly="" type="text" name="productName" value="${dto.value.name}" />
                            </td>
                            <td>
                                <img style="width: 200px;height: 200px;"  src="${dto.value.image}">
                                <input type="hidden" name="image" value="${dto.value.image}" />
                            </td>
                            <td>
                                <input type="text" name="description" readonly="" value="${dto.value.description}" />
                            </td>
                            <td> 
                                <input type="text" name="price" value="${dto.value.price}" readonly="" />
                                $        
                            </td>
                            <td>
                                <input type="hidden" name="currentPage" value="${param.currentPage}" />
                                <input type="hidden" name="nameSearch" value="${param.nameSearch}" />
                                <input type="hidden" name="priceSearch" value="${param.priceSearch}" />
                                <input type="hidden" name="categoryIDSearch" value="${param.categoryIDSearch}" />
                                <input type="submit" name="btnAction" value="Add" />                      
                            </td>
                        </tr>
                    </form>
                </c:forEach>
            </tbody>
        </table>

        <%--        <div style="text-align: center; color: red; margin-bottom: 50px;margin-top: 30px;">
                    <c:forEach var="page" begin="1" end="${requestScope.TOTAL_PAGE}">
                        <a href="">${page}</a>
                    </c:forEach>
                </div>  --%>
    </c:if>
</c:if>            



</body>
</html>
