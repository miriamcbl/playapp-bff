package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TemperatureTest {

	@Test
	void getterAndSetterTest() {
		Temperature temperature = new Temperature();
		Measurement minimum = new Measurement();
		Measurement maximum = new Measurement();
		temperature.setMinimum(minimum);
		temperature.setMaximum(maximum);
		assertEquals(minimum, temperature.getMinimum());
		assertEquals(maximum, temperature.getMaximum());
	}

	@Test
	void allArgsConstructorTest() {
		Measurement minimum = new Measurement();
		Measurement maximum = new Measurement();
		Temperature temperature = new Temperature(minimum, maximum);
		assertEquals(minimum, temperature.getMinimum());
		assertEquals(maximum, temperature.getMaximum());
	}
}
