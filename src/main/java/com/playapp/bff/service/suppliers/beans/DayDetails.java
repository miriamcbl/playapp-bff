package com.playapp.bff.service.suppliers.beans;


public class DayDetails {

	private String LongPhrase;

	public String getLongPhrase() {
		return LongPhrase;
	}

	public void setLongPhrase(String longPhrase) {
		LongPhrase = longPhrase;
	}

	public DayDetails(String longPhrase) {
		super();
		LongPhrase = longPhrase;
	}

	public DayDetails() {
		super();
	}
}
