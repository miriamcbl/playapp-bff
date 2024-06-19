package com.playapp.bff.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.stereotype.Service;

import com.playapp.bff.bean.MessageResponse;
import com.playapp.bff.config.ErrorHandler;
import com.playapp.bff.constants.Constants;
import com.playapp.bff.constants.ErrorConstants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class ChatService.
 */
@Slf4j
@Service
public class ChatService {

	/** The chat client. */
	private OpenAiChatClient chatClient;

	/** The message history. */
	private List<Message> messageHistory = new ArrayList<>();

	/**
	 * Instantiates a new chat service.
	 *
	 * @param chatClient the chat client
	 */
	public ChatService(OpenAiChatClient chatClient) {
		this.chatClient = chatClient;
	}

	@PostConstruct
	private void configAISystem() {
		addMessageToHistory(MessageType.SYSTEM.getValue(), Constants.LIMIT_THEMES_SYSTEM_PROMPT);
	}

	/**
	 * Adds the message to history.
	 *
	 * @param role    the role
	 * @param content the content
	 */
	private void addMessageToHistory(String role, String content) {
		// según el rol que sea
		MessageType messageType = MessageType.fromValue(role);
		switch (messageType) {
		case ASSISTANT:
			messageHistory.add(new AssistantMessage(content));
			break;
		case SYSTEM:
			messageHistory.add(new SystemMessage(content));
			break;
		case USER:
			messageHistory.add(new UserMessage(content));
			break;
		default:
		}
	}

	/**
	 * Gets the message history.
	 *
	 * @return the message history
	 */
	private List<Message> getMessageHistory() {
		// devolvemos una copia en lugar del chat real, por seguridad
		return new ArrayList<>(messageHistory);
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
		throw ErrorHandler.chatHandleErrorResponse(exception, ErrorConstants.PROMPTS_ERROR);
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
		throw ErrorHandler.chatHandleErrorResponse(exception, ErrorConstants.BASIC_CHAT_ERROR);
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
		throw ErrorHandler.chatHandleErrorResponse(exception, ErrorConstants.PROMPTS_OPTIONS_ERROR);
	}

	/**
	 * Gets the chat response by prompts options and system.
	 *
	 * @param message     the message
	 * @param chatOptions the chat options
	 * @return the chat response by prompts options and system
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getChatResponseByPromptsOptionsAndSystemFallback")
	public String getChatResponseByPromptsOptionsAndSystem(OpenAiChatOptions chatOptions) {
		log.info("start - getChatResponseByPromptsOptions");
		Prompt prompt = new Prompt(messageHistory, chatOptions);
		ChatResponse chatResponse = chatClient.call(prompt);
		log.info("end - getChatResponseByPromptsOptions");
		return chatResponse.getResult().getOutput().getContent();
	}

	/**
	 * Gets the chat response by prompts options and system fallback.
	 *
	 * @param message     the message
	 * @param chatOptions the chat options
	 * @param exception   the exception
	 * @return the chat response by prompts options and system fallback
	 */
	protected String getChatResponseByPromptsOptionsAndSystemFallback(String message, OpenAiChatOptions chatOptions,
			NonTransientAiException exception) {
		throw ErrorHandler.chatHandleErrorResponse(exception, ErrorConstants.PROMPTS_OPTIONS_SYSTEM_ERROR);
	}

	/**
	 * Gets the beaches recommended.
	 *
	 * @param message the message
	 * @return the beaches recommended
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getBeachesRecommendedFallback")
	public MessageResponse getBeachesRecommended(String message) {
		log.info("start - getBeachesRecommended - message: " + message);
		addMessageToHistory(MessageType.USER.getValue(), message);
		// Definir las opciones del chat para llamar a la función "weatherFunction"
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withFunction("weatherFunction").build();
		String messageResult = getChatResponseByPromptsOptionsAndSystem(chatOptions);
		addMessageToHistory(MessageType.ASSISTANT.getValue(), messageResult);
		log.info("CHAT MESSAGES: " + Arrays.toString(getMessageHistory().toArray()));
		log.info("end - getBeachesRecommended - messageResult: " + messageResult);
		return MessageResponse.builder().message(messageResult).build();
	}

	/**
	 * Gets the beaches recommended fallback.
	 *
	 * @param message   the message
	 * @param exception the exception
	 * @return the beaches recommended fallback
	 */
	protected MessageResponse getBeachesRecommendedFallback(String message, NonTransientAiException exception) {
		throw ErrorHandler.chatHandleErrorResponse(exception, ErrorConstants.PROMPTS_OPTIONS_SYSTEM_ERROR);
	}
}
