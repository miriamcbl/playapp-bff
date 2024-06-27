package com.playapp.bff.service.supplier.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class DistanceMatrixResponse.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistanceMatrixResponse {

	/** The rows. */
	private List<DistanceMatrixRow> rows;
}
