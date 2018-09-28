package thesis.mvc.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thesis.mvc.implement.AdminImplement;
import thesis.mvc.implement.BranchImplement;
import thesis.mvc.implement.CourierServiceImplement;
import thesis.mvc.implement.CustomerImplement;
import thesis.mvc.implement.DispatcherImplement;
import thesis.mvc.implement.OrderImplement;
import thesis.mvc.implement.PharmacistImplement;
import thesis.mvc.implement.PharmacyImplement;
import thesis.mvc.model.Customer;
import thesis.mvc.utility.EncryptionFunction;

@WebServlet("/HomeController")
public class HomeController  extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		int Switch;
		
		if (session.getAttribute("userAccess") != null) {
			Switch = (int) session.getAttribute("userAccess");
		} else {
			Switch = 0;
		}
		
		EncryptionFunction encryptionFunction = new EncryptionFunction();
		PharmacyImplement pharmacyImplement = new PharmacyImplement();
		
		switch(Switch) {
		case 1:
			//Customer Information
			//CustomerImplement customerImplement = new CustomerImplement();
			//request.setAttribute("CustomerDetails", session.getAttribute("Customer") );
			//Listing pending orders
			//OrderImplement orderImplement = new OrderImplement();
			//request.setAttribute( "PurchasePending", (Customer) session.getAttribute("Customer"));
			//List all the pharmacies
			request.setAttribute("PharmcyList", pharmacyImplement.getAllPharmacys());
			//Remember to add a report button
			break;
		case 2:
			//DispatcherImplement dispatcherImplement = new DispatcherImplement();
			//request.setAttribute("DispatcherDetails", session.getAttribute("Dispatcher") );
			//Dispatcher
			break;
		case 3:
			//PharmacistImplement pharmacistImplement = new PharmacistImplement();
			//request.setAttribute("PharmacistDetails", session.getAttribute("Pharmacist") );
			//PharmacyImplement pharmacyImplement1 = new PharmacyImplement();
			//request.setAttribute("PharmcyofPharmacist", pharmacyImplement1.getPharmacyById(1));
			//Pharmacist
			break;
		case 4:
			//AdminImplement adminImplement = new AdminImplement();
			//request.setAttribute("AdminDetails", adminImplement.getAdminById( (int) session.getAttribute("AdminID") ) );
			//Admin
			//AdminImplement adminImplement = new AdminImplement();
			//session.setAttribute("AdminList", adminImplement.getAllAdmins());
			//Users
			request.setAttribute("CustomerList", new CustomerImplement().getAllCustomers());
			request.setAttribute("DispatcherList", new DispatcherImplement().getAllDispatchers());
			request.setAttribute("PharmacistList", new PharmacistImplement().getAllPharmacists());
			request.setAttribute("AdminList", new AdminImplement().getAllAdmins());
			//Items
			request.setAttribute("PharmcyList", pharmacyImplement.getAllPharmacys());
			request.setAttribute("BranchList", new BranchImplement().getAllBranch());
			request.setAttribute("CourierList", new CourierServiceImplement().getAllCourierService());
			
			break;
		default:
			break;
		}
		RequestDispatcher view = request.getRequestDispatcher( "/home.jsp" );
		view.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher view;
		view = request.getRequestDispatcher( "/invalidError.jsp" );
		//request.setAttribute("customers", dao.getAllCustomers());
		view.forward(request, response);
	}
}
