package com.playapp.bff.constants;

/**
 * The Enum GeographicCoordinates.
 */
public enum GeographicCoordinates {
	
	PLAYA_LA_CALETA("36.530508", "-6.305878", true, false),
	PLAYA_SANTA_MARIA_DEL_MAR("36.520222", "-6.287010", true, false),
	PLAYA_DE_LA_VICTORIA("36.508228", "-6.281775", false, false),
	PLAYA_DE_CAMPOSOTO("36.427066", "-6.230759", false, false),
	PLAYA_DE_SANCTI_PETRI("36.388526", "-6.207810", false, false),
	PLAYA_DE_LA_BARROSA("36.372852", "-6.187338", false, false),
	PLAYA_DE_NOVO_SANCTI_PETRI("36.345789", "-6.167670", false, false),
	PLAYA_DE_ROCHE("36.314856", "-6.154535", false, false),
	CALA_ROCHE("36.307785", "-6.151288", true, false), 	
	PLAYA_CONIL("36.275956", "-6.096237", false, false),
	PLAYA_EL_PALMAR("36.231870", "-6.071992", false, false),
	PLAYA_ZAHORA("36.195913", "-6.044221", false, true),
	PLAYA_CANOS_DE_MECA("36.186646", "-6.025211", false, true),
	PLAYA_DE_BARBATE("36.185319", "-5.918190", false, false),
	PLAYA_DE_ZAHARA("36.125930", "-5.840953", false, true),
	PLAYA_DE_BOLONIA("36.088739", "-5.779860", false, true),
	PLAYA_TARIFA("36.039124", "-5.633692", false, true),
	PLAYA_DE_GETARES("36.095316", "-5.443775", false, false),
	PLAYA_PUERTO_SANTA_MARIA("36.597386", "-6.275518", false, false),
	PLAYA_ROTA("36.619368", "-6.364294", false, false),
	PLAYA_COSTA_BALLENA("36.703239", "-6.429555", false, false),
	PLAYA_CHIPIONA("36.734864", "-6.441236", false, false),
	PLAYA_SANLUCAR_BARRAMEDA("36.774598", "-6.377041", false, false);

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
