package com.playapp.bff.service.supplier;

import org.springframework.web.reactive.function.client.WebClient;

/**
 * The Class WebClientService.
 */
public class WebClientService {

	/** The web client. */
	protected WebClient webClient;

	/** The url. */
	protected String url;

	/**
	 * Instantiates a new web client service.
	 *
	 * @param webClientBuilder the web client builder
	 * @param url              the url
	 */
	protected WebClientService(WebClient.Builder webClientBuilder, String url) {
		this.webClient = webClientBuilder.baseUrl(url).build();
		this.url = url;
	}
}
