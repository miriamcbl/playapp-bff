package com.playapp.bff.service.supplier.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Wind.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wind {

	/** The speed. */
	@JsonProperty("Speed")
	private WindSpeed speed;

	/** The direction. */
	@JsonProperty("Direction")
	private WindDirection direction;
}
