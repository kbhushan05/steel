package com.demo.steel.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SteelMill {
	
	
	private int id;
	@Id
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
