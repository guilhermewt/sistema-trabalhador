package com.projetoTrabalhador.requests;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentPostRequestBody {
	@NotEmpty(message = "the department name cannot be empty")
	private String name;
	@NotEmpty(message = "the department url cannot be empty")
	private String url;
}
