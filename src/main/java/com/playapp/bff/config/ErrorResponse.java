package com.playapp.bff.config;

import java.time.LocalDateTime;

/**
 * The Class ErrorResponse.
 */
public class ErrorResponse extends BasicErrorResponse {


	/** The code status. */
	private int status;

	/** The code name error. */
	private String error;

	/**
	 * Instantiates a new error response.
	 *
	 * @param status            the status
	 * @param error             the error
	 * @param timestamp         the timestamp
	 * @param customizedMessage the customized message
	 */
	public ErrorResponse(LocalDateTime timestamp, int status, String error, String customizedMessage) {
		super(timestamp, customizedMessage);
		this.status = status;
		this.error = error;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the error.
	 *
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * Sets the error.
	 *
	 * @param error the new error
	 */
	public void setError(String error) {
		this.error = error;
	}

}
