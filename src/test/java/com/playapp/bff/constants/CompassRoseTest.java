package com.playapp.bff.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CompassRoseTest {
	@Test
	public void getShortNameTest() {
		assertEquals("N", CompassRose.NORTE.getShortName());
		assertEquals("E", CompassRose.ESTE.getShortName());
		assertEquals("ESE", CompassRose.ESTESURESTE.getShortName());
		assertEquals("SE", CompassRose.SUERESTE.getShortName());
		assertEquals("SSE", CompassRose.SURSURESTE.getShortName());
		assertEquals("O", CompassRose.OESTE.getShortName());
		assertEquals("SSO", CompassRose.SURSUROESTE.getShortName());
		assertEquals("SO", CompassRose.SUROESTE.getShortName());
		assertEquals("OSO", CompassRose.OESTESUROESTE.getShortName());
		assertEquals("S", CompassRose.SUR.getShortName());
	}

}
