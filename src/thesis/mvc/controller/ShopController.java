package thesis.mvc.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import thesis.mvc.implement.CityListingImplement;
import thesis.mvc.implement.CustomerImplement;
import thesis.mvc.implement.PharmacyImplement;
import thesis.mvc.implement.PrescriptionImplement;
import thesis.mvc.implement.ProductImplement;
import thesis.mvc.implement.StocksPriceImplement;
import thesis.mvc.model.Customer;
import thesis.mvc.model.Order;
import thesis.mvc.model.OrderDetail;
import thesis.mvc.model.Pharmacy;
import thesis.mvc.model.Prescription;
import thesis.mvc.model.Product;
import thesis.mvc.pageaction.PurchaseAction;
import thesis.mvc.pageaction.PurchaseAction.CartList;
import thesis.mvc.pageaction.SearchAction;
import thesis.mvc.pageaction.ShopAction;
import thesis.mvc.utility.DBUtility;
import thesis.mvc.utility.EncryptionFunction;
import thesis.mvc.utility.SendEmail;

@WebServlet("/ShopController")
@MultipartConfig
public class ShopController extends HttpServlet {
	
	private Connection conn;

	public ShopController() {
		conn = DBUtility.getConnection();
	}
	
	private static final long serialVersionUID = 1L;
	private final String UPLOAD_DIRECTORY = "../../../../../../../../THESIS-ISPROJ2-PROJECT/WebContent/images/";
	//private final String UPLOAD_DIRECTORY = "C:/xampp/tomcat/webapps/Medelivery/images/";
	//private final String UPLOAD_DIRECTORY = "/C:/ISPROJ2/Medelivery/webapp/images/";
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
	   	SearchAction searchAction = new SearchAction();
	   	String forward = "/Catalog.jsp";

		int PharmaID = 0;
		if (session.getAttribute("PharmaID") != null) {
			PharmaID = (int) session.getAttribute("PharmaID");
			session.setAttribute("SelectedPharmacy", new PharmacyImplement().getPharmacyById(PharmaID));
		} else {
			//Go home
		}

	   	request.setAttribute( "productList", searchAction.GeneralListing(PharmaID) );
	   	request.setAttribute( "productPriceList", new StocksPriceImplement().getAllStocksPrice());
	   	
