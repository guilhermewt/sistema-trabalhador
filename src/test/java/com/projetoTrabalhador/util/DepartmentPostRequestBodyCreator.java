package com.projetoTrabalhador.util;

import com.projetoTrabalhador.requests.DepartmentPostRequestBody;

public class DepartmentPostRequestBodyCreator {
	
	public static DepartmentPostRequestBody createDepartmentPostRequestBody() {
		return DepartmentPostRequestBody.builder()
				.name(DepartmentCreator.createDepartmentToBeSaved().getName())
				.build();
	}
}
