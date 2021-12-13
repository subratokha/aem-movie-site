package com.aem.movie.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Thumbnails{

	@JsonProperty("default")
	private DefaultResolution defaultResolution;

	@JsonProperty("high")
	private High high;

	@JsonProperty("medium")
	private Medium medium;

	public void setJsonMemberDefault(DefaultResolution defaultResolution){
		this.defaultResolution = defaultResolution;
	}

	public DefaultResolution getJsonMemberDefault(){
		return defaultResolution;
	}

	public void setHigh(High high){
		this.high = high;
	}

	public High getHigh(){
		return high;
	}

	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}
}