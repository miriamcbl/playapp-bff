package com.playapp.bff.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.playapp.bff.bean.MessageResponse;
import com.playapp.bff.config.CommonApiResponse;
import com.playapp.bff.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;

/**
 * The Class AIChatController.
 */
@CrossOrigin(origins = "${cors.allowed.origins}")
@RestController
@Tag(name = "chat-controller", description = "Controller to manage calls and responses to the AI")
@RequestMapping("/v1")
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

	@Operation(summary = "Operation to ask the AI something", description = "Operation to ask the AI something")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = String.class))) })
	@CommonApiResponse
	@GetMapping("/chat/getBasicResponse")
	public String getBasicResponse(
			@RequestParam(value = "message", defaultValue = "Cuéntame un chiste") String message) {
		return chatService.getBasicChatResponse(message);
	}

	@Operation(summary = "Operation to obtain the three best beaches of Cadiz given a date, "
			+ "according to its meteorological conditions", description = "Operation to obtain "
					+ "the three best beaches of Cadiz given a date, according to its meteorological "
					+ "conditions, such as weather and wind. ")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "OK",
					content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = MessageResponse.class))) })
	@CommonApiResponse
	@GetMapping("/chat/getBeachesRecommended")
	public MessageResponse getBeachesRecommended(
			@RequestParam(value = "message", required = true, defaultValue = "Dime a qué playa es mejor ir hoy en Cádiz") 
			String message, HttpSession session) {
		// Llamar al cliente de chat para obtener y devolver la respuesta
		return chatService.getBeachesRecommended(message, session);
	}

}