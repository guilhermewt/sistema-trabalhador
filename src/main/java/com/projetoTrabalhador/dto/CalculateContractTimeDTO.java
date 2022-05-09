package com.projetoTrabalhador.dto;

import java.io.Serializable;

import com.projetoTrabalhador.entities.Worker;

public class CalculateContractTimeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private String departmentName;
	private Double money;
	
	public CalculateContractTimeDTO() {
	}
	
	public CalculateContractTimeDTO(Worker workerObj, Double money) {
		this.id = workerObj.getId();
		this.name = workerObj.getName();
		this.departmentName = workerObj.getDepartment().getName();
		this.money = money;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
	
}
