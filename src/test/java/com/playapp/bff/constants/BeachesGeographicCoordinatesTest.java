package com.playapp.bff.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BeachesGeographicCoordinatesTest {

	@Test
	void allEnumValuesTest() {
		for (BeachesGeographicCoordinates beach : BeachesGeographicCoordinates.values()) {
			assertNotNull(beach.getLatitude());
			assertNotNull(beach.getLongitude());
			assertNotNull(beach.getSuitableForLevante());
			assertNotNull(beach.getSpecialConditionForWindGust());
		}
	}

	@Test
	void getLatitudeTest() {
		assertEquals("36.307785", BeachesGeographicCoordinates.CALA_ROCHE.getLatitude());
	}

	@Test
	void getLongitudeTest() {
		assertEquals("-6.151288", BeachesGeographicCoordinates.CALA_ROCHE.getLongitude());
	}

	@Test
	void getSuitableForLevanteTest() {
		assertTrue(BeachesGeographicCoordinates.CALA_ROCHE.getSuitableForLevante());
	}

	@Test
	void getSpecialConditionForWindGustTest() {
		assertFalse(BeachesGeographicCoordinates.CALA_ROCHE.getSpecialConditionForWindGust());
	}

	@Test
	void getLatLonTest() {
		assertEquals("36.307785,-6.151288", BeachesGeographicCoordinates.CALA_ROCHE.getLatLon());
	}


}
