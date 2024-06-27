package com.playapp.bff.service.supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.playapp.bff.service.supplier.bean.DistanceMatrixResponse;

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
}
