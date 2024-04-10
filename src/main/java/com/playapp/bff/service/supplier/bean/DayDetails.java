package com.playapp.bff.service.supplier.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class DayDetails.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DayDetails {

	/** The wind. */
	@JsonProperty("Wind")
	private Wind wind;

	/** The wind gusts. */
	@JsonProperty("WindGust")
	private Wind windGust;
}
