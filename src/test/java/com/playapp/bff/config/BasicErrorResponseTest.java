package com.playapp.bff.config;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

class BasicErrorResponseTest {

	@Test
	void allArgsConstructorTest() {
		LocalDateTime now = LocalDateTime.now();
		BasicErrorResponse response = new BasicErrorResponse(now, "");

		assertEquals(now, response.getTimestamp());
		assertEquals("", response.getCustomizedMessage());
	}

	@Test
	void noArgsConstructorTest() {
		BasicErrorResponse response = new BasicErrorResponse();

		assertNull(response.getTimestamp());
		assertNull(response.getCustomizedMessage());

		LocalDateTime now = LocalDateTime.now();
		response.setTimestamp(now);
		response.setCustomizedMessage("");

		assertEquals(now, response.getTimestamp());
		assertEquals("", response.getCustomizedMessage());
	}

	@Test
	void builderTest() {
		LocalDateTime now = LocalDateTime.now();
		BasicErrorResponse response = BasicErrorResponse.builder().timestamp(now).customizedMessage("Test Message")
				.build();

		assertEquals(now, response.getTimestamp());
		assertEquals("Test Message", response.getCustomizedMessage());
	}

	@Test
	void gettersAndSettersTest() {
		BasicErrorResponse response = new BasicErrorResponse();
		LocalDateTime now = LocalDateTime.now();

		response.setTimestamp(now);
		response.setCustomizedMessage("");

		assertEquals(now, response.getTimestamp());
		assertEquals("", response.getCustomizedMessage());
	}
}
