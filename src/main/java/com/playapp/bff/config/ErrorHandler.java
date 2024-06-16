package com.playapp.bff.config;

import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

public class ErrorHandler {

	private ErrorHandler() {
		super();
	}

	/**
	 * Gets the exception.
	 *
	 * @param status        the status
	 * @param customMessage the custom message
	 * @param ex            the ex
	 * @return the exception
	 */
	private static ResponseStatusException getException(int status, String customMessage, Exception ex) {
		switch (status) {
		case 401:
			return new ResponseStatusException(HttpStatus.UNAUTHORIZED, customMessage);
		case 403:
			return new ResponseStatusException(HttpStatus.FORBIDDEN, customMessage);
		case 404:
			return new ResponseStatusException(HttpStatus.NOT_FOUND, customMessage);
		case 405:
			return new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, customMessage);
		case 408:
			return new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT, customMessage);
		case 500:
			return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, customMessage);
		case 502:
			return new ResponseStatusException(HttpStatus.BAD_GATEWAY, customMessage);
		case 503:
			return new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, customMessage);
		case 504:
			return new ResponseStatusException(HttpStatus.GATEWAY_TIMEOUT, customMessage);
		default:
			return new ResponseStatusException(status, customMessage, ex);
		}
	}

	/**
	 * Web client handle error response.
	 *
	 * @param ex            the ex
	 * @param customMessage the custom message
	 * @return the response status exception
	 */
	public static ResponseStatusException webClientHandleErrorResponse(WebClientResponseException ex,
			String customMessage) {
		int status = ex.getStatusCode().value();
		return getException(status, customMessage, ex);
	}

	/**
	 * Chat handle error response.
	 *
	 * @param ex            the ex
	 * @param customMessage the custom message
	 * @return the response status exception
	 */
	public static ResponseStatusException chatHandleErrorResponse(NonTransientAiException ex, String customMessage) {
		int status = Integer.parseInt(ex.getMessage().substring(0, 3));
		int indexMessage = ex.getMessage().indexOf("message");
		String exceptionMessage = ex.getMessage().substring(indexMessage).replace("\n", "").replace("\"", "").trim();
		return getException(status, customMessage + ", " + exceptionMessage, ex);
	}
}
