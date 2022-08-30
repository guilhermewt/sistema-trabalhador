package com.projetoTrabalhador.util;

import com.projetoTrabalhador.entities.Department;

public class DepartmentCreator {
	
	public static Department createDepartmentToBeSaved() {
		return Department.builder().name("Design").build();
	}
	
	public static Department createValidDepartment() {
		return Department.builder().id(1l).name("Design").build();
	}
	
	public static Department createValidUpdateDepartment() {
		return Department.builder().name("Design 2").id(1l).build();
	}
}
