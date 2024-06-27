package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class DistanceMatrixElementTest {

	@Test
	void gettersAndSettersTest() {
		DistanceMatrixElement element = new DistanceMatrixElement();
		Duration duration = Duration.builder().value(300).build();
		element.setDuration(duration);
		assertEquals(duration, element.getDuration());
	}

	@Test
	void builderTest() {
		Duration duration = Duration.builder().value(300).build();
		DistanceMatrixElement element = DistanceMatrixElement.builder().duration(duration).build();
		assertEquals(duration, element.getDuration());
	}

	@Test
	void allArgsConstructoTest() {
		Duration duration = Duration.builder().value(300).build();
		DistanceMatrixElement element = new DistanceMatrixElement(duration);
		assertEquals(duration, element.getDuration());
	}

	@Test
	void noArgsConstructorTest() {
		DistanceMatrixElement element = new DistanceMatrixElement();
		assertNull(element.getDuration());
	}

}
