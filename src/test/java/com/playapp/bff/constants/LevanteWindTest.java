package com.playapp.bff.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

class LevanteWindTest {

	@Test
	void getShortNameTest() {
		assertEquals("E", LevanteWind.ESTE.getShortName());
		assertEquals("ESE", LevanteWind.ESTESURESTE.getShortName());
		assertEquals("SE", LevanteWind.SUERESTE.getShortName());
	}
}