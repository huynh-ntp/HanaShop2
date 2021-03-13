<%-- 
    Document   : admin
    Created on : Jan 5, 2021, 3:08:10 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
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
            <c:url var="GotoCreateController" value="MainController">
                <c:param name="btnAction" value="GotoCreateController"></c:param>
            </c:url>
            <a href="${GotoCreateController}">Create New</a>
            <hr>

            <h2 style="color: blueviolet; text-align: center" >${requestScope.UPDATE_MESSAGE}</h2>

            <%-- -------------------- From Search ------------------ --%>
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
                        <input type="submit" name="btnAction" value="Search Admin" />
                    </c:if>
                </form>
            </div>
                    
                    
                    
                    
                    

            <hr>
            <c:if test="${requestScope.TOTAL_PAGE!=null}">
                <div style="text-align: center">
                    <c:forEach var="page" begin="1" end="${requestScope.TOTAL_PAGE}">
                        <a href="MainController?btnAction=Search Admin&currentPage=${page}&priceSearch=${param.priceSearch}&nameSearch=${param.nameSearch}&categoryIDSearch=${param.categoryIDSearch}">${page}</a>
                    </c:forEach>
                </div>
            </c:if>
            <hr>
            <c:if test="${requestScope.LIST!=null}">
                <c:if test="${not empty requestScope.LIST}">
                    <table border="1">
                        <thead>
                            <tr>
                                <th>Product ID</th>
                                <th>Product name</th>
                                <th>Image</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Create date</th>
                                <th>Category</th>
                                <th>Quantity</th>
                                <th>Status</th>
                                <th>Update</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="dto" varStatus="counter" items="${requestScope.LIST}">
                            <form action="MainController" method="">
                                <tr>
                                    <td>             
                                        <input type="text" name="productID" value="${dto.productID}" readonly="" />
                                    </td>
                                    <td>
                                        <input type="text" name="productName" required="" value="${dto.name}" />
                                    </td>                                   
                                    <td>
                                        <img id="preview${counter.count}" src="${dto.image}" width="235" height="200"> <br>
                                        <input id="file${counter.count}" type="file" name="image" value="" onchange="previewImage${counter.count}()" />                                        
                                    </td>                                  
                                    <td>
                                        <input type="text" name="description" required="" value="${dto.description}" />
                                    </td>
                                    <td>
                                        <input type="number" required="" step="0.1" name="price" value="${dto.price}" />
                                    </td>
                                    <td>
                                        ${dto.createDate}
                                    </td>
                                    <td>
                                        <c:if test="${requestScope.CATEGORY_LIST!=null}">
                                            <select name="categoryID">
                                                <c:forEach var="category" items="${requestScope.CATEGORY_LIST}">
                                                    <c:if test="${category.categoryID eq dto.categoryID}">
                                                        <option value="${category.categoryID}" selected="">${category.categoryName}</option>                                                       
                                                    </c:if>
                                                    <c:if test="${category.categoryID != dto.categoryID}">
                                                        <option value="${category.categoryID}"  >${category.categoryName}</option>                                                       
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </td>
                                    <td>
                                        <input type="number" name="quantity" value="${dto.quantity}" required="" min="1" />
                                    </td>
                                    <td>                                       
                                        <c:if test="${requestScope.STATUS_LIST!=null}">
                                            <select name="status">
                                                <c:forEach var="status" items="${requestScope.STATUS_LIST}">
                                                    <c:if test="${status == dto.status}">
                                                        <option value="${status}" selected="">${status}</option>                                                       
                                                    </c:if>
                                                    <c:if test="${status != dto.status}">
                                                        <option value="${status}">${status}</option>                                                       
                                                    </c:if>
                                                </c:forEach>
                                            </select>
                                        </c:if>
                                    </td>
                                    <td>
                                        <input type="submit" name="btnAction" value="Update" />
                                        <input type="hidden" name="userID" value="${sessionScope.user.userID}" />
                                    </td>
                                    <td> 
                                        <input type="submit" onclick="return confirm('Do you want to delete?')" name="btnAction" value="Delete">
                                        <input type="hidden" name="currentPage" value="${param.currentPage}">
                                        <input type="hidden" name="priceSearch" value="${param.priceSearch}">
                                        <input type="hidden" name="nameSearch" value="${param.nameSearch}">
                                        <input type="hidden" name="categoryIDSearch" value="${param.categoryIDSearch}">
                                    </td>

                                </tr>


                                <script>
                                    function previewImage${counter.count}() {
                                        var file = document.getElementById("file${counter.count}").files;
                                        if (file.length > 0) {
                                            var fileReader = new FileReader();
                                            fileReader.onload = function (event) {
                                                document.getElementById("preview${counter.count}").setAttribute("src", event.target.result);
                                            };
                                        }
                                        fileReader.readAsDataURL(file[0]);
                                    }
                                </script>                               
                            </form>  
                        </c:forEach>                        
                    </tbody>
                </table>
            </c:if>
        </c:if>
        <c:if test="${requestScope.TOTAL_PAGE!=null}">
            <div style="text-align: center;margin-bottom: 50px;margin-top: 30px;">
                <c:forEach var="page" begin="1" end="${requestScope.TOTAL_PAGE}">
                    <a href="MainController?btnAction=Manage&currenPage=${page}">${page}</a>
                </c:forEach>
            </div>
        </c:if>












    </c:if>

</body>

</html>
