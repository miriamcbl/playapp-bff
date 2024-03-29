package com.playapp.bff.constants;

/**
 * The Enum GeographicCoordinates.
 */
public enum GeographicCoordinates {

	/** The cala roche. */
	CALA_ROCHE("36.307785", "-6.151288", true, false), 

	/** The cala aceite. */
	CALA_ACEITE("36.296917", "-6.132040", false, true);

	/** The latitude. */
	private final String latitude;

	/** The longitude. */
	private final String longitude;

	/** The suitable for levante. */
	private final Boolean suitableForLevante;

	/** The suitable for poniente. */
	private final Boolean suitableForPoniente;

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
	 * Gets the suitable for poniente.
	 *
	 * @return the suitable for poniente
	 */
	public Boolean getSuitableForPoniente() {
		return suitableForPoniente;
	}

	/**
	 * Instantiates a new geographic coordinates.
	 *
	 * @param latitude            the latitude
	 * @param longitude           the longitude
	 * @param suitableForLevante  the suitable for levante
	 * @param suitableForPoniente the suitable for poniente
	 */
	private GeographicCoordinates(String latitude, String longitude, Boolean suitableForLevante,
			Boolean suitableForPoniente) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.suitableForLevante = suitableForLevante;
		this.suitableForPoniente = suitableForPoniente;
	}

}
