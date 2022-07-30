package com.projetoTrabalhador.resources.exceptions;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.WebUtils;

import com.projetoTrabalhador.service.exceptions.BadRequestException;
import com.projetoTrabalhador.service.exceptions.BadRequestExceptionDetails;
import com.projetoTrabalhador.service.exceptions.ExceptionDetails;
import com.projetoTrabalhador.service.exceptions.ValidationExceptionDetails;

@ControllerAdvice 
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler{

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
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {	
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
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

		ExceptionDetails exceptionDetails = ExceptionDetails.builder()
		.timestamp(Instant.now())
		.status(status.value())
		.title(ex.getCause().getMessage())
		.details(ex.getMessage())
		.developerMessage(ex.getClass().getName())
		.build();
		
		return new ResponseEntity<>(exceptionDetails, headers, status);
	}
	
}
