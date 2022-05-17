package com.projetoTrabalhador.service.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(long id) {
		super("resource not found:" + id);
	}
}
