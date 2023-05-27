/**
 * 
 */
package jp.co.axa.apidemo.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Advise controller to handel all
 *
 */
@Getter
@Setter
public class APIResponseException extends RuntimeException {

	private final HttpStatus statusCode;
	private final String errorMessage;
	private static final long serialVersionUID = 6186404696623575924L;

	public APIResponseException(HttpStatus statusCode, String errorMessage) {
		this.statusCode = statusCode;
		this.errorMessage = errorMessage;
		
	}
}
