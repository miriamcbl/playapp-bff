package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MeasurementTest {

	@Test
	void getterAndSetterTest() {
		Measurement measurement = new Measurement();
		double value = 21;
		String unit = "C";
		int unitType = 0;
		measurement.setValue(value);
		measurement.setUnit(unit);
		measurement.setUnitType(unitType);
		assertEquals(unit, measurement.getUnit());
		assertEquals(unitType, measurement.getUnitType());
	}

	@Test
	void allArgsConstructorTest() {
		double value = 21;
		String unit = "C";
		int unitType = 1;
		Measurement measurement = new Measurement(value, unit, unitType);
		assertEquals(unit, measurement.getUnit());
		assertEquals(unitType, measurement.getUnitType());
	}

}
