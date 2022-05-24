package com.projetoTrabalhador.service.exceptions;

public class DataBaseError extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataBaseError(String msg) {
		super(msg);
	}
	
}
