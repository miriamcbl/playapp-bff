package com.playapp.bff.constants;

public class ErrorConstants {

	private ErrorConstants() {
		super();
	}

	/** The Constant DATE_ERROR. */
	public static final String DATE_ERROR = "La fecha no puede ser anterior al día de hoy ni posterior a 5 días";

	/** The Constant DATE_ERROR_PROMPT. */
	public static final String DATE_ERROR_PROMPT = "La fecha no es correcta. La fecha no puede ser anterior al día "
			+ "de hoy ni posterior a 5 días. Además tiene que tener el formato correcto: dd/mm/aaaa.";

	/** The Constant DAYS_ERROR. */
	public static final String DAYS_ERROR = "No se puede manejar total de días superior a 5";

	/** The Constant LOCATIONS_ERROR. */
	public static final String LOCATIONS_ERROR = "Error al obtener las localizaciones";

	/** The Constant WEATHER_DETAILS_ERROR. */
	public static final String WEATHER_DETAILS_ERROR = "Error al obtener los detalles del clima según localizaciones";

	/** The Constant PROMPTS_ERROR. */
	public static final String PROMPTS_ERROR = "Error al llamar a chat usando Prompts";

	/** The Constant BASIC_CHAT_ERROR. */
	public static final String BASIC_CHAT_ERROR = "Error al llamar a chat básico";

	/** The Constant PROMPTS_OPTIONS_ERROR. */
	public static final String PROMPTS_OPTIONS_ERROR = "Error al llamar a chat usando Prompts y Options";

	/** The Constant PROMPTS_OPTIONS_SYSTEM_ERROR. */
	public static final String PROMPTS_OPTIONS_SYSTEM_ERROR = "Error al llamar a chat usando Prompts y Options";
}
