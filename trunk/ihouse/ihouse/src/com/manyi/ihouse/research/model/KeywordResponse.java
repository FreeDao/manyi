package com.manyi.ihouse.research.model;

import java.util.List;

import com.manyi.ihouse.base.Response;

public class KeywordResponse extends Response{
	
	private List<String> keywords;
	
	public List<String> supportCity;

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public List<String> getSupportCity() {
		return supportCity;
	}

	public void setSupportCity(List<String> supportCity) {
		this.supportCity = supportCity;
	}
}
