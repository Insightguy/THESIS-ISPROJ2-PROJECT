package thesis.mvc.implement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import thesis.mvc.dataobjects.CustomerDAO;
import thesis.mvc.model.Customer;
import thesis.mvc.utility.DBUtility;

public class CustomerImplement implements CustomerDAO{
	
	private Connection conn;

	public CustomerImplement() {
		conn = DBUtility.getConnection();
	}

	@Override
	public void addCustomer(Customer customer) {
		try {
			String query = "INSERT INTO Customer (UserID, CustomerName, Address, Email, IsSeniorCitizen, SeniorCitizenID, ContactNumber) VALUES (?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt( 1, customer.getUserID() );
			preparedStatement.setString( 2, customer.getCustomerName() );
			preparedStatement.setString( 3, customer.getAddress() );
			preparedStatement.setString( 4, customer.getEmail() );
			preparedStatement.setBoolean( 5, customer.isIsSeniorCitizen() );
			preparedStatement.setString( 6, customer.getSeniorCitizenID() );
			preparedStatement.setInt( 7, customer.getContactNumber() );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteCustomer(int customerId) {
		try {
			String query = "DELETE FROM Customer where CustomerID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt( 1, customerId );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateCustomer(Customer customer) {
		try {
			String query = "UPDATE Customer SET UserID=?, CustomerName=?, Address=?, Email=?, IsSeniorCitizen=?, SeniorCitizenID=?, ContactNumber=? WHERE CustomerID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt( 1, customer.getUserID() );
			preparedStatement.setString( 2, customer.getCustomerName() );
			preparedStatement.setString( 3, customer.getAddress() );
			preparedStatement.setString( 4, customer.getEmail() );
			preparedStatement.setBoolean( 5, customer.isIsSeniorCitizen() );
			preparedStatement.setString( 6, customer.getSeniorCitizenID() );
			preparedStatement.setInt( 7, customer.getContactNumber() );
			preparedStatement.setInt( 8, customer.getCustomerID() );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery( "SELECT * FROM Customer" );
			while( resultSet.next() ) {
				Customer customer = new Customer();
				customer.setCustomerID( resultSet.getInt( "CustomerID" ) );
				customer.setUserID( resultSet.getInt( "UserID" ) );
				customer.setCustomerName( resultSet.getString( "CustomerName" ) );
				customer.setAddress( resultSet.getString( "Address" ) );
				customer.setEmail( resultSet.getString( "Email" ) );
				customer.setIsSeniorCitizen( resultSet.getBoolean( "IsSeniorCitizen" ) );
				customer.setSeniorCitizenID( resultSet.getString( "SeniorCitizenID" ) );
				customer.setContactNumber( resultSet.getInt( "ContactNumber" ) );
				customers.add(customer);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	@Override
	public Customer getCustomerById(int customerId) {
		Customer customer = new Customer();
		try {
			String query = "SELECT * FROM Customer WHERE CustomerID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, customerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				customer.setCustomerID( resultSet.getInt( "CustomerID" ) );
				customer.setUserID( resultSet.getInt( "UserID" ) );
				customer.setCustomerName( resultSet.getString( "CustomerName" ) );
				customer.setAddress( resultSet.getString( "Address" ) );
				customer.setEmail( resultSet.getString( "Email" ) );
				customer.setIsSeniorCitizen( resultSet.getBoolean( "IsSeniorCitizen" ) );
				customer.setSeniorCitizenID( resultSet.getString( "SeniorCitizenID" ) );
				customer.setContactNumber( resultSet.getInt( "ContactNumber" ) );
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

}
