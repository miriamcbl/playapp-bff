/*
 * 
 */
package com.playapp.bff.service;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.stereotype.Service;

import com.playapp.bff.config.ErrorHandler;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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
	@CircuitBreaker(name = "playapp", fallbackMethod = "getChatResponseByPromptsFallback")
	public ChatResponse getChatResponseByPrompts(String message) {
		log.info("start - getChatResponseByPrompts - message: " + message);
		Prompt prompt = new Prompt(new UserMessage(message));
		// Llamar al cliente de chat para obtener y devolver la respuesta
		ChatResponse response = chatClient.call(prompt);
		log.info("end - getChatResponseByPrompts - response: " + response.getResult().getOutput());
		return response;
	}

	/**
	 * Gets the chat response by prompts fallback.
	 *
	 * @param message   the message
	 * @param exception the exception
	 * @return the chat response by prompts fallback
	 */
	protected ChatResponse getChatResponseByPromptsFallback(String message, NonTransientAiException exception) {
		throw ErrorHandler.chatHandleErrorResponse(exception, "Error al llamar a chat usando Prompts");
	}

	/**
	 * Gets the basic chat response.
	 *
	 * @param message the message
	 * @return the basic chat response
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getBasicChatResponseFallback")
	public String getBasicChatResponse(String message) {
		log.info("start - getBasicChatResponse - message: " + message);
		// Llamar al cliente de chat para obtener y devolver la respuesta
		String response = chatClient.call(message);
		log.info("end - getBasicChatResponse - response: " + response);
		return response;
	}

	/**
	 * Gets the basic chat response fallback.
	 *
	 * @param message   the message
	 * @param exception the exception
	 * @return the basic chat response fallback
	 */
	protected String getBasicChatResponseFallback(String message, NonTransientAiException exception) {
		throw ErrorHandler.chatHandleErrorResponse(exception, "Error al llamar a chat básico");
	}

	/**
	 * Gets the chat response by prompts options.
	 *
	 * @param message     the message
	 * @param chatOptions the chat options
	 * @return the chat response by prompts options
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getChatResponseByPromptsOptionsFallback")
	public String getChatResponseByPromptsOptions(String message, OpenAiChatOptions chatOptions) {
		log.info("start - getChatResponseByPromptsOptions");
		Prompt prompt = new Prompt(new UserMessage(message), chatOptions);
		ChatResponse chatResponse = chatClient.call(prompt);
		log.info("end - getChatResponseByPromptsOptions");
		return chatResponse.getResult().getOutput().getContent();
	}

	/**
	 * Gets the chat response by prompts options fallback.
	 *
	 * @param message     the message
	 * @param chatOptions the chat options
	 * @param exception   the exception
	 * @return the chat response by prompts options fallback
	 */
	protected String getChatResponseByPromptsOptionsFallback(String message, OpenAiChatOptions chatOptions,
			NonTransientAiException exception) {
		throw ErrorHandler.chatHandleErrorResponse(exception, "Error al llamar a chat usando Prompts y Options");
	}

	/**
	 * Gets the beaches recommended.
	 *
	 * @param message the message
	 * @return the beaches recommended
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getBeachesRecommendedFallback")
	public String getBeachesRecommended(String message) {
		log.info("start - getBeachesRecommended - message: " + message);
		// Definir las opciones del chat para llamar a la función "weatherFunction"
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withFunction("weatherFunction").build();
		String messageResult = getChatResponseByPromptsOptions(message, chatOptions);
		log.info("end - getBeachesRecommended - messageResult: " + messageResult);
		return messageResult;
	}

	protected String getBeachesRecommendedFallback(String message, NonTransientAiException exception) {
		throw ErrorHandler.chatHandleErrorResponse(exception, "Error al llamar a chat usando Prompts y Options");
	}
}
