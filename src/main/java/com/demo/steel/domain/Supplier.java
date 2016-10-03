package com.demo.steel.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Supplier {

	@Id
	private int code;
	private String name;
	private String email;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="supplier",fetch = FetchType.EAGER)
	private Set<PartNoDetails> partNos;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="supplier",fetch = FetchType.LAZY)
	private Set<SteelMill> steelMills;
	@OneToMany(cascade=CascadeType.ALL, mappedBy="supplier",fetch = FetchType.LAZY)
	private Set<SteelHeatNo> steelHeatNos;
	
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
	
	public Set<SteelMill> getSteelMills() {
		return Collections.unmodifiableSet(steelMills);
	}
	public void setSteelMills(Collection<SteelMill> steelMills) {
		this.steelMills = new HashSet<>(steelMills);
	}
	public PartNoDetails getPartNoDetails(int partNoId){
		return getPartNos().stream().filter((part)-> part.getPartNo()==partNoId)
				.findAny().get();
	}
}
