package com.projetoTrabalhador.service.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
	
	private static final long serialVersionUID = 1L;
	
	private final String field;
	private final String fieldMessage;
}
