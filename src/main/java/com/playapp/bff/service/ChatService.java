/*
 * 
 */
package com.playapp.bff.service;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ChatService.
 */
@Slf4j
@Service
public class ChatService {

	/** The chat client. */
	private OpenAiChatClient chatClient;

	/**
	 * Instantiates a new chat service.
	 *
	 * @param chatClient the chat client
	 */
	public ChatService(OpenAiChatClient chatClient) {
		this.chatClient = chatClient;
	}

	/**
	 * Gets the chat response by prompts.
	 *
	 * @param message the message
	 * @return the chat response by prompts
	 */
	public ChatResponse getChatResponseByPrompts(String message) {
		log.info("start - getChatResponseByPrompts - message: " + message);
		Prompt prompt = new Prompt(new UserMessage(message));
		// Llamar al cliente de chat para obtener y devolver la respuesta
		ChatResponse response = chatClient.call(prompt);
		log.info("end - getChatResponseByPrompts - response: " + response.getResult().getOutput());
		return response;

	}

	/**
	 * Gets the basic chat response.
	 *
	 * @param message the message
	 * @return the basic chat response
	 */
	public String getBasicChatResponse(String message) {
		log.info("start - getBasicChatResponse - message: " + message);
		// Llamar al cliente de chat para obtener y devolver la respuesta
		String response = chatClient.call(message);
		log.info("end - getBasicChatResponse - response: " + response);
		return response;

	}

	/**
	 * Gets the chat response by prompts options.
	 *
	 * @param message     the message
	 * @param chatOptions the chat options
	 * @return the chat response by prompts options
	 */
	public String getChatResponseByPromptsOptions(String message, OpenAiChatOptions chatOptions) {
		log.info("start - getChatResponseByPromptsOptions");
		Prompt prompt = new Prompt(new UserMessage(message), chatOptions);
		ChatResponse chatResponse = chatClient.call(prompt);
		log.info("end - getChatResponseByPromptsOptions");
		return chatResponse.getResult().getOutput().getContent();
	}

	/**
	 * Gets the beaches recommended.
	 *
	 * @param message the message
	 * @return the beaches recommended
	 */
	public String getBeachesRecommended(String message) {
		log.info("start - getBeachesRecommended - message: " + message);
		// Definir las opciones del chat para llamar a la función "weatherFunction"
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withFunction("weatherFunction").build();
		String messageResult = getChatResponseByPromptsOptions(message, chatOptions);
		log.info("end - getBeachesRecommended - messageResult: " + messageResult);
		return messageResult;
	}
}
