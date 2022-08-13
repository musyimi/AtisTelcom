package com.atis.common.entity;

public class CountyDTO {

	private Integer id;
	private String name;
	
	
	public CountyDTO() {
		
	}
	
	
	public CountyDTO(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
