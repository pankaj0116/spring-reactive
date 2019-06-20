package com.example.demo.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="employee")
public class Employee {
	
	@Id
	private String id;
	
	private String name;
	
	private String country;
	
	private String highestScore;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHighestScore() {
		return highestScore;
	}

	public void setHighestScore(String highestScore) {
		this.highestScore = highestScore;
	}
	
	
}
