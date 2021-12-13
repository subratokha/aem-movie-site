package com.aem.movie.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorsItem{

	@JsonProperty("reason")
	private String reason;

	@JsonProperty("domain")
	private String domain;

	@JsonProperty("message")
	private String message;

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return reason;
	}

	public void setDomain(String domain){
		this.domain = domain;
	}

	public String getDomain(){
		return domain;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}
}