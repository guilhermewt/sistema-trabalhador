package com.projetoTrabalhador.resources.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.projetoTrabalhador.service.exceptions.BadRequestException;
import com.projetoTrabalhador.service.exceptions.BadRequestExceptionDetails;
import com.projetoTrabalhador.service.exceptions.ValidationExceptionDetails;

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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationExceptionDetails> HandlerMethodArgumentNotValidException(MethodArgumentNotValidException exception){
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		String field = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
		String fieldMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
		
		return new ResponseEntity<>(ValidationExceptionDetails.builder()
				.timestamp(Instant.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("bad request exception, invalid fields")
				.details(exception.getMessage())
				.developerMessage(exception.getClass().getName())
				.field(field)
				.fieldMessage(fieldMessage)
				.build()
				, HttpStatus.BAD_REQUEST);
	}
	
}
