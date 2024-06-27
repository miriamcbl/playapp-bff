package com.playapp.bff.constants;

public enum CadizTownsGeographicCoordinates {

	JEREZ("36.684587", "-6.128378"),
	CADIZ("36.514781", "-6.281899"),
	SAN_FERNANDO("36.464694", "-6.201576"), 
	EL_PUERTO_DE_SANTA_MARIA("36.595787", "-6.232853"),
	CHICLANA("36.415123", "-6.148173"), 
	PUERTO_REAL("36.530401", "-6.184897"), 
	VEJER("36.250242", "-5.969654");

	/** The latitude. */
	private final String latitude;

	/** The longitude. */
	private final String longitude;

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
	 * Instantiates a new geographic coordinates.
	 *
	 * @param latitude                    the latitude
	 * @param longitude                   the longitude
	 */
	private CadizTownsGeographicCoordinates(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
