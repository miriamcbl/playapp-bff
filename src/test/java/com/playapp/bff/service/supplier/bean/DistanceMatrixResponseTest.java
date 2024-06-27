package com.playapp.bff.service.supplier.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class DistanceMatrixResponseTest {

	@Test
	void gettersAndSettersTest() {
		DistanceMatrixResponse response = new DistanceMatrixResponse();
		DistanceMatrixRow row1 = new DistanceMatrixRow();
		DistanceMatrixRow row2 = new DistanceMatrixRow();
		List<DistanceMatrixRow> rows = Arrays.asList(row1, row2);
		response.setRows(rows);
		assertEquals(rows, response.getRows());
	}

	@Test
	void builderTest() {
		DistanceMatrixRow row1 = new DistanceMatrixRow();
		DistanceMatrixRow row2 = new DistanceMatrixRow();
		List<DistanceMatrixRow> rows = Arrays.asList(row1, row2);
		DistanceMatrixResponse response = DistanceMatrixResponse.builder().rows(rows).build();
		assertEquals(rows, response.getRows());
	}

	@Test
	void allArgsConstructorTest() {
		DistanceMatrixRow row1 = new DistanceMatrixRow();
		DistanceMatrixRow row2 = new DistanceMatrixRow();
		List<DistanceMatrixRow> rows = Arrays.asList(row1, row2);
		DistanceMatrixResponse response = new DistanceMatrixResponse(rows);
		assertEquals(rows, response.getRows());
	}

	@Test
	void noArgsConstructorTest() {
		DistanceMatrixResponse response = new DistanceMatrixResponse();
		assertNull(response.getRows());
	}

}
