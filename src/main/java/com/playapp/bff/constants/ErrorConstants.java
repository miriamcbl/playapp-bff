package com.playapp.bff.constants;

public class ErrorConstants {

	private ErrorConstants() {
		super();
	}

	/** The Constant DATE_ERROR. */
	public static final String DATE_ERROR = "La fecha no puede ser anterior al día de hoy ni posterior a 5 días";

	/** The Constant DAYS_ERROR. */
	public static final String DAYS_ERROR = "No se puede manejar total de días superior a 5";
}
