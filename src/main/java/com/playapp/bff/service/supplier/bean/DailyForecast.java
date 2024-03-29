package com.playapp.bff.service.supplier.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class DailyForecast.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyForecast {

	/** The temperature. */
	@JsonProperty("Temperature")
	private Temperature temperature;

	/** The real feel temperature. */
	@JsonProperty("RealFeelTemperature")
	private RealFeelTemperature realFeelTemperature;

	@JsonProperty("Day")
	private DayDetails day;
}
