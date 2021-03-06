<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MedeliveryGUI_v4</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="assets/fonts/ionicons.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Cookie">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Lora">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:400,300,700,400italic,300italic,700italic">
    <link rel="stylesheet" href="assets/css/Article-Clean.css">
    <link rel="stylesheet" href="assets/css/Article-List.css">
    <link rel="stylesheet" href="assets/css/Brands.css">
    <link rel="stylesheet" href="assets/css/Contact-Form-Clean.css">
    <link rel="stylesheet" href="assets/css/Data-Summary-Panel---3-Column-Overview--Mobile-Responsive.css">
    <link rel="stylesheet" href="assets/css/Features-Blue.css">
    <link rel="stylesheet" href="assets/css/Features-Boxed.css">
    <link rel="stylesheet" href="assets/css/Footer-Basic.css">
    <link rel="stylesheet" href="assets/css/Footer-Dark.css">
    <link rel="stylesheet" href="assets/css/Good-login-dropdown-menu.css">
    <link rel="stylesheet" href="assets/css/Good-login-dropdown-menu1.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/Swiper/3.3.1/css/swiper.min.css">
    <link rel="stylesheet" href="assets/css/Gallery.css">
    <link rel="stylesheet" href="assets/css/Gallery1.css">
    <link rel="stylesheet" href="assets/css/Login-Form-Clean.css">
    <link rel="stylesheet" href="assets/css/Pretty-Search-Form.css">
    <link rel="stylesheet" href="assets/css/Map-Clean.css">
    <link rel="stylesheet" href="assets/css/multi-item-carousel.css">
    <link rel="stylesheet" href="assets/css/Navbar-Centered.css">
    <link rel="stylesheet" href="assets/css/Navigation-with-Search.css">
    <link rel="stylesheet" href="assets/css/Pretty-Table.css">
    <link rel="stylesheet" href="assets/css/Pretty-Table1.css">
    <link rel="stylesheet" href="assets/css/Registration-Form-with-Photo.css">
    <link rel="stylesheet" href="assets/css/Simple-Slider.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="stylesheet" href="assets/css/styles.css">
    <link rel="stylesheet" href="assets/css/Team-Boxed.css">
    <link rel="stylesheet" href="assets/css/TR-Form.css">
    <link rel="stylesheet" href="assets/css/Custom.css">
    <link rel="stylesheet" type="text/css" href="js/data_table/reset-min.css">
	<link rel="stylesheet" type="text/css" href="js/data_table/complete.css">
	
</head>

