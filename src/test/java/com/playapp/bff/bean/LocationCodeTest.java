package com.playapp.bff.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class LocationCodeTest {

	@Test
	void testConstructorAndGetters() {
		String name = "Playa Bolonia";
		String code = "11";
		LocationCode locationCode = new LocationCode(name, code);
		assertNotNull(locationCode);
		assertEquals(code, locationCode.getCode());
		assertEquals(name, locationCode.getName());
	}

	@Test
	void testBuilder() {
		String name = "Playa Bolonia";
		String code = "11";
		LocationCode locationCode = LocationCode.builder().name(name).code(code).build();
		assertNotNull(locationCode);
		assertEquals(code, locationCode.getCode());
		assertEquals(name, locationCode.getName());
	}

	@Test
	void testSetters() {
		String name = "Playa Bolonia";
		String code = "11";
		LocationCode locationCode = new LocationCode();
		locationCode.setName(name);
		locationCode.setCode(code);
		assertNotNull(locationCode);
		assertEquals(name, locationCode.getName());
		assertEquals(code, locationCode.getCode());
	}

}
