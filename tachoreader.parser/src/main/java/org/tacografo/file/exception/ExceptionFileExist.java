package org.tacografo.file.exception;

@SuppressWarnings("serial")
public class ExceptionFileExist extends Exception {

	private String fileName;
	
	public ExceptionFileExist(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}	
}
