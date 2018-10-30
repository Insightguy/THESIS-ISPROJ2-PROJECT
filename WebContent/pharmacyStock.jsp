<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="assets/css/Login-Form-Clean.css" />
<link rel="stylesheet" href="assets/css/styles.css" />
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700" />
<link rel="stylesheet" href="assets/css/Header-Blue.css" />
</head>
<body>
	<div class="header-blue">
            <nav class="navbar navbar-dark navbar-expand-md navigation-clean-search">
                <div class="container"><a href="index.jsp" class="navbar-brand">Medelivery</a>
                    <div class="collapse navbar-collapse" id="navcol-1">
                        <ul class="nav navbar-nav"></ul>
                        <form target="_self" class="form-inline mr-auto">
                            <div class="form-group"><label for="search-field"></label></div>
                        </form>
                        <c:if test="${userAccess == null}">
                        <span class="navbar-text"><a href="login.jsp" class="login">Log In</a></span>
                        <span class="navbar-text"><a href="register.jsp" class="login">Register</a></span>
                        </c:if>
                        <c:if test="${userAccess == 1}">
                        <span class="navbar-text"><a href="LoginController" class="login">Log Out</a></span>
                        </c:if>
                        <c:if test="${userAccess == 2}">
                        <span class="navbar-text"><a href="DispatcherController?Action=DispatchOrder">Order Dispatch - Regular</a><br></span>
						<span class="navbar-text"><a href="LoginController">Logout</a></span>
                        </c:if>
                        <c:if test="${userAccess == 3}">
                        <span class="navbar-text"><a href="ProductController?Action=AddProduct">Add Product</a><br></span>
                        <span class="navbar-text"><a href="PharmacistController?Action=Prescription">Approve/Disapprove prescription orders</a></span>
						<span class="navbar-text"><a href="LoginController">Logout</a><br></span>
                        </c:if>
                        <c:if test="${userAccess == 4}">
                        <span class="navbar-text"><a href="LoginController" class="login">Log Out</a></span>
                        </c:if>
                    </div>
                </div>
            </nav>
        </div>
    <div class="login-clean">
	<form action="/ProductController">
		<div class="form-group">
        	<input type="hidden"/>
        </div>
		Product to add: <br>
		<select name="ProductAdd">
			<c:forEach items="${ProductList}" var="productList">
				<option value='<c:out value="${productList.productID}" />'><c:out value="${productList.productName}" /></option>
			</c:forEach>
		</select><br>
		Is it Featured?: <br>
		<input type="checkbox" name="feature"/><br>
		Price: <br>
		<input type="number" name="Price"/><br>
		<input type="submit" name="action" value="addProduct"/> 
	</form>
	</div>
	<a href="ProductController?Action=AddnewProduct" class="btn btn-info" role="button">Add a new Product</a>
</body>
</html>