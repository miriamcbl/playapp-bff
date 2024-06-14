package com.playapp.bff.config;

import java.time.LocalDateTime;

public class ErrorResponse {
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String customizedMessage;

	public ErrorResponse(LocalDateTime timestamp, int status, String error, String customizedMessage) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.customizedMessage = customizedMessage;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCustomizedMessage() {
		return customizedMessage;
	}

	public void setCustomizedMessage(String customizedMessage) {
		this.customizedMessage = customizedMessage;
	}
}
