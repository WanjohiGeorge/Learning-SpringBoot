package com.master.demo.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.master.demo.ui.response.models.ErrorMessage;

//To be able to handle exceptions anonnate with.  This annotation provides a method that 
@ControllerAdvice
public class AppExceptionHandler {

//	UserServiceException is the exception we will be handling
//	Webrequest will provide access to the cookies, headers and body of the http request.	
//	to handle exceptions via this method, add exceptions to the annootation:
	@ExceptionHandler(value = UserServiceExceptions.class)
	public ResponseEntity<Object> handleUserServiceExceptions(UserServiceExceptions ex, WebRequest req) {
//		use custom error message

		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

//		 creates a response with a body, headers, and status
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = RuntimeException.class)
	public ResponseEntity<Object> handleUserServiceExceptions(RuntimeException ex, WebRequest req) {
//		use custom error message

		ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
//		 creates a response with a body, headers, and status
		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
