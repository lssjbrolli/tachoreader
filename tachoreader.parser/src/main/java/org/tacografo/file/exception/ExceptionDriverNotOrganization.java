package org.tacografo.file.exception;

@SuppressWarnings("serial")
public class ExceptionDriverNotOrganization extends Exception {

	private String driverName;
	private String company;
	
	public ExceptionDriverNotOrganization(String company, String driverName) {
		this.company = company;
		this.driverName = driverName;
	}

	public String getCompany() {
		return company;
	}
	
	public String getDriverName() {
		return driverName;
	}
	
}
