package thesis.mvc.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import thesis.mvc.dataobjects.OrderDAO;
import thesis.mvc.model.Order;
import thesis.mvc.utility.DBUtility;

public class OrderImplement implements OrderDAO{
	
	private Connection conn;

	public OrderImplement() {
		conn = DBUtility.getConnection();
	}

	@Override
	public int addOrder(Order order) {
		try {
			Timestamp CurrentDate = new Timestamp(Calendar.getInstance().getTime().getTime());
			String query = "INSERT INTO `order`(`CustomerID`, `DeliveryID`, `PharmacistID`, `PharmacyID`, `BranchID`, `CityID`, `PrescriptionID`, `OrderAddress`, `DateOrdered`, `DateProcessed`, `DateDelivered`, `OrderType`, `OrderStatus`, `SeniorDiscount`, `PaymentMethod`, `ActualCost`, `PayMayaID`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement( query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt( 1, order.getCustomerID() );
			preparedStatement.setInt( 2, order.getDeliveryID() );
			preparedStatement.setInt( 3, order.getPharmacistID() );
			preparedStatement.setInt( 4, order.getPharmacyID() );
			preparedStatement.setInt( 5, order.getBranchID() );
			preparedStatement.setInt( 6, order.getCityID() );
			preparedStatement.setInt( 7, order.getPrescriptionID() );
			preparedStatement.setString( 8, order.getOrderAddress() );
			preparedStatement.setTimestamp( 9, CurrentDate );
			preparedStatement.setTimestamp( 10, order.getDateProcessed() );
			preparedStatement.setTimestamp( 11, order.getDateDelivered() );
			preparedStatement.setString( 12, order.getOrderType() );
			preparedStatement.setString( 13, order.getOrderStatus() );
			preparedStatement.setBoolean( 14, order.getSeniorDiscount() );
			preparedStatement.setString( 15, order.getPaymentMethod() );
			preparedStatement.setDouble( 16, order.getActualCost() );
			preparedStatement.setString( 17, order.getPaymayaID() );
			int NewID = preparedStatement.executeUpdate();
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				NewID = rs.getInt(1);
			}
			preparedStatement.close();
			return NewID;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void deleteOrder(int orderId) {
		try {
			String query = "DELETE FROM `order` where OrderID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, orderId);
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateOrder(Order order) {
		try {
			String query = "UPDATE `order` SET CustomerID=?, DeliveryID=?, PharmacistID=?, PharmacyID=?, CityID=?, BranchID=?, PrescriptionID=?, OrderAddress=?, DateOrdered=?, DateProcessed=?, DateDelivered=?, OrderType=?, OrderStatus=?, SeniorDiscount=?, PaymentMethod=?, ActualCost=?, PayMaya=? WHERE OrderID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt( 1, order.getCustomerID() );
			preparedStatement.setInt( 2, order.getDeliveryID() );
			preparedStatement.setInt( 3, order.getPharmacistID() );
			preparedStatement.setInt( 4, order.getPharmacyID() );
			preparedStatement.setInt( 5, order.getCityID() );
			preparedStatement.setInt( 6, order.getBranchID() );
			preparedStatement.setInt( 7, order.getPrescriptionID() );
			preparedStatement.setString( 8, order.getOrderAddress() );
			preparedStatement.setTimestamp( 9, order.getDateOrdered() );
			preparedStatement.setTimestamp( 10, order.getDateProcessed() );
			preparedStatement.setTimestamp( 11, order.getDateDelivered() );
			preparedStatement.setString( 12, order.getOrderType() );
			preparedStatement.setString( 13, order.getOrderStatus() );
			preparedStatement.setBoolean( 14, order.getSeniorDiscount() );
			preparedStatement.setString( 15, order.getPaymentMethod() );
			preparedStatement.setDouble(16, order.getActualCost() );
			preparedStatement.setInt( 17, order.getOrderID() );
			preparedStatement.setString( 18, order.getPaymayaID() );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> getOrder() {
		List<Order> orders = new ArrayList<Order>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery( "SELECT * FROM `order`" );
			while( resultSet.next() ) {
				Order order = new Order();
				order.setOrderID( resultSet.getInt( "OrderID" ) );
				order.setCustomerID( resultSet.getInt( "CustomerID" ) );
				order.setDeliveryID( resultSet.getInt( "DeliveryID" ) );
				order.setPharmacistID( resultSet.getInt( "PharmacistID" ) );
				order.setPharmacyID( resultSet.getInt("PharmacyID"));
				order.setCityID( resultSet.getInt( "CityID" ) );
				order.setBranchID( resultSet.getInt( "BranchID" ));
				order.setPrescriptionID( resultSet.getInt( "PrescriptionID" ) );
				order.setOrderAddress( resultSet.getString( "OrderAddress" ) );
				order.setDateOrdered( resultSet.getTimestamp( "DateOrdered" ) );
				order.setDateProcessed( resultSet.getTimestamp( "DateProcessed" ) );
				order.setDateDelivered( resultSet.getTimestamp( "DateDelivered" ) );
				order.setOrderType( resultSet.getString( "OrderType" ) );
				order.setOrderStatus( resultSet.getString( "OrderStatus" ) );
				order.setSeniorDiscount( resultSet.getBoolean( "SeniorDiscount" ) );
				order.setPaymentMethod( resultSet.getString( "PaymentMethod" ) );
				order.setActualCost( resultSet.getDouble( "ActualCost" ) );
				orders.add(order);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	@Override
	public Order getOrderById(int orderId) {
		Order order = new Order();
		try {
			String query = "SELECT * FROM `order` WHERE OrderID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, orderId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				order.setOrderID( resultSet.getInt( "OrderID" ) );
				order.setCustomerID( resultSet.getInt( "CustomerID" ) );
				order.setDeliveryID( resultSet.getInt( "DeliveryID" ) );
				order.setPharmacistID( resultSet.getInt( "PharmacistID" ) );
				order.setPharmacyID( resultSet.getInt("PharmacyID"));
				order.setCityID( resultSet.getInt( "CityID" ) );
				order.setBranchID( resultSet.getInt( "BranchID" ));
				order.setPrescriptionID( resultSet.getInt( "PrescriptionID" ) );
				order.setOrderAddress( resultSet.getString( "OrderAddress" ) );
				order.setDateOrdered( resultSet.getTimestamp( "DateOrdered" ) );
				order.setDateProcessed( resultSet.getTimestamp( "DateProcessed" ) );
				order.setDateDelivered( resultSet.getTimestamp( "DateDelivered" ) );
				order.setOrderType( resultSet.getString( "OrderType" ) );
				order.setOrderStatus( resultSet.getString( "OrderStatus" ) );
				order.setSeniorDiscount( resultSet.getBoolean( "SeniorDiscount" ) );
				order.setPaymentMethod( resultSet.getString( "PaymentMethod" ) );
				order.setActualCost( resultSet.getDouble( "ActualCost" ) );
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	public void updateOrderStatus(int OrderID, String Status, int BranchID) {
		try {
			Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
			String query = "UPDATE `order` SET OrderStatus=?, DateProcessed=?, BranchID=? WHERE OrderID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setString( 1, Status );
			preparedStatement.setDate( 2, CurrentDate );
			preparedStatement.setInt( 3, BranchID );
			preparedStatement.setInt( 4	, OrderID );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void updateOrderPayment(int OrderID, String Payment) {
		try {
			String query = "UPDATE `order` SET PaymentMethod=?, OrderStatus='PAID' WHERE OrderID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setString( 1, Payment );
			preparedStatement.setInt( 2, OrderID );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateDeliveryID(int OrderID, int DeliveryID) {
		try {
			String query = "UPDATE `order` SET DeliveryID=? WHERE OrderID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt( 1, DeliveryID );
			preparedStatement.setInt( 2, OrderID );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public List<Order> getPendingOrder(int CID) {
		List<Order> orders = new ArrayList<Order>();
		try {
			String query = "SELECT * FROM `order` WHERE OrderStatus='APPROVED' AND CustomerID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, CID);			
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				Order order = new Order();
				order.setOrderID( resultSet.getInt( "OrderID" ) );
				order.setCustomerID( resultSet.getInt( "CustomerID" ) );
				order.setDeliveryID( resultSet.getInt( "DeliveryID" ) );
				order.setPharmacistID( resultSet.getInt( "PharmacistID" ) );
				order.setPharmacyID( resultSet.getInt("PharmacyID"));
				order.setCityID( resultSet.getInt( "CityID" ) );
				order.setBranchID( resultSet.getInt( "BranchID" ));
				order.setPrescriptionID( resultSet.getInt( "PrescriptionID" ) );
				order.setOrderAddress( resultSet.getString( "OrderAddress" ) );
				order.setDateOrdered( resultSet.getTimestamp( "DateOrdered" ) );
				order.setDateProcessed( resultSet.getTimestamp( "DateProcessed" ) );
				order.setDateDelivered( resultSet.getTimestamp( "DateDelivered" ) );
				order.setOrderType( resultSet.getString( "OrderType" ) );
				order.setOrderStatus( resultSet.getString( "OrderStatus" ) );
				order.setSeniorDiscount( resultSet.getBoolean( "SeniorDiscount" ) );
				order.setPaymentMethod( resultSet.getString( "PaymentMethod" ) );
				order.setActualCost( resultSet.getDouble( "ActualCost" ) );
				orders.add(order);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public List<Order> getOrderByPharmacyId(int pharmacyId) {
		List<Order> orders = new ArrayList<Order>();
		try {
			String query = "SELECT * FROM `order` WHERE PharmacyID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, pharmacyId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				Order order = new Order();
				order.setOrderID( resultSet.getInt( "OrderID" ) );
				order.setCustomerID( resultSet.getInt( "CustomerID" ) );
				order.setDeliveryID( resultSet.getInt( "DeliveryID" ) );
				order.setPharmacistID( resultSet.getInt( "PharmacistID" ) );
				order.setPharmacyID( resultSet.getInt("PharmacyID"));
				order.setCityID( resultSet.getInt( "CityID" ) );
				order.setBranchID( resultSet.getInt( "BranchID" ));
				order.setPrescriptionID( resultSet.getInt( "PrescriptionID" ) );
				order.setOrderAddress( resultSet.getString( "OrderAddress" ) );
				order.setDateOrdered( resultSet.getTimestamp( "DateOrdered" ) );
				order.setDateProcessed( resultSet.getTimestamp( "DateProcessed" ) );
				order.setDateDelivered( resultSet.getTimestamp( "DateDelivered" ) );
				order.setOrderType( resultSet.getString( "OrderType" ) );
				order.setOrderStatus( resultSet.getString( "OrderStatus" ) );
				order.setSeniorDiscount( resultSet.getBoolean( "SeniorDiscount" ) );
				order.setPaymentMethod( resultSet.getString( "PaymentMethod" ) );
				order.setActualCost( resultSet.getDouble( "ActualCost" ) );
				orders.add(order);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public List<Order> getOrderByCustomerId(int customerId) {
		List<Order> orders = new ArrayList<Order>();
		try {
			String query = "SELECT * FROM `order` WHERE CustomerID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, customerId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				Order order = new Order();
				order.setOrderID( resultSet.getInt( "OrderID" ) );
				order.setCustomerID( resultSet.getInt( "CustomerID" ) );
				order.setDeliveryID( resultSet.getInt( "DeliveryID" ) );
				order.setPharmacistID( resultSet.getInt( "PharmacistID" ) );
				order.setPharmacyID( resultSet.getInt("PharmacyID"));
				order.setCityID( resultSet.getInt( "CityID" ) );
				order.setBranchID( resultSet.getInt( "BranchID" ));
				order.setPrescriptionID( resultSet.getInt( "PrescriptionID" ) );
				order.setOrderAddress( resultSet.getString( "OrderAddress" ) );
				order.setDateOrdered( resultSet.getTimestamp( "DateOrdered" ) );
				order.setDateProcessed( resultSet.getTimestamp( "DateProcessed" ) );
				order.setDateDelivered( resultSet.getTimestamp( "DateDelivered" ) );
				order.setOrderType( resultSet.getString( "OrderType" ) );
				order.setOrderStatus( resultSet.getString( "OrderStatus" ) );
				order.setSeniorDiscount( resultSet.getBoolean( "SeniorDiscount" ) );
				order.setPaymentMethod( resultSet.getString( "PaymentMethod" ) );
				order.setActualCost( resultSet.getDouble( "ActualCost" ) );
				orders.add(order);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}
	/*
	public int addIncompleteOrder(Order order) {
		try {
			Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
			String query = "INSERT INTO `order` (CustomerID, CityID, PharmacyID, OrderAddress, DateOrdered, OrderType, OrderStatus, SeniorDiscount, ActualCost) VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement preparedStatement = conn.prepareStatement( query, Statement.RETURN_GENERATED_KEYS );
			preparedStatement.setInt( 1, order.getCustomerID() );
			preparedStatement.setInt( 2, order.getCityID() );
			preparedStatement.setInt( 3, order.getPharmacyID() );
			preparedStatement.setString( 4, order.getOrderAddress() );
			preparedStatement.setTimestamp( 5, CurrentDate );
			preparedStatement.setString( 6, order.getOrderType() );
			preparedStatement.setString( 7, order.getOrderStatus() );
			preparedStatement.setBoolean( 8, order.getSeniorDiscount() );
			preparedStatement.setDouble( 9, order.getActualCost() );
			
			preparedStatement.executeUpdate();
			int NewID = 0;
			ResultSet rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				NewID = rs.getInt(1);
			}
			preparedStatement.close();
			return NewID;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	*/
}
