package com.playapp.bff.web;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.bff.service.suppliers.AccuWeatherRestService;
import com.playapp.bff.service.suppliers.beans.WeatherDetails;

import reactor.core.publisher.Flux;

/**
 * The Class AIChatController.
 */
@RestController
public class AIChatController {

	/** The chat client. */
    private final OpenAiChatClient chatClient;

	/** The rest service. */
	private AccuWeatherRestService restService;

	/**
	 * Instantiates a new AI chat controller.
	 *
	 * @param chatClient  the chat client
	 * @param restService the rest service
	 */
    @Autowired
	public AIChatController(OpenAiChatClient chatClient, AccuWeatherRestService restService) {
        this.chatClient = chatClient;
		this.restService = restService;
    }

	/**
	 * Generate.
	 *
	 * @param message the message
	 * @return the string
	 */
    @GetMapping("/ai/generate")
	public String generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return chatClient.call(message);
    }

	/**
	 * Generate a stream from prompts
	 * 
	 * @param message
	 * @return
	 */
	@GetMapping("/ai/generateStream")
	public Flux<ChatResponse> generateStream(
			@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		Prompt prompt = new Prompt(new UserMessage(message));
		Flux<ChatResponse> cont = chatClient.stream(prompt);
		return cont;
	}

	/**
	 * Gets the weather details - just an endpoint for testing purposes.
	 *
	 * @return the weather details
	 */
	@GetMapping("/ai/getDetails")
	public WeatherDetails getWeatherDetails() {
		return restService.getDetails();
	}
}
