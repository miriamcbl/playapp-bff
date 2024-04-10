package com.playapp.bff.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.playapp.bff.bean.LocationCode;
import com.playapp.bff.mapper.WeatherMapper;
import com.playapp.bff.service.supplier.AccuWeatherRestService;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;

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


	@Test
	public void getBeachWeathersTest() {
		when(accuWeatherRestService.getDetails(Mockito.anyString()))
				.thenReturn(WeatherDetailsResponse.builder().beachName("test").build());
		assertNotNull(service.getBeachWeathers(List.of(LocationCode.builder().code("test").build())));
	}

//	@Test
//	public void getInformationDataTest() {
//		when(accuWeatherRestService.getDetails(Mockito.anyString())).thenReturn(WeatherDetailsResponse.builder()
//				.beachName("PLAYA_LA_CALETA")
//				.dailyForecasts(List.of(DailyForecast.builder()
//						.day(DayDetails.builder().wind(
//								Wind.builder().direction(WindDirection.builder().localized("test").build())
//										.speed(WindSpeed.builder().value(23.3d).build()).build())
//								.build())
//						.build()))
//				.beachName("test").build());
//		when(accuWeatherRestService.getLocations(Mockito.anyString(), Mockito.anyString()))
//				.thenReturn(LocationResponse.builder().type("").version(0).key("0001").build());
//		when(weatherMapper.mapToLocationCode(Mockito.anyString(), Mockito.anyString()))
//				.thenReturn(LocationCode.builder().name("test").code("0001").build());
//		List<WeatherDetailsResponse> weatherDetails = List.of(
//				WeatherDetailsResponse.builder().beachName("PLAYA_LA_CALETA")
//						.dailyForecasts(List.of(DailyForecast.builder().day(DayDetails.builder()
//								.wind(Wind.builder().direction(WindDirection.builder().localized("test").build())
//										.speed(WindSpeed.builder().value(23.3d).build()).build())
//								.build()).build()))
//						.beachName("test").build(),
//				WeatherDetailsResponse.builder().beachName("PLAYA_DE_CAMPOSOTO")
//						.dailyForecasts(List.of(DailyForecast.builder().day(DayDetails.builder()
//								.wind(Wind.builder().direction(WindDirection.builder().localized("test").build())
//										.speed(WindSpeed.builder().value(15.3d).build()).build())
//								.build()).build()))
//						.beachName("test").build());
//		when(service.getBeachWeathers(Mockito.any())).thenReturn(weatherDetails);
//		assertNotNull(service.getInformationData());
//	}

}
