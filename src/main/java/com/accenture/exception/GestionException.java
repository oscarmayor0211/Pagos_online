package com.accenture.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
public class GestionException {
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> manejaError(Exception e) {
		//se devuelve una respuesta con el código de error BAD_REQUEST
		//indicando en el cuerpo el mensaje de error
	    return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
