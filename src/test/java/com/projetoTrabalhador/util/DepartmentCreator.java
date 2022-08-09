package com.projetoTrabalhador.util;

import com.projetoTrabalhador.entities.Department;

public class DepartmentCreator {
	
	public static Department createDepartmentToBeSaved() {
		return Department.builder().name("informatica").build();
	}
	
	public static Department createValidDepartment() {
		return Department.builder().name("informatica").id(1l).build();
	}
	
	public static Department createValidUpdateDepartment() {
		return Department.builder().name("informatica 2").id(1l).build();
	}
}
