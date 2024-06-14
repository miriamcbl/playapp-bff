package com.playapp.bff.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

public class ErrorHandler {

	public static ResponseStatusException webClientHandleErrorResponse(WebClientResponseException ex,
			String customMessage) {
		int status = ex.getStatusCode().value();
		switch (status) {
		case 401:
			return new ResponseStatusException(HttpStatus.UNAUTHORIZED, customMessage);
		case 405:
			return new ResponseStatusException(HttpStatus.BAD_REQUEST, customMessage);
		case 500:
			return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, customMessage);
		default:
			return new ResponseStatusException(status, customMessage, ex);
		}
	}
}
