package thesis.mvc.model;

import java.sql.Date;

public class CourierService {
	
	private int CourierServiceID;
	private String CompanyName;
	private String CompanyStreet;
	private String CompanyBarangay;
	private String CompanyCity;
	private String CompanyProvince;
	private int CompanyLandline;
	private int CompanyCellular;
	private String CompanyContact; 
	private Date DateAdded;
	public int getCourierServiceID() {
		return CourierServiceID;
	}
	public void setCourierServiceID(int courierServiceID) {
		CourierServiceID = courierServiceID;
	}
	public String getCompanyName() {
		return CompanyName;
	}
	public void setCompanyName(String companyName) {
		CompanyName = companyName;
	}
	public String getCompanyStreet() {
		return CompanyStreet;
	}
	public void setCompanyStreet(String companyStreet) {
		CompanyStreet = companyStreet;
	}
	public String getCompanyBarangay() {
		return CompanyBarangay;
	}
	public void setCompanyBarangay(String companyBarangay) {
		CompanyBarangay = companyBarangay;
	}
	public String getCompanyCity() {
		return CompanyCity;
	}
	public void setCompanyCity(String companyCity) {
		CompanyCity = companyCity;
	}
	public String getCompanyProvince() {
		return CompanyProvince;
	}
	public void setCompanyProvince(String companyProvince) {
		CompanyProvince = companyProvince;
	}
	public int getCompanyLandline() {
		return CompanyLandline;
	}
	public void setCompanyLandline(int companyLandline) {
		CompanyLandline = companyLandline;
	}
	public int getCompanyCellular() {
		return CompanyCellular;
	}
	public void setCompanyCellular(int companyCellular) {
		CompanyCellular = companyCellular;
	}
	public String getCompanyContact() {
		return CompanyContact;
	}
	public void setCompanyContact(String companyContact) {
		CompanyContact = companyContact;
	}
	public Date getDateAdded() {
		return DateAdded;
	}
	public void setDateAdded(Date dateAdded) {
		DateAdded = dateAdded;
	}
}
