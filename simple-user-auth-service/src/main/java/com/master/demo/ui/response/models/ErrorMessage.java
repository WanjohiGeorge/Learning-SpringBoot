package com.master.demo.ui.response.models;

import java.util.Date;

public class ErrorMessage {
	private Date date;
	private String message;

	public ErrorMessage() {
		
	}

	public ErrorMessage(Date timestamp, String message) {
		this.message = message;
		this.date = timestamp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