	   	RequestDispatcher view = request.getRequestDispatcher( forward );
	   	view.forward(request, response);
    }
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Boolean Test = ServletFileUpload.isMultipartContent(request);
		HttpSession session = request.getSession(true);
		List<OrderDetail> OrderDetails = new ArrayList<OrderDetail>();
		if (session.getAttribute("OrderDetails") != null) {
			OrderDetails = (List<OrderDetail>) session.getAttribute("OrderDetails");
		}
		
		String action = "";
		if (request.getParameter( "Action" ) != null) {
			action = request.getParameter( "Action" );	
		}
		
		if (action.equalsIgnoreCase("AddtoCart")) {
			//sets order and generates it if it does not exist
			Order order = (Order) session.getAttribute("Order");

			//Set the branch 
			Pharmacy selectedPharmacy = new Pharmacy();
			int PID = 0;
				try{
					selectedPharmacy = (Pharmacy) session.getAttribute("SelectedPharmacy");
					PID = selectedPharmacy.getPharmacyID();
				} catch (Exception e) {
					PID = Integer.valueOf( request.getParameter("SelectedPharmacy") );
					session.setAttribute("CatalogType", "Regular");
				}
			
			if (order == null) {
				order = new Order();
				Customer customer = new Customer();
				int CustomerID = (int) session.getAttribute("Customer");
 				customer = new CustomerImplement().getCustomerById(CustomerID);
 						//(Customer) session.getAttribute("Customer");
				
				int CusID = customer.getCustomerID();
				String Adr = customer.getCustomerStreet()
						+ ", " + customer.getCustomerBarangay()
						+ ", " + new CityListingImplement().getCityListingById(customer.getCityID()).getCityName()
						+ ", " + customer.getCustomerProvince();
				int CityID = customer.getCityID();
				boolean IS = customer.isIsSeniorCitizen();
				
				order.setCustomerID(CusID);
				order.setOrderAddress(Adr);
				order.setPharmacyID(PID);
				order.setCityID(CityID);
				order.setSeniorDiscount(IS);
				session.setAttribute("Order", order );
			}
			//ProductID & Quantity & Cost per unit
			int ProductID = Integer.valueOf( request.getParameter( "ProductID" ) );
			int Quantity = Integer.valueOf( request.getParameter( "Quantity" ) );
			double CostPerUnit = new PurchaseAction().getProductCost( ProductID, PID, order);
			
			//Takes the existing order detail if there is and adds the next order detail to there
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProductID(ProductID);
			orderDetail.setQuantity(Quantity);
			orderDetail.setCostPerUnit(CostPerUnit);
	        DecimalFormat df = new DecimalFormat("#.##");
			orderDetail.setTotalCost(Double.valueOf( df.format(CostPerUnit * Quantity) ) );
			OrderDetails.add( orderDetail );
			session.setAttribute("OrderDetails", OrderDetails );

			
			//Insert things into cartDetails
			ProductImplement productImplement = new ProductImplement();
			Product product = new Product();
			product = productImplement.getProductById(orderDetail.getProductID());
			
			List<CartList> cartlists = new ArrayList<CartList>();
			if (session.getAttribute("CartList") != null) {
				cartlists = (List<CartList>) session.getAttribute("CartList");
			}
			CartList cartlist = new PurchaseAction().new CartList();
			cartlist.setName(product.getProductName());
			cartlist.setDescription(product.getProductDescription());
			cartlist.setImage(product.getProductImage());
			cartlist.setSize(product.getProductPackaging());
			cartlist.setPrescription(product.isRXProduct());
			cartlist.setQuantity(orderDetail.getQuantity());
			cartlist.setUnitCost(orderDetail.getCostPerUnit());
			cartlist.setTotalCost(orderDetail.getTotalCost());
			cartlists.add(cartlist);
			session.setAttribute("CartList", cartlists );
			
			if (session.getAttribute("CatalogType") == null && request.getParameter( "FromCarosel" ) == null) {
				session.removeAttribute("Order");
				session.removeAttribute("OrderDetails");
				session.removeAttribute("CartList");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else if (session.getAttribute("CatalogType") == "Prescription") {
				session.setAttribute("CatalogType", "Prescription");
				session.setAttribute("productList", new SearchAction().GeneralListing(PID) );
				session.setAttribute("SelectedPharmacy", new PharmacyImplement().getPharmacyById(PID) );
				session.setAttribute("SelectedPharmacyID", PID);
				response.sendRedirect(request.getContextPath() + "/CatalogAdvanced.jsp");
			} else if (session.getAttribute("CatalogType") == "Regular" || request.getParameter( "FromCarosel" ).equalsIgnoreCase("FromCarosel") ){
				session.setAttribute("CatalogType", "Regular");
				session.setAttribute("productList", new SearchAction().GeneralListing(PID) );
				session.setAttribute("SelectedPharmacy", new PharmacyImplement().getPharmacyById(PID) );
				session.setAttribute("SelectedPharmacyID", PID);
				response.sendRedirect(request.getContextPath() + "/CatalogBasic.jsp");
			}
		} else if (action.equalsIgnoreCase("CheckoutOrder")) {
			Order order = (Order) session.getAttribute("Order");
			OrderDetails = (List<OrderDetail>) session.getAttribute("OrderDetails");
			List<CartList> cartList = (List<CartList>) session.getAttribute("CartList");
			SendEmail sendEmail = new SendEmail();
			CustomerImplement customerImplement = new CustomerImplement();
			int userID = (int) session.getAttribute("userID");
			
			double finalAmount = 0.0;
			for (OrderDetail orderDetail : OrderDetails) {
				finalAmount += orderDetail.getTotalCost();
			}
			
			if(order == null || OrderDetails.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else if (finalAmount <= 300.00) {
				session.setAttribute("message", "Orders must reach a minimum of 50 pesos");
				if (session.getAttribute("CatalogType") == "Regular") {
					response.sendRedirect(request.getContextPath() + "/CatalogBasic.jsp");
				} else if (session.getAttribute("CatalogType") == "Prescription") {
					response.sendRedirect(request.getContextPath() + "/CatalogAdvanced.jsp");
				}
			} else {
				String CustomerEmail = customerImplement.getCustomerByUserId(userID).getEmail();
				Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
				String redirect = new ShopAction().purchaseOrder(order, OrderDetails, "https://isproj2a.benilde.edu.ph" + request.getContextPath() + "/index.jsp");// request.getContextPath()
				DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd hh:mm a");
				sendEmail.send(CustomerEmail, "Reciept of transaction on " + dateFormat.format(CurrentDate), sendEmail.OrderEmail(order));
				session.setAttribute("CartlistReciept", cartList);
				session.setAttribute("orderReciept", order);
				session.setAttribute("ApproveChecker", false);
				session.removeAttribute("Order");
				session.removeAttribute("OrderDetails");
				session.removeAttribute("CartList");
				session.setAttribute("kick", redirect);
				response.sendRedirect(request.getContextPath() + "/kick.jsp");
				//response.sendRedirect("https://" + redirect);
			}
			//order.setPaymentMethod( request.getParameter( "orderPayment" ) );
			//order.setDateOrdered(today);

		} else if (action.equalsIgnoreCase("CheckoutOrderPrescription")) {			
			int UID = (int) session.getAttribute("userID");
			
			CustomerImplement customerImplement = new CustomerImplement();
			int CID = customerImplement.getCustomerByUserId(UID).getCustomerID();
			
			int prescriptionID = 0;
			if(ServletFileUpload.isMultipartContent(request)){
	            try {

	            	Part filePart = request.getPart("file");
					String name = "Prescription" + Calendar.getInstance().getTime().getTime();
					//String end = filePart.getContentType();
					String end = filePart.getContentType();
					if (end.startsWith("image")) {
						String imageType = end.replace("image/", "");
						name = name + "." + imageType;
						String DbaseName = new EncryptionFunction().encrypt(name);
						String AFileName = name;
				        System.out.println(DbaseName);
				        System.out.println(AFileName);
						Prescription prescription = new Prescription();
						prescription.setCustomerID(CID);
						prescription.setPrescription(DbaseName);
						prescription.setPermissionStatus("PENDING");
						prescription.setPharmacistID(0);
						prescription.setRemark("");
						prescriptionID = new PrescriptionImplement().addPrescription(prescription);
				        System.out.println("PrescriptionID: " + prescriptionID);
				        //System.out.println(UPLOAD_DIRECTORY + File.separator + AFileName + "." + imageType);
				        //System.out.println(UPLOAD_DIRECTORY +"|"+ File.separator +"|"+ AFileName +"|"+ "." +"|"+ imageType);
						filePart.write(UPLOAD_DIRECTORY + File.separator + AFileName);
						System.out.println( "File Uploaded Successfully: " + UPLOAD_DIRECTORY + File.separator + AFileName);
					} else {
						System.out.println( "File Uploaded is not an image!");
					}
	            	
	            } catch (Exception ex) {
	            	System.out.println( "File Upload Failed due to " + ex);
	            }
	            
	        }
			Order order = new Order();
			order = (Order) session.getAttribute("Order");
			order.setPrescriptionID(prescriptionID);
			
			OrderDetails = (List<OrderDetail>) session.getAttribute("OrderDetails");
			List<CartList> cartList = (List<CartList>) session.getAttribute("CartList");
			DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd hh:mm a");
			SendEmail sendEmail = new SendEmail();
			int userID = (int) session.getAttribute("userID");
			
			double finalAmount = 0.0;
			for (OrderDetail orderDetail : OrderDetails) {
				finalAmount += orderDetail.getTotalCost();
			}
			
			if(order == null || OrderDetails.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else if (finalAmount <= 300.00) {
				session.setAttribute("message", "Orders must reach a minimum of 50 pesos.");
				if (session.getAttribute("CatalogType") == "Regular") {
					response.sendRedirect(request.getContextPath() + "/CatalogBasic.jsp");
				} else if (session.getAttribute("CatalogType") == "Prescription") {
					response.sendRedirect(request.getContextPath() + "/CatalogAdvanced.jsp");
				}
			}
			if(order == null || OrderDetails.isEmpty()) {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
			} else {
				String CustomerEmail = customerImplement.getCustomerByUserId(userID).getEmail();
				Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
				String redirect = new ShopAction().purchaseOrder(order, OrderDetails, "https://isproj2a.benilde.edu.ph" + request.getContextPath() + "/index.jsp");// request.getContextPath()
				sendEmail.send(CustomerEmail, "Reciept of transaction on " + dateFormat.format(CurrentDate), sendEmail.OrderEmail(order));
				session.setAttribute("CartlistReciept", cartList);
				session.setAttribute("orderReciept", order);
				session.setAttribute("ApproveChecker", false);
				session.removeAttribute("Order");
				session.removeAttribute("OrderDetails");
				session.removeAttribute("CartList");
				session.setAttribute("kick", redirect);
				response.sendRedirect(request.getContextPath() + "/kick.jsp");
			}
			
			//response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
			
	}
	
	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

}
