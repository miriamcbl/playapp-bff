package com.playapp.bff.service.supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.util.UriComponentsBuilder;

import com.playapp.bff.config.ErrorHandler;
import com.playapp.bff.service.supplier.bean.LocationResponse;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;

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
	public LocationResponse getLocations(String latitude, String longitude) {
		log.info("Begin - getLocations");
		try {
			String latitudeLongitudeForQueryParam = latitude + "," + longitude;
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(url + "/locations/v1/cities/geoposition/search");
			builder.queryParam("apikey", accuWeatherApiKey);
			builder.queryParam("q", latitudeLongitudeForQueryParam);
			LocationResponse locationResponse = webClient.get().uri(builder.build().encode().toUriString()).retrieve()
					.bodyToMono(LocationResponse.class).block();
			log.info("End - getLocations - Response: {}", locationResponse);
			return locationResponse;
		} catch (WebClientResponseException ex) {
			throw ErrorHandler.webClientHandleErrorResponse(ex, "Error al obtener los detalles del clima");
		}
	}

	/**
	 * Gets the days weather details for the indicated days (1, 5, 10 or 15), by the
	 * location code.
	 *
	 * @param days         the days
	 * @param locationCode the location code
	 * @return the days weather details
	 */
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

	public WeatherDetailsResponse getWeatherDetailsByDaysFallback(String days, String locationCode, Throwable throwable)
			throws Exception {
		throw new Exception("Error al obtener el tiempo por código");
	}

}