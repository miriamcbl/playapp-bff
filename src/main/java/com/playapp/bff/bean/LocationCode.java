package com.playapp.bff.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class LocationCode.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationCode {

	/** The name. */
	private String name;

	/** The code. */
	private String code;

}
