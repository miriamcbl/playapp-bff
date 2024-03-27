package com.playapp.bff.constants;

public enum GeographicCoordinates {

	CALA_ROCHE("36.307785", "-6.151288"), 
	CALA_ACEITE("36.296917", "-6.132040");


	private final String latitude;
	private final String longitude;

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	private GeographicCoordinates(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

}
