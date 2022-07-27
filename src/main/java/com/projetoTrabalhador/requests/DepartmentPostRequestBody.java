package com.projetoTrabalhador.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class DepartmentPostRequestBody {
	@NotEmpty(message = "the department name cannot be empty")
	private String name;
}
