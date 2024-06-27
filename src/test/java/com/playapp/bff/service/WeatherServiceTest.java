package com.playapp.bff.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.Generation;

import com.playapp.bff.bean.LocationCode;
import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.DailyForecast;
import com.playapp.bff.service.supplier.bean.DayDetails;
import com.playapp.bff.service.supplier.bean.DistanceMatrixElement;
import com.playapp.bff.service.supplier.bean.DistanceMatrixResponse;
import com.playapp.bff.service.supplier.bean.DistanceMatrixRow;
import com.playapp.bff.service.supplier.bean.Duration;
import com.playapp.bff.service.supplier.bean.LocationResponse;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;
import com.playapp.bff.service.supplier.bean.Wind;
import com.playapp.bff.service.supplier.bean.WindDirection;
import com.playapp.bff.service.supplier.bean.WindSpeed;

class WeatherServiceTest {

	@Mock
	private AccuWeatherRestService accuWeatherRestService;

	@Mock
	private WeatherMapper weatherMapper;

	@Mock
	private ChatService chatService;

	/** The maps service. */
	@Mock
	private MapsService mapsService;

	@InjectMocks
	private WeatherService service;

	private String today;

	@BeforeEach
	void initMocks() {
		MockitoAnnotations.openMocks(this);
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		today = currentDate.format(formatter);
	}

