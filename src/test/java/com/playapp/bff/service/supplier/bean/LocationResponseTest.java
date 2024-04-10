package com.playapp.bff.service.supplier.bean;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LocationResponseTest {
	@Test
	public void getterAndSetterTest() {
		LocationResponse locationResponse = new LocationResponse();
		int version = 1;
		String key = "0001";
		String type = "";
		locationResponse.setVersion(version);
		locationResponse.setKey(key);
		locationResponse.setType(type);
		assertEquals(version, locationResponse.getVersion());
		assertEquals(key, locationResponse.getKey());
		assertEquals(type, locationResponse.getType());
	}

	@Test
	public void allArgsConstructorTest() {
		int version = 1;
		String key = "0001";
		String type = "";
		LocationResponse locationResponse = new LocationResponse(version, key, type);
		assertEquals(version, locationResponse.getVersion());
		assertEquals(key, locationResponse.getKey());
		assertEquals(type, locationResponse.getType());
	}
}
