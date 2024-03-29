package com.playapp.bff.service.supplier.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class WeatherDetailsResponse.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDetailsResponse {

	/** The name cala. */
	private String beachName;

	/** The daily forecasts. */
	@JsonProperty("DailyForecasts")
	private List<DailyForecast> dailyForecasts;
}
