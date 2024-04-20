package com.playapp.bff.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.test.util.ReflectionTestUtils;

import com.playapp.bff.service.WeatherService;


class AIChatControllerTest {

	@Mock
	private OpenAiChatClient chatClient;

	@Mock
	private WeatherService weatherService;

	@InjectMocks
	private AIChatController aiChatController;

	@BeforeEach
	void initMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void generateTest() throws Exception {
		ReflectionTestUtils.setField(aiChatController, "chatClient", chatClient);
		String message = "This is a test message";
		String expectedResult = "This is a test response";
		// mockeamos el metodo call, para que devuelva el mensaje de la linea anterior
		when(chatClient.call(anyString())).thenReturn(expectedResult);
		assertNotNull(aiChatController.generate(message));
	}

	@Test
	void generateMyStreamTest() throws Exception {
		Generation generation = new Generation(null);
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);
		assertNotNull(aiChatController.generateMyStream(""));
	}

}
