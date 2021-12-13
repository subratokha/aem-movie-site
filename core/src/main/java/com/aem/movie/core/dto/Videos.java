package com.aem.movie.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Videos{

	@JsonProperty("results")
	private List<ResultsItem> results;

	public void setResults(List<ResultsItem> results){
		this.results = results;
	}

	public List<ResultsItem> getResults(){
		return results;
	}
}