package com.playapp.bff.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.playapp.bff.bean.MessageResponse;
import com.playapp.bff.service.ChatService;

import jakarta.servlet.http.HttpSession;

class AIChatControllerTest {

	@Mock
	private ChatService chatService;

	@InjectMocks
	private AIChatController aiChatController;
	private String message;
	private String expectedResult;

	@BeforeEach
	void initMocks() {
		MockitoAnnotations.openMocks(this);
		message = "This is a test message";
		expectedResult = "This is a test response";
	}

	@Test
	void getBasicResponseTest() {
		// mockeamos el metodo call, para que devuelva el mensaje de la linea anterior
		when(chatService.getBasicChatResponse(anyString())).thenReturn(expectedResult);
		assertNotNull(aiChatController.getBasicResponse(message));
	}

	@Test
	void getBeachesRecommendedTest() {
		when(chatService.getBeachesRecommended(anyString(), any()))
				.thenReturn(MessageResponse.builder().message(expectedResult).build());
		HttpSession httpSession = null;
		assertNotNull(aiChatController.getBeachesRecommended(message, httpSession));
	}

}
