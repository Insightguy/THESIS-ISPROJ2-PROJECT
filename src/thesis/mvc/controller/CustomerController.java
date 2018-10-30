package thesis.mvc.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thesis.mvc.implement.OrderDetailImplement;
import thesis.mvc.implement.OrderImplement;
import thesis.mvc.implement.PharmacyImplement;
import thesis.mvc.model.Order;
import thesis.mvc.model.Pharmacy;
import thesis.mvc.pageaction.SearchAction;
import thesis.mvc.utility.DBUtility;

@WebServlet("/CustomerController")
public class CustomerController extends HttpServlet{
	
	private Connection conn;

	public CustomerController() {
		conn = DBUtility.getConnection();
	}
	
	private static final long serialVersionUID = 1L;
	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//Ensures that the person can select what he/she wants to buy
		String forward;
		HttpSession session = request.getSession();
		
		String action = "";
		if (request.getParameter("action") != null) {
			action = request.getParameter( "action" );
		}

		if(action.isEmpty()) {
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else if(action.equalsIgnoreCase("GoToCatalog")) {
			int PharmaID = Integer.parseInt(request.getParameter( "PharmaID" ));
			if (session.getAttribute("CatalogType") == null || session.getAttribute("CatalogType") == "Prescription" || (int) session.getAttribute("SelectedPharmacyID") != PharmaID) {
				session.setAttribute("CatalogType", "Regular");
				session.removeAttribute("SelectedPharmacy");
				session.removeAttribute("Order");
				session.removeAttribute("OrderDetails");
				session.removeAttribute("CartList");
			}
			session.setAttribute("CatalogType", "Regular");
			session.setAttribute("productList", new SearchAction().GeneralListing(PharmaID) );
			session.setAttribute("SelectedPharmacy", new PharmacyImplement().getPharmacyById(PharmaID) );
			session.setAttribute("SelectedPharmacyID", PharmaID);
			response.sendRedirect(request.getContextPath() + "/CatalogBasic.jsp");
		} else if(action.equalsIgnoreCase("GoToCatalogPrescription")) {
			int PharmaID = Integer.parseInt(request.getParameter( "PharmaID" ));
			if (session.getAttribute("CatalogType") == null || session.getAttribute("CatalogType") == "Regular" || (int) session.getAttribute("SelectedPharmacyID") != PharmaID) {
				session.setAttribute("CatalogType", "Prescription");
				session.removeAttribute("SelectedPharmacy");
				session.removeAttribute("Order");
				session.removeAttribute("OrderDetails");
				session.removeAttribute("CartList");
			}
			session.setAttribute("CatalogType", "Prescription");
			session.setAttribute("productList", new SearchAction().GeneralListing(PharmaID) );
			session.setAttribute("SelectedPharmacy", new PharmacyImplement().getPharmacyById(PharmaID) );
			session.setAttribute("SelectedPharmacyID", PharmaID);
			response.sendRedirect(request.getContextPath() + "/CatalogAdvanced.jsp");
		} else if (action.equalsIgnoreCase("GoToOrders")) {
			session.setAttribute("OrderHistory", new OrderImplement().getOrderByCustomerId((int)session.getAttribute("Customer")));
			session.setAttribute("OrderDetailHistory", new OrderDetailImplement().getOrderDetail() );
			response.sendRedirect(request.getContextPath() + "/CustomerOrders.jsp");
		} else if (action.equalsIgnoreCase("GoToPending")) {
			session.setAttribute("OrderHistory", new OrderImplement().getOrderByCustomerId((int)session.getAttribute("Customer")));
			session.setAttribute("OrderDetailHistory", new OrderDetailImplement().getOrderDetail() );
			response.sendRedirect(request.getContextPath() + "/CustomerPending.jsp");
		} else if (action.equalsIgnoreCase("CancelOrder")) {
			Order cancelledOrder = new OrderImplement().getOrderById( Integer.parseInt(request.getParameter("OrderID")));
			cancelledOrder.setOrderStatus("CANCELLED");
			new OrderImplement().updateOrder( cancelledOrder );
			session.setAttribute("OrderHistory", new OrderImplement().getOrderByCustomerId((int)session.getAttribute("Customer")));
			session.setAttribute("OrderDetailHistory", new OrderDetailImplement().getOrderDetail() );
			response.sendRedirect(request.getContextPath() + "/CustomerPending.jsp");
		}
		
    }

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}
}
