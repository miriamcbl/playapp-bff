package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DailyForecastTest {

	@Test
	void getterAndSetterTest() {
		DailyForecast forecast = new DailyForecast();
		Temperature temperature = new Temperature();
		forecast.setTemperature(temperature);
		DayDetails dayDetails = new DayDetails();
		forecast.setDay(dayDetails);
		RealFeelTemperature realFeelTemperature = new RealFeelTemperature();
		forecast.setRealFeelTemperature(realFeelTemperature);
		assertEquals(temperature, forecast.getTemperature());
		assertEquals(realFeelTemperature, forecast.getRealFeelTemperature());
		assertEquals(dayDetails, forecast.getDay());
	}

	@Test
	void allArgsConstructorTest() {
		Temperature temperature = new Temperature();
		DayDetails dayDetails = new DayDetails();
		RealFeelTemperature realFeelTemperature = new RealFeelTemperature();
		DailyForecast forecast = new DailyForecast(temperature, realFeelTemperature, dayDetails);
		assertEquals(temperature, forecast.getTemperature());
		assertEquals(realFeelTemperature, forecast.getRealFeelTemperature());
		assertEquals(dayDetails, forecast.getDay());
	}

}
