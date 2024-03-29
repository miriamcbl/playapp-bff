package com.playapp.bff.constants;

public enum LevanteWind {
	
	ESTE("E"),
	ESTESURESTE("ESE");
	
	private final String shortName;

	public String getShortName() {
		return shortName;
	}

	private LevanteWind(String shortName) {
		this.shortName = shortName;
	}

}
