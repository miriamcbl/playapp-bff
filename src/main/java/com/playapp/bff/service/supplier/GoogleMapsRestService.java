package com.playapp.bff.service.supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.playapp.bff.config.ErrorHandler;
import com.playapp.bff.constants.ErrorConstants;
import com.playapp.bff.service.supplier.bean.DistanceMatrixResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class GoogleMapsRestService.
 */
@Slf4j
@Service
public class GoogleMapsRestService extends WebClientService {

	/** The google maps api key. */
	private String googleMapsApiKey;

	/**
	 * Instantiates a new google maps rest service.
	 *
	 * @param webClientBuilder the web client builder
	 * @param url              the url
	 * @param googleMapsApiKey the google maps api key
	 */
	public GoogleMapsRestService(WebClient.Builder webClientBuilder, 
			@Value("${googlemaps-platform.rest}") String url,
			@Value("${googlemaps.apikey}") String googleMapsApiKey) {
		super(webClientBuilder, url);
		this.googleMapsApiKey = googleMapsApiKey;
	}


	/**
	 * Gets the distance.
	 *
	 * @param origin      the origin
	 * @param destination the destination
	 * @return the distance
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getDistanceFallback")
	public DistanceMatrixResponse getDistance(String origin, String destination) {
		log.info("Begin - getDistance");
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(url + "/maps/api/distancematrix/json");
		builder.queryParam("origins", origin);
		builder.queryParam("destinations", destination);
		builder.queryParam("key", googleMapsApiKey);
		log.info(builder.toUriString());
		DistanceMatrixResponse distanceResponse = webClient.get().uri(builder.build().encode().toUriString()).retrieve()
				.bodyToMono(DistanceMatrixResponse.class).block();
		log.info("End - getDistance");
		return distanceResponse;
	}

	/**
	 * Gets the distance fallback.
	 *
	 * @param origin      the origin
	 * @param destination the destination
	 * @param exception   the exception
	 * @return the distance fallback
	 */
	protected DistanceMatrixResponse getDistanceFallback(String origin, String destination,
			WebClientResponseException exception) {
		throw ErrorHandler.webClientHandleErrorResponse(exception, ErrorConstants.DISTANCES_ERROR);
	}
}
