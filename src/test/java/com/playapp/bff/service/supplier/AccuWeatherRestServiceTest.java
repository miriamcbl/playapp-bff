package com.playapp.bff.service.supplier;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class AccuWeatherRestServiceTest {

	private AccuWeatherRestService restService;

	private MockWebServer server;

	@Before
	public void init() {
		this.server = new MockWebServer();
		this.restService = new AccuWeatherRestService(WebClient.builder(), server.url("/").toString(), "");
	}


	@Test
	public void getLocationsTest() {
		server.enqueue(new MockResponse().setStatus("HTTP/1.1 200").addHeader(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON_VALUE));
		assertDoesNotThrow(() -> restService.getLocations("", ""));
	}

	@Test
	public void getDetailsTest() {
		server.enqueue(new MockResponse().setStatus("HTTP/1.1 200").addHeader(HttpHeaders.CONTENT_TYPE,
				MediaType.APPLICATION_JSON_VALUE));
		assertDoesNotThrow(() -> restService.getDetails(""));
	}

}