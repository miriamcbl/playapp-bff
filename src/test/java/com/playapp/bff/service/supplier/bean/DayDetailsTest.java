package com.playapp.bff.service.supplier.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DayDetailsTest {

	@Test
	public void getterAndSetterTest() {
		DayDetails dayDetails = new DayDetails();
		Wind wind = new Wind();
		Wind windGust = new Wind();
		dayDetails.setWind(wind);
		dayDetails.setWindGust(windGust);
		assertEquals(wind, dayDetails.getWind());
		assertEquals(windGust, dayDetails.getWindGust());
	}

	@Test
	public void allArgsConstructorTest() {
		Wind wind = new Wind();
		Wind windGust = new Wind();
		DayDetails dayDetails = new DayDetails(wind, windGust);
		assertEquals(wind, dayDetails.getWind());
		assertEquals(windGust, dayDetails.getWindGust());
	}
}
