package com.playapp.bff.service.suppliers.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Measurement {
	@JsonProperty("Value")
	private double value;
	@JsonProperty("Unit")
	private String unit;
	@JsonProperty("UnitType")
	private int unitType;
}