package com.projetoTrabalhador.requests;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentPostRequestBody {
	@NotEmpty(message = "the department name cannot be empty")
	private String name;
}
