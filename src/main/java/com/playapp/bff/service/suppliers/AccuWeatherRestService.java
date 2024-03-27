package com.playapp.bff.service.suppliers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.playapp.bff.service.suppliers.beans.WeatherDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccuWeatherRestService extends WebClientService {


	private String accuWeatherApiKey;

	public AccuWeatherRestService(WebClient.Builder webClientBuilder,
			@Value("${env.accuweather-day.rest}") String url,
			@Value("${env.accuweather.apikey}") String accuWeatherApiKey) {
		super(webClientBuilder, url);
		this.accuWeatherApiKey = accuWeatherApiKey;
	}

	public WeatherDetails getDetails() {
		log.info("Begin - getDetails");
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(
				url + "/2327930");
		builder.queryParam("apikey", accuWeatherApiKey);
		builder.queryParam("language", "es-es");
		builder.queryParam("details", "true");
		builder.queryParam("metric", "true");
		WeatherDetails weather = webClient.get().uri(builder.build().encode().toUriString())
				.retrieve()
				.bodyToMono(WeatherDetails.class).block();
		log.info("End - getDetails - Response: {}", weather);
		return weather;
	}

}