package org.tacografo.file.exception;

import java.util.Date;

@SuppressWarnings("serial")
public class ExceptionDriverNotExist extends Exception {

	private String driverName;
	private String cardNumber;
	private Date  cardExpiryDate;
	private Date driverBirthDate;
	
	public ExceptionDriverNotExist(String driverName, String cardNumber, Date cardExpiryDate, Date driverBirthDate) {
		this.driverName = driverName;
		this.cardNumber = cardNumber;
		this.cardExpiryDate = cardExpiryDate;
		this.driverBirthDate = driverBirthDate;
	}

	public String getDriverName() {
		return driverName;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}

	public Date getCardExpiryDate() {
		return cardExpiryDate;
	}

	public Date getDriverBirthDate() {
		return driverBirthDate;
	}

	public void setDriverBirthDate(Date driverBirthDate) {
		this.driverBirthDate = driverBirthDate;
	}
	
}
