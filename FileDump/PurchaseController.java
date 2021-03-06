package thesis.mvc.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thesis.mvc.implement.BranchImplement;
import thesis.mvc.implement.CustomerImplement;
import thesis.mvc.implement.OrderDetailImplement;
import thesis.mvc.implement.OrderImplement;
import thesis.mvc.implement.ProductImplement;
import thesis.mvc.model.Branch;
import thesis.mvc.model.Customer;
import thesis.mvc.model.Order;
import thesis.mvc.model.OrderDetail;
import thesis.mvc.model.Product;
import thesis.mvc.pageaction.ApprovalAction;
import thesis.mvc.pageaction.PurchaseAction;
import thesis.mvc.pageaction.PurchaseAction.CartList;
import thesis.mvc.pageaction.SearchAction;
import thesis.mvc.utility.DBUtility;
import thesis.mvc.utility.SendEmail;

@WebServlet("/PurchaseController")
public class PurchaseController extends HttpServlet {
	
	private Connection conn;

	public PurchaseController() {
		conn = DBUtility.getConnection();
	}
	
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Ensures that the person can select what he/she wants to buy
		String forward;
		HttpSession session = request.getSession();
		
		String action = "";
		if (request.getParameter("action") != null && !request.getParameter("action").isEmpty()) {
			action = request.getParameter( "action" );			
		}
		
		int PharmaID = 0;
		if (session.getAttribute("PharmaID") != null) {
			PharmaID = (int) session.getAttribute("PharmaID");
			BranchImplement branchImplement = new BranchImplement();
			session.setAttribute("SelectedBranch", branchImplement.getBranchById(PharmaID));
		} else if (request.getParameter("PharmaID") != null && !request.getParameter("PharmaID").isEmpty()){
			PharmaID = Integer.parseInt( request.getParameter( "PharmaID" ) );
			BranchImplement branchImplement = new BranchImplement();
			session.setAttribute("SelectedBranch", branchImplement.getBranchById(PharmaID));
		}
		
		int access = 0;
		if (session.getAttribute("userAccess") != null) {
			access = (int) session.getAttribute("userAccess");	
		}
		
		
		boolean test = true;
    	
		if (access == 1) {
			if (isInteger(action) && Integer.parseInt(action) > 0) {
				//Get the order
				OrderImplement orderImplement = new OrderImplement();
	    		Order order = orderImplement.getOrderById(Integer.parseInt(action));
	    		if (order.getOrderStatus().equalsIgnoreCase("APPROVED")) {
	    			session.setAttribute("ApproveChecker", true);
	    		} else {
	    			session.setAttribute("ApproveChecker", false);
	    		}
	    		//Get the Details
	    		OrderDetailImplement orderDetailImplement = new OrderDetailImplement();
	    		List<OrderDetail> OrderDetails = new ArrayList<OrderDetail>();
				OrderDetails = orderDetailImplement.getspecificOrderDetail(Integer.parseInt(action));
				
				//Ready the Cart List
				PurchaseAction purchaseAction = new PurchaseAction();
				List<CartList> cartlists = new ArrayList<CartList>();
				ProductImplement productImplement = new ProductImplement();
				for (OrderDetail orderDetail : OrderDetails) {
					CartList cartlist = purchaseAction.new CartList();
					//Product Info
					Product product = productImplement.getProductById(orderDetail.getProductID());
					cartlist.setName(product.getProductName());
					cartlist.setDescription(product.getProductDescription());
					cartlist.setImage(product.getProductImage());
					cartlist.setSize(product.getProductPackaging());
					cartlist.setPrescription(product.isRXProduct());
					
					//Other Details
					cartlist.setQuantity(orderDetail.getQuantity());
					cartlist.setUnitCost(orderDetail.getCostPerUnit());
					cartlist.setTotalCost(orderDetail.getTotalCost());
					cartlists.add(cartlist);
				}
				/*
				PurchaseAction purchaseAction = new PurchaseAction();
				ProductImplement productImplement = new ProductImplement();
				Product product = new Product();
				product = productImplement.getProductById(orderDetail.getProductID());
				
				List<CartList> cartlists = new ArrayList<CartList>();
				CartList cartlist = purchaseAction.new CartList();
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

				CartList cartlist = purchaseAction.new CartList();
				SendEmail sendEmail = new SendEmail();
				CustomerImplement customerImplement = new CustomerImplement();
				*/
				
				//Set The UserID
				session.setAttribute("orderReciept", order);
				session.setAttribute("CartlistReciept", cartlists);
				forward = "/Checkout.jsp";
	    		RequestDispatcher view = request.getRequestDispatcher( forward );
	    		view.forward(request, response);
			} else {
	    		SearchAction searchAction = new SearchAction();
	    		forward = "/Catalog.jsp";
	    		request.setAttribute( "productList", searchAction.GeneralListing(PharmaID) );
	    		RequestDispatcher view = request.getRequestDispatcher( forward );
	    		view.forward(request, response);
			}
    		
    	} else if (access == 3) {
    		if (action.equalsIgnoreCase("Approve")) {
        		PurchaseAction purchaseAction = new PurchaseAction();
        		purchaseAction.pharmacistApproval( Integer.parseInt( request.getParameter( "orderID" ) ), true );
        	} else if (action.equalsIgnoreCase("Reject")) {
        		PurchaseAction purchaseAction = new PurchaseAction();
        		purchaseAction.pharmacistApproval( Integer.parseInt( request.getParameter( "orderID" ) ) , false );
        	} if (PharmaID != 0) {
	    		ApprovalAction approvalAction = new ApprovalAction();
	    		session.setAttribute("orderPharmacistCheck", approvalAction.getRegularOrder(PharmaID) );
	    		forward = "PharmacistPage.jsp";
	    		RequestDispatcher view = request.getRequestDispatcher( forward );
	    		view.forward(request, response);
        	} else {
				response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
    	} else {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
    	}
		
		
		/*
		 String forward;
		int BranchID = Integer.parseInt( request.getParameter( "BranchID" ) );
		HttpSession session = request.getSession();
		forward = "/Catalog.jsp";
		
		SearchAction searchAction = new SearchAction();
		ProductList productList	= searchAction.GeneralListing(BranchID);
		
		RequestDispatcher view = request.getRequestDispatcher( forward );
		view.forward(request, response);
		 */
	}
    
	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward;
		int ProductID = 0;
		int Quantity = 0;
		double CostPerUnit = 0.0;
    	conn = DBUtility.getConnection();
		String action = "";
		if (request.getParameter( "Action" ) != null) {
			action = request.getParameter( "Action" );	
		}
		HttpSession session = request.getSession(true);
		List<OrderDetail> OrderDetails = new ArrayList<OrderDetail>();
		PurchaseAction purchaseAction = new PurchaseAction();
		Boolean SurgeCheck;
		
