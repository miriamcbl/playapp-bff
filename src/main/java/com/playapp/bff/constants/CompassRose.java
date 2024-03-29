package com.playapp.bff.constants;

public enum CompassRose {
	
	NORTE("N"),	
	ESTE("E"),
	ESTESURESTE("ESE"),
	SUERESTE("SE"),
	SURSURESTE("SSE"),
	OESTE("O"),
	SURSUROESTE("SSO"),
	SUROESTE("SO"),
	OESTESUROESTE("OSO"),
	SUR("S");
	
	private final String shortName;

	public String getShortName() {
		return shortName;
	}

	private CompassRose(String shortName) {
		this.shortName = shortName;
	}

}
