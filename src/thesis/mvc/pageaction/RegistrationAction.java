package thesis.mvc.pageaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import thesis.mvc.implement.*;
import thesis.mvc.model.*;
import thesis.mvc.utility.DBUtility;

public class RegistrationAction {
	
	private Connection conn;

	public RegistrationAction() {
		conn = DBUtility.getConnection();
	}
	
    public boolean makeCustomer(Login login, Customer customer) {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
		int UserID = 0;
		
    	//Check if user-name is used
    	try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM login WHERE Username = ?")) {
            stmt.setString(1, login.getUsername());
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Login table
    	LoginImplement LoginImp = new LoginImplement();
    	login.setLoginStatus("Just Registered");
    	login.setUsertype("Customer");
    	LoginImp.addLogin(login);
    	
    	//Get the UserID
    	try(PreparedStatement stmt = conn.prepareStatement(""
    			+ "SELECT UserID FROM login WHERE Username = ? AND Password = ?")) {

            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getPassword());

            try(ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                	UserID = rs.getInt(1);
                }  else {
                    return false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Customer table
    	CustomerImplement CustomerImp = new CustomerImplement();
    	customer.setUserID(UserID);
        if (customer.getSeniorCitizenID() == null) {
        	customer.setIsSeniorCitizen(false);
        } else {
        	customer.setIsSeniorCitizen(true);
        }
        CustomerImp.addCustomer(customer);
        
        //Audit Log
        Audit audit = new Audit();
        audit.setUserID(UserID);
        audit.setLogType("CustReg");
        audit.setTimestamp(CurrentDate);
        audit.setActionTaken("User ID " + UserID + " With the username " + login.getUsername() + " made an account on " + df.format(CurrentDate));
        AuditImplement AuditImp = new AuditImplement();
        AuditImp.addAudit(audit);
        return true;
    }
    
	public boolean makeAdmin(Login login, Admin admin) {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
		int UserID = 0;
		
    	//Check if user-name is used
    	try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM login WHERE Username = ?")) {
            stmt.setString(1, login.getUsername());
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Login table
    	LoginImplement LoginImp = new LoginImplement();
    	login.setLoginStatus("Just Registered");
    	login.setUsertype("Customer");
    	LoginImp.addLogin(login);
    	
    	//Get the UserID
    	try(PreparedStatement stmt = conn.prepareStatement(""
    			+ "SELECT UserID FROM login WHERE Username = ? AND Password = ?")) {

            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getPassword());

            try(ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                	UserID = rs.getInt(1);
                }  else {
                    return false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Admin table
    	AdminImplement AdminImp = new AdminImplement();
    	admin.setUserID(UserID);
    	AdminImp.addAdmin(admin);
        
        //Audit Log
        Audit audit = new Audit();
        audit.setUserID(UserID);
        audit.setLogType("CustReg");
        audit.setTimestamp(CurrentDate);
        audit.setActionTaken("User ID " + UserID + " With the username " + login.getUsername() + " made an account on " + df.format(CurrentDate));
        AuditImplement AuditImp = new AuditImplement();
        AuditImp.addAudit(audit);
        return true;
    }
    
    public boolean makeDispatcher(Login login, Dispatcher dispatcher) {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
		int UserID = 0;
		
    	//Check if user-name is used
    	try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM login WHERE Username = ?")) {
            stmt.setString(1, login.getUsername());
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Login table
    	LoginImplement LoginImp = new LoginImplement();
    	login.setLoginStatus("Just Registered");
    	login.setUsertype("Customer");
    	LoginImp.addLogin(login);
    	
    	//Get the UserID
    	try(PreparedStatement stmt = conn.prepareStatement(""
    			+ "SELECT UserID FROM login WHERE Username = ? AND Password = ?")) {

            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getPassword());

            try(ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                	UserID = rs.getInt(1);
                }  else {
                    return false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Dispatcher table
    	DispatcherImplement DispatcherImp = new DispatcherImplement();
    	dispatcher.setUserID(UserID);
    	DispatcherImp.addDispatcher(dispatcher);
        
        //Audit Log
        Audit audit = new Audit();
        audit.setUserID(UserID);
        audit.setLogType("CustReg");
        audit.setTimestamp(CurrentDate);
        audit.setActionTaken("User ID " + UserID + " With the username " + login.getUsername() + " made an account on " + df.format(CurrentDate));
        AuditImplement AuditImp = new AuditImplement();
        AuditImp.addAudit(audit);
        return true;
    }
    
    public boolean makePharmacist(Login login, Pharmacist pharmacist) {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date CurrentDate = new Date(Calendar.getInstance().getTime().getTime());
		int UserID = 0;
		
    	//Check if user-name is used
    	try(PreparedStatement stmt = conn.prepareStatement("SELECT * FROM login WHERE Username = ?")) {
            stmt.setString(1, login.getUsername());
            try(ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Login table
    	LoginImplement LoginImp = new LoginImplement();
    	login.setLoginStatus("Just Registered");
    	login.setUsertype("Customer");
    	LoginImp.addLogin(login);
    	
    	//Get the UserID
    	try(PreparedStatement stmt = conn.prepareStatement(""
    			+ "SELECT UserID FROM login WHERE Username = ? AND Password = ?")) {

            stmt.setString(1, login.getUsername());
            stmt.setString(2, login.getPassword());

            try(ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                	UserID = rs.getInt(1);
                }  else {
                    return false;
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    	
    	//Insert into Pharmacist table
    	PharmacistImplement PharmacistImp = new PharmacistImplement();
    	pharmacist.setUserID(UserID);
    	PharmacistImp.addPharmacist(pharmacist);
        
        //Audit Log
        Audit audit = new Audit();
        audit.setUserID(UserID);
        audit.setLogType("CustReg");
        audit.setTimestamp(CurrentDate);
        audit.setActionTaken("User ID " + UserID + " With the username " + login.getUsername() + " made an account on " + df.format(CurrentDate));
        AuditImplement AuditImp = new AuditImplement();
        AuditImp.addAudit(audit);
        return true;
    }
    
}
