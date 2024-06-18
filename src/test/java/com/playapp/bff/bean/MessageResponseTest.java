package com.playapp.bff.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class MessageResponseTest {

	@Test
	void NoArgsConstructorTest() {
		MessageResponse response = new MessageResponse();
		assertNotNull(response);
		assertEquals(null, response.getMessage());
	}

	@Test
	void setterTest() {
		MessageResponse response = new MessageResponse();
		String testMessage = "msg";
		response.setMessage(testMessage);
		assertEquals(testMessage, response.getMessage());
	}

}
