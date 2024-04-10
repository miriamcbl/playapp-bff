package com.playapp.bff.service.supplier.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class LocationResponse.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationResponse {

	/** The version. */
	@JsonProperty("Version")
	private int version;

	/** The key. */
	@JsonProperty("Key")
	private String key;

	/** The type. */
	@JsonProperty("Type")
	private String type;
}
