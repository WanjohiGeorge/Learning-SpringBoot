package com.master.demo.exceptions;

public enum ErrorMessages {

	MISSING_REQUIRED_FIELDS("Mising required field. Please read the documentation"),
	RECORD_EXISTS("Record already exists");

	private String errorMessage;

	ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
