package com.projetoTrabalhador.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projetoTrabalhador.service.exceptions.BadRequestException;
import com.projetoTrabalhador.service.exceptions.BadRequestExceptionDetails;

@ControllerAdvice 
public class ResourceExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<BadRequestExceptionDetails> HandlerBadRequestException(BadRequestException bre){
		
		return new ResponseEntity<>(BadRequestExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("bad request exception, check the documentation")
				.details(bre.getMessage())
				.developerMessage(bre.getClass().getName())
				.build()
				, HttpStatus.BAD_REQUEST);
	}
	
}
