package com.playapp.bff.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class ErrorResponseTest {

	@Test
	public void builderTest() {
		LocalDateTime now = LocalDateTime.of(2024, 06, 13, 4, 30);
		ErrorResponse response = ErrorResponse.builder().customizedMessage("").error("").status(0).timestamp(now)
				.build();

		assertEquals("", response.getCustomizedMessage());
		assertEquals(LocalDateTime.of(2024, 06, 13, 4, 30), response.getTimestamp());
		assertEquals(0, response.getStatus());
		assertEquals("", response.getError());
	}

	public void gettersAndSettersTest() {
		ErrorResponse response = new ErrorResponse();
		response.setCustomizedMessage("");
		response.setStatus(404);
		response.setError("Not Found");

		assertEquals("", response.getCustomizedMessage());
		assertEquals(404, response.getStatus());
		assertEquals("Not Found", response.getError());
	}
}
