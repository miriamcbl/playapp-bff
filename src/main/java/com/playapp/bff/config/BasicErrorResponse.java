package com.playapp.bff.config;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class BasicErrorResponse.
 */
public class BasicErrorResponse {

	/** The timestamp. */
	private LocalDateTime timestamp;

	/** The customized message. */
	@JsonProperty("message")
	private String customizedMessage;

	/**
	 * Instantiates a new basic error response.
	 *
	 * @param timestamp         the timestamp
	 * @param customizedMessage the customized message
	 */
	public BasicErrorResponse(LocalDateTime timestamp, String customizedMessage) {
		this.timestamp = timestamp;
		this.customizedMessage = customizedMessage;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets the customized message.
	 *
	 * @return the customized message
	 */
	public String getCustomizedMessage() {
		return customizedMessage;
	}

	/**
	 * Sets the customized message.
	 *
	 * @param customizedMessage the new customized message
	 */
	public void setCustomizedMessage(String customizedMessage) {
		this.customizedMessage = customizedMessage;
	}
}