<body><img src="assets/img/MedeliveryLogo.png">
    <nav class="navbar navbar-light navbar-expand-md">
        <div class="container-fluid"><a class="navbar-brand" href="#"></a><button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse"
                id="navcol-1">
                <ul class="nav navbar-nav">
                    <li class="nav-item" role="presentation"><a class="nav-link" href="index.jsp">Home</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="About.jsp">About</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="ContactUs.jsp">Contact Us</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="Members.jsp">Membership Registration</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="Catalog.jsp">View Products</a></li>
                </ul>
                <c:choose>
	                	<c:when test = "${userID > 0}">
	                		<ul class="nav navbar-nav ml-auto">
			                    <li class="nav-item" role="presentation"><a class="nav-link" href="Cart.jsp">My Cart</a></li>
			                    <li class="nav-item" role="presentation"><a class="nav-link" href="Logout.jsp">Log out</a></li>
			                </ul>
	                	</c:when>
	                	<c:otherwise>
			                <ul class="nav navbar-nav ml-auto">
			                    <li class="nav-item" role="presentation"><a class="nav-link" href="Login.jsp">Login</a></li>
			                    <li class="nav-item" role="presentation"><a class="nav-link" href="RegistrationCustomer.jsp">Register</a></li>
			                </ul>
			            </c:otherwise>
					</c:choose>
            </div>
        </div>
    </nav>
    <div>
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="row">
                        <div class="col">
                            <h1 class="text-center" style="font-family:Lora, serif;font-size:28px;">For Pharmacists Approval<br></h1>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
		<div class="row">
			<div class="col-md-4" align="Center"><a class="button blue" href="ReportController?ReportTy=2">PRINT THINGS</a></div>
			<div class="col-md-4" align="Center"><a class="button blue" href="ReportController?ReportTy=3">PRINT THINGS</a></div>
			<div class="col-md-4" align="Center"><a class="button blue" href="ReportController?ReportTy=4">PRINT THINGS</a></div>
		</div>
	</div>
    <div>
        <div class="container">                           
                    <div>
                    <!-- First Table -->
                    <h1 class="text-center" style="font-family:Lora, serif;font-size:28px;">Over the counter<br></h1>
                        <table id="OTC" class="pretty"">
                            <thead>
      <tr>
      	  <th>Order ID</th>
      	  <th>Customer Information</th>
      	  <th>City Name</th>
      	  <th>Prescription ID</th>
      	  <th>Order Address</th>
      	  <th>Date Ordered </th>
	      <th>Order Status</th>
	      <th>Senior Discount</th>
	      <th>Order Details</th>
	      <th>Total Cost</th>
	      <th></th>
	      <th></th>
      </tr>
		</thead>
    
    
    
    <tbody>
    	<c:forEach items="${orderPharmacistCheck}" var="order">
			<tr>
				<td><c:out value="${order.orderID}" /></td>
				<td><c:out value="${order.customerInfo}" /></td>
				<td><c:out value="${order.cityName}" /></td>
				<td><c:out value="${order.prescriptionID}" /></td>
				<td><c:out value="${order.orderAddress}" /></td>
				<td><c:out value="${order.dateOrdered}" /></td>
				<td><c:out value="${order.orderStatus}" /></td>
				<td><c:out value="${order.seniorDiscount}" /></td>
				<td>${order.orderDetails}</td>
				<td><c:out value="${order.actualCost}" /></td>
				<td><a href="PurchaseController?action=Reject&orderID=<c:out value="${order.orderID}"/>">REJECT</a></td>
				<td><a href="PurchaseController?action=Approve&orderID=<c:out value="${order.orderID}"/>">APPROVE</a></td>
			</tr>
		</c:forEach>
	</tbody>
          </table>
                        <br>
                 <!-- Second Table -->
                 <h1 class="text-center" style="font-family:Lora, serif;font-size:28px;">Prescription requests<br></h1>
                        <table id="Prescription" class="pretty">
                            <thead>
                                <tr>
						            <th>ProductName</th>
						            <th>GenericName</th>
						            <th>ProductStrength</th>
						            <th>ProductForm</th>
						            <th>ProductPackaging</th>
						            <th>ProductDescription</th>
						            <th>ProductImage</th>
						            <th>Quantity</th>
						            <th>PriceSet</th>
						            <th>Quantity & Buy</th>
                                </tr>
                            </thead>
                            <tbody>
                              	<c:forEach items="${productList}" var="item">
										<tr>
											<td><c:out value="${item.productName}" /></td>
											<td><c:out value="${item.genericName}" /></td>
											<td><c:out value="${item.productStrength}" /></td>
											<td><c:out value="${item.productForm}" /></td>
											<td><c:out value="${item.productPackaging}" /></td>
											<td><c:out value="${item.productDescription}" /></td>
											<td><c:out value="${item.productImage}" /></td>
											<td><c:out value="${item.quantity}" /></td>
											<td><c:out value="${item.priceSet}" /></td>													
										</tr>
								</c:forEach>
                            </tbody>
                        </table>
                    </div>
      </div>
      </div>
      <br>
    <div>
        <div class="container">
            <div class="row">
                <div class="col-md-4"><button class="btn btn-primary" type="button" style="color:rgb(255,255,255);background-color:#56c5ff;width:212px;">Update Changes<br></button></div>
            </div>
        </div>
    </div>
    <div>
    </div>
	
	
	
    <div class="footer-basic">
        <footer>
            <div class="social"><a href="#"><i class="icon ion-social-instagram"></i></a><a href="#"><i class="icon ion-social-snapchat"></i></a><a href="#"><i class="icon ion-social-twitter"></i></a><a href="#"><i class="icon ion-social-facebook"></i></a></div>
            <ul class="list-inline">
                <li class="list-inline-item"><a href="#">Home</a></li>
                <li class="list-inline-item"><a href="#">Services</a></li>
                <li class="list-inline-item"><a href="#">About</a></li>
                <li class="list-inline-item"><a href="#">Terms</a></li>
                <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
            </ul>
            <p class="copyright">Company Name © 2017</p>
        </footer>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/Gallery.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Swiper/3.3.1/js/swiper.jquery.min.js"></script>
    <script src="assets/js/index.js"></script>
    <script src="assets/js/multi-item-carousel.js"></script>
    <script src="assets/js/Simple-Slider1.js"></script>
        <script type="text/javascript"  src="js/jquery-1.8.3.js"></script>
<script type="text/javascript"  src="js/data_table/jquery.dataTables.min.js"></script>
<script type="text/javascript"  src="js/data_table/jquery.dataTables.plugins.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#OTC").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": true, "sScrollX": "100%",
                "bScrollCollapse": true
            });
        });
        </script>
        <script type="text/javascript">
        $(document).ready(function () {
            $("#Prescription").dataTable({
                "sPaginationType": "full_numbers",
                "bJQueryUI": false
            });
        });
        </script>
</body>

</html>