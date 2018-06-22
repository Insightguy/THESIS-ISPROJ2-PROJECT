package thesis.mvc.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import thesis.mvc.pageaction.ApprovalAction;
import thesis.mvc.pageaction.FilterAction;
import thesis.mvc.pageaction.PurchaseAction;
import thesis.mvc.utility.DBUtility;

@WebServlet("/ReportController")
public class ReportController {
	
	private Connection conn;
	
	public ReportController() {
		conn = DBUtility.getConnection();
	}

	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward;
		int BranchID = Integer.parseInt( request.getParameter( "BranchID" ) );
		String Pharma = request.getParameter( "BranchID" );
		HttpSession session = request.getSession();
		forward = "/Catalog.jsp";
		
		String Test = "SELECT product.ProductName, product.GenericName, product.ProductStrength, product.ProductForm, product.ProductPackaging, product.ProductDescription, product.ProductImage, stocks.Quantity, stocksprice.PriceSet FROM stocks INNER JOIN stocksprice ON stocksprice.StockID = stocks.StockID INNER JOIN product ON stocks.ProductID = product.ProductID WHERE stocksprice.IsCurrent = 1 AND stocks.BranchID = ?";
		
		RequestDispatcher view = request.getRequestDispatcher( forward );
		view.forward(request, response);
		
	}
	

	//FilterAction filteraction = new FilterAction();
	//request.setAttribute( "SearchList", filteraction.ProductListing(1) );
	//session.setAttribute("orderPharmacistCheck", approvalAction.getOrder() );
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
