package com.aem.movie.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TMDBErrorResponse{

	@JsonProperty("status_message")
	private String statusMessage;

	@JsonProperty("status_code")
	private int statusCode;

	public String getStatusMessage(){
		return statusMessage;
	}

	public int getStatusCode(){
		return statusCode;
	}
}