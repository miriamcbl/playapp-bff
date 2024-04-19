package com.playapp.bff.web;

import java.util.List;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.bff.service.WeatherService;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;

import reactor.core.publisher.Flux;

/**
 * The Class AIChatController.
 */
@RestController
public class AIChatController {

	/** The chat client. */
    private final OpenAiChatClient chatClient;

	/** The weather service. */
	private WeatherService weatherService;

	/**
	 * Instantiates a new AI chat controller.
	 *
	 * @param chatClient     the chat client
	 * @param weatherService the weather service
	 */
    @Autowired
	public AIChatController(OpenAiChatClient chatClient,
			WeatherService weatherService) {
        this.chatClient = chatClient;
		this.weatherService = weatherService;
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
	public List<WeatherDetailsResponse> getWeatherDetails() {
		return weatherService.getBeachesDataByWeather();
	}
}
