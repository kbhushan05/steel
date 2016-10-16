package com.demo.steel.domain;

import java.util.Collections;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Supplier {

	@Id
	private Integer code;
	private String name;
	private String email;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="supplier",fetch = FetchType.EAGER)
	private Set<PartNoDetails> partNos;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<PartNoDetails> getPartNos() {
		return Collections.unmodifiableSet(partNos);
	}
	public void setPartNos(Set<PartNoDetails> partNos) {
		this.partNos = partNos;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
