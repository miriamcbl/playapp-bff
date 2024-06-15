package com.playapp.bff.config;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

/**
 * The Class GlobalExceptionHandler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handle response status exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex) {
		int status = ex.getStatusCode().value();
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), status, HttpStatus.valueOf(status).name(),
				ex.getReason());
		return new ResponseEntity<>(errorResponse, ex.getStatusCode());
	}

	/**
	 * Handle generic exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Ocurrió un error inesperado");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<BasicErrorResponse> handleGenericException(IllegalArgumentException ex) {
		BasicErrorResponse errorResponse = new BasicErrorResponse(LocalDateTime.now(), ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}