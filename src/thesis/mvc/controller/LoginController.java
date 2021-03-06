package thesis.mvc.controller;

import java.io.IOException;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.mail.Session;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thesis.mvc.implement.AdminImplement;
import thesis.mvc.implement.CustomerImplement;
import thesis.mvc.implement.DispatcherImplement;
import thesis.mvc.implement.LoginImplement;
import thesis.mvc.implement.OrderImplement;
import thesis.mvc.implement.PharmacistImplement;
import thesis.mvc.model.Login;
import thesis.mvc.pageaction.LoginAction;
import thesis.mvc.utility.DBUtility;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	
	private Connection conn;
	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	public LoginController() {
		conn = DBUtility.getConnection();
	}
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward;
		HttpSession session = request.getSession();
		
		//Logout
		if ((int) session.getAttribute("userID") > 0) {
			LoginAction loginAction = new LoginAction();
			loginAction.logoutUser((int)session.getAttribute("userID"), (String)session.getAttribute("username"));
			switch((int) session.getAttribute("userAccess")) {
			case 1: session.removeAttribute("CustomerID"); break;
			case 2: session.removeAttribute("DispatcherID"); break;
			case 3: session.removeAttribute("PharmacistID"); break;
			case 4:	session.removeAttribute("AdminID");	break;
			}
			session.removeAttribute("userID");
			session.removeAttribute("username");
			session.removeAttribute("userAccess");
		}
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Initializing
		LoginAction loginAction = new LoginAction();
		HttpSession session = request.getSession();
		RequestDispatcher view;
		int Attempt = 0;
		
		if (session.getAttribute( "LoginTry" ) != null) {
			Attempt = (int) session.getAttribute( "LoginTry" );
		}
		//Username and password check
		String Username = request.getParameter( "Username" );
		String Password = request.getParameter( "Password" );
		int LoginID = loginAction.loginUser(Username, Password);
		
		//Capcha Check
		//boolean Capcha = request.getParameter( "CapchaLogin" ) == "true";
		
		//Retry Check
		if (Attempt != 0) {
			session.setAttribute("LoginTry", Attempt + 1);
		} else {
			session.setAttribute("LoginTry", 1);
		}
		
		if (LoginID > 0 && Attempt <= 6) {
			
			//Set ID
			session.setAttribute("userID", LoginID);
			
			//Set Username
			LoginImplement LoginImp = new LoginImplement();
			Login login = LoginImp.getLoginByID(LoginID);
			session.setAttribute("username", login.getUsername()); 
			
			//Set Access Level
			int AL = loginAction.checkUserType(LoginID);
			session.setAttribute("userAccess", AL);

			switch(AL) {
			case 1:
				//Customer
				CustomerImplement customerImplement = new CustomerImplement();
				session.setAttribute("Customer", customerImplement.getCustomerByUserId(LoginID).getCustomerID());
				break;
			case 2:
				//Dispatcher
				DispatcherImplement dispatcherImplement = new DispatcherImplement();
				session.setAttribute("Dispatcher", dispatcherImplement.getDispatcherByUserID(LoginID).getDispatcherID());
				break;
			case 3:
				//Pharmacist
				PharmacistImplement pharmacistImplement = new PharmacistImplement();
				session.setAttribute("Pharmacist", pharmacistImplement.getPharmacistByUserId(LoginID).getPharmacistID());
				break;
			case 4:
				//Admin
				AdminImplement adminImplement = new AdminImplement();
				session.setAttribute("Admin", adminImplement.getAdminByUserId(LoginID).getAdminID());
				break;
			}
			session.removeAttribute("LoginTry");
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} else if (Attempt >= 3) {
			session.setAttribute("LoginWarning", 1);
		} else {
			view = request.getRequestDispatcher( "/AccountRecovery.jsp" );
			view.forward(request, response);
		}
	}
}
