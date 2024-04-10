package com.playapp.bff.service.supplier.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class WindSpeed.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WindSpeed {

	/** The value. */
	@JsonProperty("Value")
	private double value;

	/** The unit. */
	@JsonProperty("Unit")
	private String unit;

	/** The unit type. */
	@JsonProperty("UnitType")
	private int unitType;
}
