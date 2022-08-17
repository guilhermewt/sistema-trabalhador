package com.projetoTrabalhador.util;

import com.projetoTrabalhador.requests.DepartmentPutRequestBody;

public class DepartmentPutRequestBodyCreator {
	public static DepartmentPutRequestBody createDepartmentPutRequestBody() {
		return DepartmentPutRequestBody.builder()
				.id(DepartmentCreator.createValidUpdateDepartment().getId())
				.name(DepartmentCreator.createDepartmentToBeSaved().getName())
				.build();
	}
}
