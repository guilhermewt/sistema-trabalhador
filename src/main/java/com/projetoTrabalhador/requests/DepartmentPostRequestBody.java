package com.projetoTrabalhador.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class DepartmentPostRequestBody {
	@NotEmpty
	private String name;
}
