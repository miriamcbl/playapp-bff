package com.playapp.bff.constants;

/**
 * The Class Constants.
 */
public class Constants {

	private Constants() {
		super();
	}

	/** The Constant WIND_SUPERIOR_30. */
	public static final String WIND_SUPERIOR_30_MESSAGE = "Parece que hoy en Cádiz hace muchísimo viento. "
			+ "Mejor vete a tomar un mojito a tu chiringuito de confianza";

	/** The Constant CANNOT_CONNECT_API. */
	public static final String CANNOT_CONNECT_API_MESSAGE = "Parece que no podemos conectarnos con el servicio del "
			+ "tiempo actualmente. Vuelve a intentarlo más tarde.";

	/** The Constant ONE_DAY_PREDICTION. */
	public static final String ONE_DAY_PREDICTION = "1";

	/** The Constant FIVE_DAYS_PREDICTION. */
	public static final String FIVE_DAYS_PREDICTION = "5";

	/** The Constant ITS_RAINING. */
	public static final String ITS_RAINING_MESSAGE = "Vaya, parece que hoy va a llover. Ya sea que chispee o llueva "
			+ "muchísimo, es mejor que no vayas a la playa";

	/** The Constant LIMIT_THEMES_SYSTEM_PROMPT. */
	public static final String LIMIT_THEMES_SYSTEM_PROMPT = "Eres un asistente de inteligencia artificial muy útil "
			+ "y servicial que ayuda a las personas a elegir a qué playa ir en Cádiz, dándole las tres mejores opciones. "
			+ "Tu nombre es Chorli, y te llamas así en honor a una especie de aves que habita en estas playas. "
			+ "Utiliza los siguientes pasos para responder, pero responde sin separar pasos: "
			+ "PASO 1. Tienes que presentarte explicando brevemente el motivo por el que te llamas así y "
			+ "poniendo después de tu nombre un emoji de pájaro, e indicar que estás para ayudarle "
			+ "a elegir la mejor playa a la que ir. "
			+ "PASO 2. Tienes que responder a la pregunta que te hace el usuario. "
			+ "Ten en cuenta estas normas importantes: "
			+ "Si te preguntan por temas diferentes a las playas en Cádiz, no puedes bajo ningún concepto. "
			+ "Si te preguntan por otra ciudad, no puedes responder porque no tienes esa información, "
			+ "y si te preguntan por otros temas, tampoco puedes responder porque no estás entrenado para ello.";

	/** The Constant OUTPUT_SYSTEM_PROMPT. */
	public static final String OUTPUT_SYSTEM_PROMPT = "Tienes que ordenar la lista en base a los km/h del viento, "
			+ "de menor a mayor, y teniendo en cuenta también los km/h de las rafagas, de menor a mayor también. "
			+ "Tienes que devolver solo las tres mejores opciones, que serán las que tengan relación viento-rafaga "
			+ "más bajo. Además, tienes que usar un lenguaje bonito y agradable "
			+ "tendrás que esquematizarlo en orden, para que el usuario lo vea "
			+ "fácilmente, y finalizar con un mensaje divertido acerca de las playas de Cádiz y un emoji de playa. "
			+ "El listado de playas es el siguiente: ";

	/** The Constant GUSTS_MESSAGE. */
	public static final String GUSTS_MESSAGE = "Hoy hay unas ráfagas de viento muy fuertes. Mejor no vayas a la "
			+ "playa y disfruta de un mojito en tu chiringuito de confianza";
}
