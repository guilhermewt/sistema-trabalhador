package com.projetoTrabalhador.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentPutRequestBody {
	private Long id;
	private String name;
}
