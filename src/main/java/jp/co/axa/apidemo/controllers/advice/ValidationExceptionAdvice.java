package jp.co.axa.apidemo.controllers.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jp.co.axa.apidemo.exceptions.ValidationException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ValidationExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> exception(ValidationException ex) {
		return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
	}
	
}
