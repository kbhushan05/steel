package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="steelmill")
public class SteelMill {
	
	@Id
	private String name;
	private int id;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
