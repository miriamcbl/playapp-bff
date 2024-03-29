package com.playapp.bff.service.supplier.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class WindDirection.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WindDirection {

	/** The degrees. */
	@JsonProperty("Degrees")
	private int degrees;

	/** The localized. */
	@JsonProperty("Localized")
	private String localized;
}
