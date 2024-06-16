package com.playapp.bff.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;

class ErrorHandlerTest {

	@ParameterizedTest
	@EnumSource(value = HttpStatus.class, names = { "UNAUTHORIZED", "FORBIDDEN", "NOT_FOUND", "METHOD_NOT_ALLOWED",
			"REQUEST_TIMEOUT", "INTERNAL_SERVER_ERROR", "BAD_GATEWAY", "SERVICE_UNAVAILABLE", "GATEWAY_TIMEOUT" })
	public void webClientHandleErrorResponseTest(HttpStatus httpStatus) {
		WebClientResponseException ex = mock(WebClientResponseException.class);
		when(ex.getStatusCode()).thenReturn(httpStatus);
		String customMessage = "";
		ResponseStatusException responseStatusException = ErrorHandler.webClientHandleErrorResponse(ex, customMessage);
		assertEquals(customMessage, responseStatusException.getReason());
	}

	@ParameterizedTest
	@EnumSource(value = HttpStatus.class, names = { "UNAUTHORIZED", "FORBIDDEN", "NOT_FOUND", "METHOD_NOT_ALLOWED",
			"REQUEST_TIMEOUT", "INTERNAL_SERVER_ERROR", "BAD_GATEWAY", "SERVICE_UNAVAILABLE", "GATEWAY_TIMEOUT" })
	public void chatHandleErrorResponseTest(HttpStatus httpStatus) {
		NonTransientAiException ex = mock(NonTransientAiException.class);
		when(ex.getMessage()).thenReturn(httpStatus.value() + "message");
		String customMessage = "";
		ResponseStatusException responseStatusException = ErrorHandler.chatHandleErrorResponse(ex, customMessage);
		assertTrue(responseStatusException.getReason().contains(customMessage));
		assertTrue(responseStatusException.getReason().contains("message"));
	}

}
