package br.com.jogos.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException{

		public ObjectNotFoundException(String message) {
			super(message);
		}
		public ObjectNotFoundException(String message, Throwable cause) {
			super(message, cause);
		}
}
