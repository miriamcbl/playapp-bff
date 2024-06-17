package com.playapp.bff.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.bff.bean.MessageResponse;
import com.playapp.bff.service.ChatService;

/**
 * The Class AIChatController.
 */
@CrossOrigin(origins = "${cors.allowed.origins}")
@RestController
public class AIChatController {

	/** The chat service. */
	private ChatService chatService;

	/**
	 * Instantiates a new AI chat controller.
	 *
	 * @param chatClient     the chat client
	 * @param weatherService the weather service
	 */
	@Autowired
	public AIChatController(ChatService chatService) {
		this.chatService = chatService;
	}

	/**
	 * Generate.
	 *
	 * @param message the message
	 * @return the string
	 */
	@GetMapping("/chat/getBasicResponse")
	public String getBasicResponse(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
		return chatService.getBasicChatResponse(message);
	}

	@GetMapping("/chat/getBeachesRecommended")
	public MessageResponse getBeachesRecommended(
			@RequestParam(value = "message", defaultValue = "Dime a qué playa es mejor ir hoy en Cádiz") String message) {
		// Llamar al cliente de chat para obtener y devolver la respuesta
		return chatService.getBeachesRecommended(message);
	}

}
