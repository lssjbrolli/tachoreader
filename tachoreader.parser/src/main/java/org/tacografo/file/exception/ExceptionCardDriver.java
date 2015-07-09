package org.tacografo.file.exception;

@SuppressWarnings("serial")
public class ExceptionCardDriver extends Exception {

	private String tachoDriverIdentification;
	private String cardNumber;
	private String tachoHolderName;
	
	public ExceptionCardDriver(String tachoDriverIdentification, String cardNumber, String tachoHolderName) {
		this.tachoDriverIdentification = tachoDriverIdentification;
		this.cardNumber = cardNumber;
		this.tachoHolderName = tachoHolderName;
	}

	public String getTachoDriverIdentification() {
		return tachoDriverIdentification;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public String getTachoHolderName() {
		return tachoHolderName;
	}	
}
