package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DayDetailsTest {

	@Test
	void getterAndSetterTest() {
		DayDetails dayDetails = new DayDetails();
		Wind wind = new Wind();
		Wind windGust = new Wind();
		dayDetails.setWind(wind);
		dayDetails.setWindGust(windGust);
		assertEquals(wind, dayDetails.getWind());
		assertEquals(windGust, dayDetails.getWindGust());
	}

	@Test
	void allArgsConstructorTest() {
		Wind wind = new Wind();
		Wind windGust = new Wind();
		DayDetails dayDetails = new DayDetails(wind, windGust, false);
		assertEquals(wind, dayDetails.getWind());
		assertEquals(windGust, dayDetails.getWindGust());
	}
}
