package br.com.jogos.api.exception;

import java.io.Serializable;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler({EmptyResultDataAccessException.class}) //exceçao para realocar o erro 500 para 404
	public ResponseEntity errorNotFoundy(Exception ex) {
		return ResponseEntity.notFound().build();
	}
	
	@ExceptionHandler({IllegalArgumentException.class}) //exceçao para realocar o erro 500 para 404
	public ResponseEntity errorBadRequest(Exception ex) {
		return ResponseEntity.badRequest().build();
	}
	
	@ExceptionHandler({AccessDeniedException.class}) //exceçao para realocar o erro 500 para 404
	public ResponseEntity errorDanied() {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Error("Acesso NEGADO"));
	}
	
	@Override //sobrescrevendo  ResponseEntity para que quando der o erro handleHttpRequestMethodNotSupported - apresente uma menssagem ao invés do json
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported (HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
		return new ResponseEntity<>(new ExceptionError("Operação não permitida"), HttpStatus.METHOD_NOT_ALLOWED);
	}
		
}

class ExceptionError implements Serializable{
	private String error;
	ExceptionError(String error){
		this.error = error;
	}
	public String getError() {
		return error;
	}
	
	
}
