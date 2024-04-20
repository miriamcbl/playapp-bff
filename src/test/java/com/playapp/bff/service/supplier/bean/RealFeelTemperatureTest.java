package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RealFeelTemperatureTest {

	@Test
	void getterAndSetterTest() {
		RealFeelTemperature realFeelTemperature = new RealFeelTemperature();
		Measurement minimum = new Measurement();
		Measurement maximum = new Measurement();
		realFeelTemperature.setMinimum(minimum);
		realFeelTemperature.setMaximum(maximum);
		assertEquals(minimum, realFeelTemperature.getMinimum());
		assertEquals(maximum, realFeelTemperature.getMaximum());
	}

	@Test
	void allArgsConstructorTest() {
		Measurement minimum = new Measurement();
		Measurement maximum = new Measurement();
		RealFeelTemperature realFeelTemperature = new RealFeelTemperature(minimum, maximum);
		assertEquals(minimum, realFeelTemperature.getMinimum());
		assertEquals(maximum, realFeelTemperature.getMaximum());
	}

}
