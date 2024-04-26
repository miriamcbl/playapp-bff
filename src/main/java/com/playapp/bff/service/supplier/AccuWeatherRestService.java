package com.playapp.bff.service.supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

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
	 * Gets the details.
	 *
	 * @return the details
	 */
	public WeatherDetailsResponse getDetails(String locationCode) {
		log.info("Begin - getDetails");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				url + "/forecasts/v1/daily/1day/" + locationCode);
		builder.queryParam("apikey", accuWeatherApiKey);
		builder.queryParam("language", "es-es");
		builder.queryParam("details", "true");
		builder.queryParam("metric", "true");
		WeatherDetailsResponse weather = webClient.get().uri(builder.build().encode().toUriString())
				.retrieve()
				.bodyToMono(WeatherDetailsResponse.class).block();
		log.info("End - getDetails - Response: {}", weather);
		return weather;
	}

	public LocationResponse getLocations(String latitude, String longitude) {
		log.info("Begin - getLocations");
		String latitudeLongitudeForQueryParam = latitude + "," + longitude;
		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(url + "/locations/v1/cities/geoposition/search");
		builder.queryParam("apikey", accuWeatherApiKey);
		builder.queryParam("q", latitudeLongitudeForQueryParam);
		LocationResponse locationResponse = webClient.get().uri(builder.build().encode().toUriString()).retrieve()
				.bodyToMono(LocationResponse.class).block();
		log.info("End - getLocations - Response: {}", locationResponse);
		return locationResponse;
	}

}