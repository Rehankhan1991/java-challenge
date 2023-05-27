/**
 * 
 */
package jp.co.axa.apidemo.controllers.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.co.axa.apidemo.exceptions.APIResponseException;

/**
 * @author prreh
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class CatchAllExceptionsAdvice {

	@ExceptionHandler(APIResponseException.class)
	public ResponseEntity<?> apiException(APIResponseException ex) {
		return new ResponseEntity<>(ex, ex.getStatusCode());
	}
	
}
