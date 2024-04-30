package com.playapp.bff.web;

import java.util.List;

import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.bff.service.WeatherService;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.LocationResponse;
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

	private AccuWeatherRestService accuweather;

	/**
	 * Instantiates a new AI chat controller.
	 *
	 * @param chatClient     the chat client
	 * @param weatherService the weather service
	 */
	@Autowired
	public AIChatController(OpenAiChatClient chatClient, WeatherService weatherService,
			AccuWeatherRestService accuweather) {
		this.chatClient = chatClient;
		this.weatherService = weatherService;
		this.accuweather = accuweather;
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

	@GetMapping("/ai/generateMyStream")
	public ChatResponse generateMyStream(
			@RequestParam(value = "message", defaultValue = "Dime a qué playa es mejor ir hoy en Cádiz") String message) {
		// Definir las opciones del chat para llamar a la función "weatherFunction"
		OpenAiChatOptions chatOptions = OpenAiChatOptions.builder().withFunction("weatherFunction").build();

		// Crear el prompt con el mensaje y las opciones del chat
		Prompt prompt = new Prompt(new UserMessage(message), chatOptions);

		// Llamar al cliente de chat para obtener y devolver la respuesta
		return chatClient.call(prompt);
	}

	@GetMapping("/ai/holaMundo")
	public String getDetailsHola() {
		return "Hola Mundo";
	}

	@GetMapping("/ai/rest")
	public LocationResponse getdetailsRest() {
		return accuweather.getLocations("36.502971", "-6.276354");
	}
}
