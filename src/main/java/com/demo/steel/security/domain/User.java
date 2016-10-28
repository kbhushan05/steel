package com.demo.steel.security.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="user")
public class User {
	
	@Id
	private String username;
	private String password;
	private String role;
	private String supplierName;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=*****" + ", role=" + role + ", supplierName=" + supplierName + "]";
	}
	
}