	@Test
	void getBeachesDataMuchWindTest() {
		when(accuWeatherRestService.getLocations(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationResponse.builder().key("123455").build());
		when(accuWeatherRestService.getWeatherDetailsByDays(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(
						WeatherDetailsResponse.builder()
								.dailyForecasts(List.of(DailyForecast.builder().day(DayDetails.builder()
										.wind(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
												.speed(WindSpeed.builder().value(40.0).build()).build())
										.build()).build()))
								.build());
		assertNotNull(service.getBeachesDataByWeather(today, "CHICLANA", 0, 10));
	}

	@Test
	void getBeachesDataTest() {
		when(mapsService.getDistance(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(DistanceMatrixResponse.builder().rows(List.of(
						DistanceMatrixRow.builder().elements(List.of(
								DistanceMatrixElement.builder().duration(Duration.builder().value(5).build()).build()))
								.build()))
						.build());
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatService.getChatResponseByPrompts(anyString())).thenReturn(chatResponse);
		when(accuWeatherRestService.getLocations(Mockito.any(), Mockito.any()))
				.thenReturn(LocationResponse.builder().key("12").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("212").name("playa").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("213").name("playa").build());
		List<DailyForecast> forecasts = new ArrayList<>();
		DailyForecast forecast = DailyForecast.builder()
				.day(DayDetails.builder()
						.wind(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
								.speed(WindSpeed.builder().value(20.0).build()).build())
						.windGust(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
								.speed(WindSpeed.builder().value(20.0).build()).build())
						.build())
				.build();
		forecasts.add(forecast);
		WeatherDetailsResponse responseWeather = WeatherDetailsResponse.builder().beachName("playa")
				.dailyForecasts(forecasts).build();
		when(accuWeatherRestService.getWeatherDetailsByDays(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(responseWeather);
		assertNotNull(service.getBeachesDataByWeather(today, "CHICLANA", 0, 10));
	}

	@Test
	void getBeachesDataTest2() {
		when(mapsService.getDistance(Mockito.anyString(), Mockito.anyString())).thenReturn(DistanceMatrixResponse
				.builder()
				.rows(List.of(DistanceMatrixRow.builder()
						.elements(List.of(
								DistanceMatrixElement.builder().duration(Duration.builder().value(5).build()).build()))
						.build()))
				.build());
		when(accuWeatherRestService.getLocations(Mockito.any(), Mockito.any()))
				.thenReturn(LocationResponse.builder().key("12").build());
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatService.getChatResponseByPrompts(anyString())).thenReturn(chatResponse);
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("212").name("playa").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("213").name("playa").build());
		List<DailyForecast> forecasts = new ArrayList<>();
		DailyForecast forecast = DailyForecast.builder()
				.day(DayDetails.builder()
						.wind(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
								.speed(WindSpeed.builder().value(20.0).build()).build())
						.windGust(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
								.speed(WindSpeed.builder().value(20.0).build()).build())
						.build())
				.build();
		forecasts.add(forecast);
		WeatherDetailsResponse responseWeather = WeatherDetailsResponse.builder().beachName("playa")
				.dailyForecasts(forecasts).build();
		when(accuWeatherRestService.getWeatherDetailsByDays(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(responseWeather);
		assertNotNull(service.getBeachesDataByWeather(today, "CHICLANA", 0, 10));
	}

	@Test
	void getBeachesDataTest3() {
		when(mapsService.getDistance(Mockito.anyString(), Mockito.anyString())).thenReturn(DistanceMatrixResponse
				.builder()
				.rows(List.of(DistanceMatrixRow.builder()
						.elements(List.of(
								DistanceMatrixElement.builder().duration(Duration.builder().value(5).build()).build()))
						.build()))
				.build());
		when(accuWeatherRestService.getLocations(Mockito.any(), Mockito.any()))
				.thenReturn(LocationResponse.builder().key("12").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("212").name("playa").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("213").name("playa").build());
		when(accuWeatherRestService.getWeatherDetailsByDays(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		assertNotNull(service.getBeachesDataByWeather(today, "CHICLANA", 0, 10));
	}

	@Test
	void getBeachesDataItsRainingTest4() {
		when(mapsService.getDistance(Mockito.anyString(), Mockito.anyString())).thenReturn(DistanceMatrixResponse
				.builder()
				.rows(List.of(DistanceMatrixRow.builder()
						.elements(List.of(
								DistanceMatrixElement.builder().duration(Duration.builder().value(5).build()).build()))
						.build()))
				.build());
		when(accuWeatherRestService.getLocations(Mockito.any(), Mockito.any()))
				.thenReturn(LocationResponse.builder().key("12").build());
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatService.getChatResponseByPrompts(anyString())).thenReturn(chatResponse);
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("212").name("playa").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("213").name("playa").build());
		List<DailyForecast> forecasts = new ArrayList<>();
		DailyForecast forecast = DailyForecast.builder().day(DayDetails.builder().hasPrecipitation(true).build())
				.build();
		forecasts.add(forecast);
		WeatherDetailsResponse responseWeather = WeatherDetailsResponse.builder().beachName("playa")
				.dailyForecasts(forecasts).build();
		when(accuWeatherRestService.getWeatherDetailsByDays(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(responseWeather);
		assertNotNull(service.getBeachesDataByWeather(today, "CHICLANA", 0, 10));
	}

	@Test
	void getBeachesDataSpecialConditionTest5() {
		when(mapsService.getDistance(Mockito.anyString(), Mockito.anyString())).thenReturn(DistanceMatrixResponse
				.builder()
				.rows(List.of(DistanceMatrixRow.builder()
						.elements(List.of(
								DistanceMatrixElement.builder().duration(Duration.builder().value(5).build()).build()))
						.build()))
				.build());
		Generation generation = new Generation("");
		ChatResponse chatResponse = new ChatResponse(List.of(generation));
		when(chatService.getChatResponseByPrompts(anyString())).thenReturn(chatResponse);
		when(accuWeatherRestService.getLocations(Mockito.any(), Mockito.any()))
				.thenReturn(LocationResponse.builder().key("12").build());
		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationCode.builder().code("213").name("PLAYA_DE_BOLONIA").build());
		List<DailyForecast> forecasts = new ArrayList<>();
		DailyForecast forecast = DailyForecast.builder()
				.day(DayDetails.builder()
						.wind(Wind.builder().direction(WindDirection.builder().localized("OSO").build())
								.speed(WindSpeed.builder().value(10.0).build()).build())
						.windGust(Wind.builder().direction(WindDirection.builder().localized("OSO").build())
								.speed(WindSpeed.builder().value(10.0).build()).build())
						.build())
				.build();
		forecasts.add(forecast);
		WeatherDetailsResponse responseWeather = WeatherDetailsResponse.builder().beachName("PLAYA_DE_BOLONIA")
				.dailyForecasts(forecasts).build();
		when(accuWeatherRestService.getWeatherDetailsByDays(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(responseWeather);
		assertNotNull(service.getBeachesDataByWeather(today, "CHICLANA", 0, 10));
	}

}
