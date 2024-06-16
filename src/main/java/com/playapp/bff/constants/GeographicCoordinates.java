package com.playapp.bff.constants;

/**
 * The Enum GeographicCoordinates.
 */
public enum GeographicCoordinates {
	
	PLAYA_LA_CALETA("36.530508", "-6.305878", true, false),
	// PLAYA_SANTA_MARIA_DEL_MAR("36.520222", "-6.287010", true),
	PLAYA_DE_LA_VICTORIA("36.508228", "-6.281775", false, false),
	// PLAYA_DE_LA_CORTADURA("36.491299", "-6.270577", false),
	// PLAYA_DEL_CHATO("36.476424", "-6.261524", false),
	// PLAYA_DE_SANTIBANEZ("36.472013", "-6.259240", false),
	// PLAYA_DE_TORREGORDA("36.461742", "-6.254052", false),
	// PLAYA_URRUTIA("36.442793", "-6.240329", false),
	PLAYA_DE_CAMPOSOTO("36.427066", "-6.230759", false, false),
	// PLAYA_DEL_CASTILLO("36.390211", "-6.213424", false),
	// PLAYA_DE_SANCTI_PETRI("36.388526", "-6.207810", false),
	PLAYA_DE_LA_BARROSA("36.372852", "-6.187338", false, false),
	// PLAYA_DE_NOVO_SANCTI_PETRI("36.345789", "-6.167670", false),
	// PLAYA_DEL_PUERCO("36.321835", "-6.157602", false),
	// PLAYA_DE_ROCHE("36.314856", "-6.154535", false),
	CALA_ROCHE("36.307785", "-6.151288", true, false), 
	CALA_EL_FRAILECILLO("36.305420", "-6.149550", true, false),
	// CALA_EL_ENEBRO("36.304261", "-6.148713", true),
	// CALA_DEL_PATO("36.302852", "-6.147571", true),
	// CALA_DEL_JUAN_TIO_DE_MEDINA("36.299022", "-6.144411", true),
	// CALA_DEL_FARO("36.297094", "-6.143080", true),
	// CALA_DEL_ACEITE("36.296640", "-6.132352", true),
	// CALA_DEL_PUNTALEJO("36.296108", "-6.127186", true),
	// PLAYA_FUENTE_DEL_GALLO("36.292123", "-6.111808", false),
	PLAYA_LA_FONTANILA("36.288276", "-6.107432", false, false),
	// PLAYA_DE_LOS_BATELES("36.278466", "-6.098354", false),
	// PLAYA_PUNTALEJOS("36.264096", "-6.088808", false),
	// PLAYA_CASTILNOVO("36.260290", "-6.086767", false),
	PLAYA_EL_PALMAR("36.231870", "-6.071992", false, false),
	// PLAYA_DE_LAS_CALDERAS("36.217574", "-6.062390", false),
	// PLAYA_MANGUETA("36.202667", "-6.052310", false),
	PLAYA_ZAHORA("36.195913", "-6.044221", false, true),
	// PLAYA_FARO_DEL_TRAFALGAR("36.186625", "-6.036898", false),
	PLAYA_CANOS_DE_MECA("36.186646", "-6.025211", false, true),
	// PLAYA_EL_PIRATA("36.185204", "-6.009849", false),
	// PLAYA_EL_CASTILLEJO("36.181744", "-5.999412", false),
	// PLAYA_DE_LA_CORTINA("36.179334", "-5.990481", false),
	// PLAYA_DE_LA_HIERBABUENA("36.185031", "-5.939446", false),
	// PLAYA_DE_BARBATE("36.185319", "-5.918190", false),
	// PLAYA_DEL_CABO_DE_PLATA("36.130025", "-5.844335", false),
	PLAYA_DE_ATLANTERRA("36.106583", "-5.826657", false, true),
	// PLAYA_DE_LOS_ALEMANES("36.096183", "-5.814845", false),
	PLAYA_DE_BOLONIA("36.088739", "-5.779860", false, true),
	// PLAYA_PUNTA_PALOMA("36.062773", "-5.725176", false),
	// PLAYA_VALDEVAQUEROS("36.068432", "-5.690948", false),
	// PLAYA_TARIFA("36.039124", "-5.633692", false),
	PLAYA_DE_GETARES("36.095316", "-5.443775", false, false);

	/** The latitude. */
	private final String latitude;

	/** The longitude. */
	private final String longitude;

	/** The suitable for levante. */
	private final Boolean suitableForLevante;

	/** The special condition for wind gust. */
	private final Boolean specialConditionForWindGust;


	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Gets the suitable for levante.
	 *
	 * @return the suitable for levante
	 */
	public Boolean getSuitableForLevante() {
		return suitableForLevante;
	}

	/**
	 * Gets the special condition for wind gust.
	 *
	 * @return the special condition for wind gust
	 */
	public Boolean getSpecialConditionForWindGust() {
		return specialConditionForWindGust;
	}

	/**
	 * Instantiates a new geographic coordinates.
	 *
	 * @param latitude                    the latitude
	 * @param longitude                   the longitude
	 * @param suitableForLevante          the suitable for levante
	 * @param specialConditionForWindGust the special condition for wind gust
	 */
	private GeographicCoordinates(String latitude, String longitude, Boolean suitableForLevante,
			Boolean specialConditionForWindGust) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.suitableForLevante = suitableForLevante;
		this.specialConditionForWindGust = specialConditionForWindGust;
	}

}