		//Safety measure
		if (session.getAttribute("userID") != null && session.getAttribute("SelectedBranch") != null) {
			SurgeCheck = true;		
		} else if (action.equalsIgnoreCase("OrderPay")) {
			SurgeCheck = true;		
		} else {
			SurgeCheck = false;
		}

		if(action.equalsIgnoreCase("OrderPay")) {
			int orderID =Integer.parseInt(request.getParameter("OrdertoUpdate"));
			OrderImplement orderImplement = new OrderImplement();
			orderImplement.updateOrderPayment( orderID, request.getParameter("Payment"));
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		
		} else if(action.equalsIgnoreCase("Addtocart") && SurgeCheck) {
			
			//sets order and generates it if it does not exist
			Order order = (Order) session.getAttribute("Order");
			
			if(order == null) { // || order.getBranchID() != Current Branch
				int UID = (int) session.getAttribute("userID");
				CustomerImplement customerImplement = new CustomerImplement();
				Customer customer = new Customer();
				customer = customerImplement.getCustomerByUserId(UID);
				int CusID = customer.getCustomerID();
				String ADD = customer.getAddress();
				boolean SID = customer.isIsSeniorCitizen();
				int CID = customer.getCityID();
				Branch SelectedBranch = new Branch();
				SelectedBranch = (Branch) session.getAttribute("SelectedBranch");
				int BID = SelectedBranch.getBranchID();
				session.setAttribute("OrderDetails", OrderDetails );
				
				order = purchaseAction.setInitalOrder(CusID, ADD, SID, CID, BID);
				session.setAttribute("Order", order );
				
				//ProductID & Quantity & Cost per unit
				ProductID = Integer.valueOf( request.getParameter( "ProductID" ) );
				Quantity = Integer.valueOf( request.getParameter( "Quantity" ) );
				CostPerUnit = purchaseAction.getProductCost( ProductID, BID, order );
				
				//Takes the existing order detail if there is and adds the next order detail to there
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProductID(ProductID);
				orderDetail.setQuantity(Quantity);
				orderDetail.setCostPerUnit(CostPerUnit);
				orderDetail.setTotalCost( Math.round(CostPerUnit * Quantity * 100) / 100 );
				OrderDetails.add( orderDetail );
				session.setAttribute("OrderDetails", OrderDetails );

				
				//Insert things into cartDetails
				ProductImplement productImplement = new ProductImplement();
				Product product = new Product();
				product = productImplement.getProductById(orderDetail.getProductID());
				
				List<CartList> cartlists = new ArrayList<CartList>();
				CartList cartlist = purchaseAction.new CartList();
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
				
				//Refreshes and goes back to the cart
				forward = "/Cart.jsp";
				//forward = "/A-test-customerpurchasecheckout.jsp";
				RequestDispatcher view = request.getRequestDispatcher( forward );
				view.forward(request, response);
			} else {
				//ProductID & Quantity & Cost per unit
				ProductID = Integer.valueOf( request.getParameter( "ProductID" ) );
				Quantity = Integer.valueOf( request.getParameter( "Quantity" ) );
				CostPerUnit = purchaseAction.getProductCost( ProductID, 1, order );
				
				//Takes the existing order detail if there is and adds the next order detail to there
				OrderDetail orderDetail = new OrderDetail();
				orderDetail.setProductID(ProductID);
				orderDetail.setQuantity(Quantity);
				orderDetail.setCostPerUnit(CostPerUnit);
				orderDetail.setTotalCost(CostPerUnit * Quantity);
				OrderDetails = (List<OrderDetail>) session.getAttribute("OrderDetails");
				OrderDetails.add( orderDetail );
				session.setAttribute("OrderDetails", OrderDetails );
				
				//Insert things into cartDetails
				ProductImplement productImplement = new ProductImplement();
				Product product = new Product();
				product = productImplement.getProductById(orderDetail.getProductID());
				
				List<CartList> cartlists = (List<CartList>) session.getAttribute("CartList");
				CartList cartlist = purchaseAction.new CartList();
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
				
				//Refreshes and goes back to the cart
				forward = "/Cart.jsp";
				//forward = "/A-test-customerpurchasecheckout.jsp";
				RequestDispatcher view = request.getRequestDispatcher( forward );
				view.forward(request, response);
			}
			
		} else if (action.equalsIgnoreCase("Checkout") && SurgeCheck) {
			
			Order order = (Order) session.getAttribute("Order");
			OrderDetails = (List<OrderDetail>) session.getAttribute("OrderDetails");
			List<CartList> cartList = (List<CartList>) session.getAttribute("CartList");
			SendEmail sendEmail = new SendEmail();
			CustomerImplement customerImplement = new CustomerImplement();
			int userID = (int) session.getAttribute("userID");
			
			String CustomerEmail = customerImplement.getCustomerByUserId(userID).getEmail();

			Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
			purchaseAction.purchaseOrder(order, OrderDetails);
			sendEmail.send(CustomerEmail, "Reciept of transaction on " + CurrentDate, "This is a test message");
			if(order == null || OrderDetails.isEmpty()) {
				forward = "/index.jsp"; //or an error page
			} else {
				session.setAttribute("CartlistReciept", cartList);
				session.setAttribute("orderReciept", order);
				session.setAttribute("ApproveChecker", false);
				session.removeAttribute("Order");
				session.removeAttribute("OrderDetails");
				session.removeAttribute("CartList");
				forward = "Checkout.jsp";
			}
			//order.setOrderAddress( request.getParameter( "orderAddress" ) );
			//order.setPaymentMethod( request.getParameter( "orderPayment" ) );
			//order.setDateOrdered(today);
			RequestDispatcher view = request.getRequestDispatcher( forward );
			view.forward(request, response);

		} else if (action.equalsIgnoreCase("PrescriptionCheckout") && SurgeCheck) {
			/*
			Order order = (Order) session.getAttribute("Order");
			int UID = (int) session.getAttribute("userID");
			CustomerImplement customerImplement = new CustomerImplement();
			Customer customer = new Customer();
			customer = customerImplement.getCustomerById(UID);
			String ADD = customer.getAddress();
			boolean SID = customer.isIsSeniorCitizen();
			int CID = customer.getCityID();
			Branch SelectedBranch = new Branch();
			SelectedBranch = (Branch) session.getAttribute("SelectedBranch");
			int BID = SelectedBranch.getBranchID();
			session.setAttribute("OrderDetails", OrderDetails );
			
			order = purchaseAction.setInitalOrder(UID, ADD, SID, CID, BID);
			session.setAttribute("Order", order );
			
			//ProductID & Quantity & Cost per unit
			ProductID = Integer.valueOf( request.getParameter( "ProductID" ) );
			Quantity = Integer.valueOf( request.getParameter( "Quantity" ) );
			CostPerUnit = purchaseAction.getProductCost( ProductID, BID, order);
			
			//Takes the existing order detail if there is and adds the next order detail to there
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setProductID(ProductID);
			orderDetail.setQuantity(Quantity);
			orderDetail.setCostPerUnit(CostPerUnit);
			orderDetail.setTotalCost( Math.round(CostPerUnit * Quantity * 100) / 100 );
			OrderDetails.add( orderDetail );
			session.setAttribute("OrderDetails", OrderDetails );

			
			//Insert things into cartDetails
			ProductImplement productImplement = new ProductImplement();
			Product product = new Product();
			product = productImplement.getProductById(orderDetail.getProductID());
			
			List<CartList> cartlists = new ArrayList<CartList>();
			CartList cartlist = purchaseAction.new CartList();
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
			
			//Refreshes and goes back to the cart
			forward = "/Cart.jsp";
			//forward = "/A-test-customerpurchasecheckout.jsp";
			 */

			forward = "/Cart.jsp";
			RequestDispatcher view = request.getRequestDispatcher( forward );
			view.forward(request, response);
		} else {
			forward = "/index.jsp";
			RequestDispatcher view = request.getRequestDispatcher( forward );
			view.forward(request, response);
		}
		if (!SurgeCheck) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
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
