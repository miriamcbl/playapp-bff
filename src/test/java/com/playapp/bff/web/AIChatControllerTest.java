package com.playapp.bff.web;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;

import com.playapp.bff.service.WeatherService;

@WebMvcTest(AIChatController.class)
class AIChatControllerTest {

	@MockBean
	private OpenAiChatClient chatClient;

	@MockBean
	private WeatherService weatherService;

	@InjectMocks
	private AIChatController aiChatController;

	@Test
	void generateTest() throws Exception {
		ReflectionTestUtils.setField(aiChatController, "chatClient", chatClient);
		String message = "This is a test message";
		String expectedResult = "This is a test response";
		// mockeamos el metodo call, para que devuelva el mensaje de la linea anterior
		when(chatClient.call(anyString())).thenReturn(expectedResult);
		assertNotNull(aiChatController.generate(message));
	}

}
