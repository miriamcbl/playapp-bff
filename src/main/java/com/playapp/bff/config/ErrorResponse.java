package com.playapp.bff.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * The Class ErrorResponse.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Builder
public class ErrorResponse extends BasicErrorResponse {


	/** The code status. */
	private int status;

	/** The code name error. */
	private String error;

}
