package com.playapp.bff.service.supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.playapp.bff.config.ErrorHandler;
import com.playapp.bff.constants.ErrorConstants;
import com.playapp.bff.service.supplier.bean.LocationResponse;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class AccuWeatherRestService.
 */
@Slf4j
@Service
public class AccuWeatherRestService extends WebClientService {

	/** The accu weather api key. */
	private String accuWeatherApiKey;

	/**
	 * Instantiates a new accu weather rest service.
	 *
	 * @param webClientBuilder  the web client builder
	 * @param url               the url
	 * @param accuWeatherApiKey the accu weather api key
	 */
	public AccuWeatherRestService(WebClient.Builder webClientBuilder,
			@Value("${accuweather-forecasts.rest}") String url,
			@Value("${accuweather.apikey}") String accuWeatherApiKey) {
		super(webClientBuilder, url);
		this.accuWeatherApiKey = accuWeatherApiKey;
	}

	/**
	 * Gets the locations code by the geo position.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 * @return the locations
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getLocationsFallback")
	public LocationResponse getLocations(String latitude, String longitude) {
		log.info("Begin - getLocations");
		String latitudeLongitudeForQueryParam = latitude + "," + longitude;
		log.info(latitudeLongitudeForQueryParam);
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(url + "/locations/v1/cities/geoposition/search");
		builder.queryParam("apikey", accuWeatherApiKey);
		builder.queryParam("q", latitudeLongitudeForQueryParam);
		log.info(builder.toUriString());
		LocationResponse locationResponse = webClient.get().uri(builder.build().encode().toUriString()).retrieve()
				.bodyToMono(LocationResponse.class).block();
		log.info("End - getLocations - Response: {}", locationResponse);
		return locationResponse;
	}

	/**
	 * Gets the locations fallback.
	 *
	 * @param latitude  the latitude
	 * @param longitude the longitude
	 * @param exception the exception
	 * @return the locations fallback
	 */
	protected LocationResponse getLocationsFallback(String latitude, String longitude,
			WebClientResponseException exception) {
		throw ErrorHandler.webClientHandleErrorResponse(exception, ErrorConstants.LOCATIONS_ERROR);
	}

	/**
	 * Gets the days weather details for the indicated days (1, 5, 10 or 15), by the
	 * location code.
	 *
	 * @param days         the days
	 * @param locationCode the location code
	 * @return the days weather details
	 */
	@CircuitBreaker(name = "playapp", fallbackMethod = "getWeatherDetailsByDaysFallback")
	public WeatherDetailsResponse getWeatherDetailsByDays(String days, String locationCode) {
		log.info("Begin - getDetails");
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(url + "/forecasts/v1/daily/" + days + "day/" + locationCode);
		builder.queryParam("apikey", accuWeatherApiKey);
		builder.queryParam("language", "es-es");
		builder.queryParam("details", "true");
		builder.queryParam("metric", "true");
		WeatherDetailsResponse weather = webClient.get().uri(builder.build().encode().toUriString()).retrieve()
				.bodyToMono(WeatherDetailsResponse.class).block();
		log.info("End - getDetails - Response: {}", weather);
		return weather;
	}

	/**
	 * Gets the weather details by days fallback.
	 *
	 * @param days         the days
	 * @param locationCode the location code
	 * @param exception    the exception
	 * @return the weather details by days fallback
	 * @throws Exception the exception
	 */
	protected WeatherDetailsResponse getWeatherDetailsByDaysFallback(String days, String locationCode,
			WebClientResponseException exception) {
		throw ErrorHandler.webClientHandleErrorResponse(exception, ErrorConstants.WEATHER_DETAILS_ERROR);
	}

}