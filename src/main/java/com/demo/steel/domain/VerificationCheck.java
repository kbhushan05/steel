package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name="verificationcheck")
public class VerificationCheck {
	
	public enum Type{
		BASIC, FHT;
	}
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private String testName;
	@Enumerated(EnumType.STRING)
	private Type type;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
