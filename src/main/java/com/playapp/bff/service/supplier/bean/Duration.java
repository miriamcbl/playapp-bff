package com.playapp.bff.service.supplier.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Duration.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Duration {

	/** The text. */
	private String text;

	/** The value. */
	private int value;
}
