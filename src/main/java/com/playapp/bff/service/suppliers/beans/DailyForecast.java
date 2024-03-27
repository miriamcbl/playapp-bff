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
public class DailyForecast {
	@JsonProperty("Date")
	private String date;

	@JsonProperty("EpochDate")
	private long epochDate;
	@JsonProperty("Temperature")
	private Temperature temperature;
	@JsonProperty("RealFeelTemperature")
	private RealFeelTemperature realFeelTemperature;
}
