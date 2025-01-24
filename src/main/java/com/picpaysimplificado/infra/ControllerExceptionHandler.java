package com.picpaysimplificado.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.picpaysimplificado.TransactionDTO.ExceptionDTO;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> threatDuplicateEntry(DataIntegrityViolationException exception) {
		String statusCode = null;
		ExceptionDTO exceptionDTO = new ExceptionDTO("Usuário já cadastrado", statusCode);
		return (ResponseEntity<?>) ResponseEntity.badRequest().body(exceptionDTO);
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> threat404(EntityNotFoundException exception) {
		return (ResponseEntity<?>) ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> threatGeneralException(Exception exception) {
		String statusCode = null;
		ExceptionDTO exceptionDTO = new ExceptionDTO(exception.getMessage(), statusCode);
		return ResponseEntity.internalServerError().body(exceptionDTO);
	}
}
