package com.playapp.bff.function;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.playapp.bff.function.WeatherFunction.Request;
import com.playapp.bff.service.WeatherService;
import com.playapp.bff.service.supplier.bean.WeatherDetailsResponse;

class WeatherFunctionTest {

	@Mock
	private WeatherService weatherService;

	@InjectMocks
	private WeatherFunction weatherFunction;

	@BeforeEach
	void initMocks() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void applyTest() {
		when(weatherService.getBeachesDataByWeather()).thenReturn(List.of(WeatherDetailsResponse.builder().build()));
		assertNotNull(weatherFunction.apply(new Request("test")));
	}

}
