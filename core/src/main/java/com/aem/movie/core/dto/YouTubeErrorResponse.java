package com.aem.movie.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class YouTubeErrorResponse{

	@JsonProperty("error")
	private Error error;

	public void setError(Error error){
		this.error = error;
	}

	public Error getError(){
		return error;
	}
}