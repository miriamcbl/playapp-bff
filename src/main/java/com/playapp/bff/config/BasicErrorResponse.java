package com.playapp.bff.config;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The Class BasicErrorResponse.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BasicErrorResponse {

	/** The timestamp. */
	private LocalDateTime timestamp;

	/** The customized message. */
	@JsonProperty("message")
	private String customizedMessage;

}
