package com.playapp.bff.service;

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
import org.springframework.ai.openai.OpenAiChatOptions;

class ChatServiceTest {

	@InjectMocks
	private ChatService chatService;

	@Mock
	private OpenAiChatClient chatClient;

	@BeforeEach
	void initMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void getChatResponseByPromptsTest() {
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);
		assertNotNull(chatService.getChatResponseByPrompts(""));

	}

	@Test
	void getBasicChatResponseTest() {
		when(chatClient.call(anyString())).thenReturn("");
		assertNotNull(chatService.getBasicChatResponse(""));
	}

	@Test
	void getChatResponseByPromptsOptionsTest() {
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);
		assertNotNull(chatService.getChatResponseByPromptsOptions("", new OpenAiChatOptions()));
	}

	@Test
	void getBeachesRecommendedTest() {
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatClient.call(any(Prompt.class))).thenReturn(chatResponse);
		assertNotNull(chatService.getBeachesRecommended(""));
	}

}
