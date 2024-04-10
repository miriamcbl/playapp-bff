package com.playapp.bff.service.supplier.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RealFeelTemperatureTest {

	@Test
	public void getterAndSetterTest() {
		RealFeelTemperature realFeelTemperature = new RealFeelTemperature();
		Measurement minimum = new Measurement();
		Measurement maximum = new Measurement();
		realFeelTemperature.setMinimum(minimum);
		realFeelTemperature.setMaximum(maximum);
		assertEquals(minimum, realFeelTemperature.getMinimum());
		assertEquals(maximum, realFeelTemperature.getMaximum());
	}

	@Test
	public void allArgsConstructorTest() {
		Measurement minimum = new Measurement();
		Measurement maximum = new Measurement();
		RealFeelTemperature realFeelTemperature = new RealFeelTemperature(minimum, maximum);
		assertEquals(minimum, realFeelTemperature.getMinimum());
		assertEquals(maximum, realFeelTemperature.getMaximum());
	}

}
