<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Purchase an item</title>
</head>
<body>
	<form action="PurchaseController" method="post">
		<fieldset>
			<div align="center">
				<label for="OrderAddress">Order Address</label>
				<input type="text" name="OrderAddress" placeholder="Order Address" maxlength="25"/><br />
				<label for="PaymentMethod">Payment Method</label>
				<input type="text" name="PaymentMethod" placeholder="Payment Method" maxlength="25"/><br />
			</div>&nbsp;
			<div align="center">
				<!-- Copy start -->
				<h3>Order 1:</h3>
				<label for="Password">Product:</label>
				<select id="ProductID">
					<c:forEach items="${productList}" var="product">
						<option value="<c:out value="${product.ProductID}" />"><c:out value='${product.ProductName}' /></option>
					</c:forEach>
				</select>
				<label for="Quantity1" >Quantity</label>
				<input type="text" name="Quantity1" placeholder="Password" maxlength="25"/><br />
				<!-- Copy end -->
			</div>&nbsp;
			<div align="center">
				<input type="submit" value="Submit" />
			</div>
		</fieldset>
	</form>
</body>
</html>