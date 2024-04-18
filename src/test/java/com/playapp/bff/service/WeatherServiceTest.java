package com.playapp.bff.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.DailyForecast;
import com.playapp.bff.service.supplier.bean.DayDetails;
import com.playapp.bff.service.supplier.bean.LocationResponse;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;
import com.playapp.bff.service.supplier.bean.Wind;
import com.playapp.bff.service.supplier.bean.WindDirection;
import com.playapp.bff.service.supplier.bean.WindSpeed;

public class WeatherServiceTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	/** The accu weather rest service. */
	@Mock
	private AccuWeatherRestService accuWeatherRestService;

	/** The weather mapper. */
	@Mock
	private WeatherMapper weatherMapper;

	@InjectMocks
	private WeatherService service;

	@Before
	public void initMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void getBeachesDataMuchWind() {
		when(accuWeatherRestService.getLocations(Mockito.anyString(), Mockito.anyString()))
				.thenReturn(LocationResponse.builder().key("123455").build());
		when(accuWeatherRestService.getDetails(Mockito.anyString()))
				.thenReturn(WeatherDetailsResponse.builder()
						.dailyForecasts(List.of(DailyForecast.builder().day(DayDetails.builder()
								.wind(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
										.speed(WindSpeed.builder().value(40.0).build()).build())
								.build())
								.build()))
						.build());
		assertNotNull(service.getBeachesDataByWeather());
	}

//	@Test
//	public void getBeachesData() {
//		when(accuWeatherRestService.getLocations(Mockito.anyString(), Mockito.anyString()))
//				.thenReturn(LocationResponse.builder().key("123455").build());
//		when(accuWeatherRestService.getDetails(Mockito.anyString())).thenReturn(WeatherDetailsResponse.builder()
//				.dailyForecasts(List.of(DailyForecast.builder()
//						.day(DayDetails.builder()
//								.wind(Wind.builder().direction(WindDirection.builder().localized("ESE").build())
//										.speed(WindSpeed.builder().value(20.0).build()).build())
//								.build())
//						.build()))
//				.build());
//		assertNotNull(service.getBeachesDataByWeather());
//	}



}
