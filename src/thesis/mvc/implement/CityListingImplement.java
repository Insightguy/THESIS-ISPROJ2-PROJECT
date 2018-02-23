package thesis.mvc.implement;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import thesis.mvc.dataobjects.CityListingDAO;
import thesis.mvc.model.CityListing;
import thesis.mvc.utility.DBUtility;

public class CityListingImplement implements CityListingDAO{
	
	private Connection conn;

	public CityListingImplement() {
		conn = DBUtility.getConnection();
	}

	@Override
	public void addCityListing(CityListing cityListing) {
		try {
		String query = "INSERT INTO CityListing (CityName, CityCost) VALUES (?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement( query );
		preparedStatement.setString( 1, cityListing.getCityName() );
		preparedStatement.setBigDecimal( 2, cityListing.getCityCost() );
		preparedStatement.executeUpdate();
		preparedStatement.close();
	} catch (SQLException e) {
		e.printStackTrace();
	} try {
		Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
		String query = "INSERT INTO Audit (UserID, LogType, Timestamp, ActionTaken) VALUES (?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement( query );
		preparedStatement.setInt( 1, 3 ); //3 is a placeholder
		preparedStatement.setString( 2, "create, update or delete" ); //placeholder
		preparedStatement.setDate( 3, CurrentDate );
		preparedStatement.setString( 4, "User with ID " + "" );
		preparedStatement.executeUpdate();
		preparedStatement.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public void deleteCityListing(int cityListingId) {
		try {
			String query = "DELETE FROM CityListing where CityID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt( 1, cityListingId );
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateCityListing(CityListing cityListing) {
		try {
		String query = "UPDATE CityListing SET CityName=?, CityCost=? WHERE CityID=?";
		PreparedStatement preparedStatement = conn.prepareStatement( query );
		preparedStatement.setString( 1, cityListing.getCityName() );
		preparedStatement.setBigDecimal( 2, cityListing.getCityCost() );
		preparedStatement.setInt( 3, cityListing.getCityID() );
		preparedStatement.executeUpdate();
		preparedStatement.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
		
	}

	@Override
	public List<CityListing> getAllCityListing() {
		List<CityListing> cityListings = new ArrayList<CityListing>();
		try {
			Statement statement = conn.createStatement();
			ResultSet resultSet = statement.executeQuery( "SELECT * FROM CityListing" );
			while( resultSet.next() ) {
				CityListing cityListing = new CityListing();
				cityListing.setCityID( resultSet.getInt( "CityID" ) );
				cityListing.setCityName( resultSet.getString( "CityName" ) );
				cityListing.setCityCost( resultSet.getBigDecimal( "CityCost" ) );
				cityListings.add(cityListing);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cityListings;
	}

	@Override
	public CityListing getCityListingById(int cityListingId) {
		CityListing cityListing = new CityListing();
		try {
			String query = "SELECT * FROM CityListing WHERE CityID=?";
			PreparedStatement preparedStatement = conn.prepareStatement( query );
			preparedStatement.setInt(1, cityListingId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while( resultSet.next() ) {
				cityListing.setCityID( resultSet.getInt( "CityID" ) );
				cityListing.setCityName( resultSet.getString( "CityName" ) );
				cityListing.setCityCost( resultSet.getBigDecimal( "CityCost" ) );
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cityListing;
	}
	

}
