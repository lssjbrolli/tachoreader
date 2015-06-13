package org.tacografo.file.exception;

@SuppressWarnings("serial")
public class ExceptionVehicleNotExist extends Exception {

	private String registration;
	
	public ExceptionVehicleNotExist(String registration) {
		this.registration = registration;
	}

	public String getRegistration() {
		return registration;
	}	
}
