package com.playapp.bff.service.supplier.bean;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Row.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistanceMatrixRow {

	/** The elements. */
	private List<DistanceMatrixElement> elements;
}
