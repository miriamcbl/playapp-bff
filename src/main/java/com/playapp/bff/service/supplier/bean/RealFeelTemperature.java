package com.playapp.bff.service.supplier.bean;

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
public class RealFeelTemperature {
	@JsonProperty("Minimun")
	private Measurement minimum;
	@JsonProperty("Maximum")
	private Measurement maximum;
}
