package jp.co.axa.apidemo.utils;

import java.util.Optional;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.exceptions.ValidationException;
import jp.co.axa.apidemo.services.dto.EmployeeList;

/**
 * 
 * @author Rehan khan
 * Validate API Request Payloads
 *
 */
public class Validate {

	private Validate() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated"); 
	}
	
	public static Object checkPayload(Object value, String name) {
		return Optional.ofNullable(value)
		.orElseThrow(() -> new ValidationException(String.format("%s Cannot Be null", name)));
	}
	
	public static Long checkPayload(Long value, String name) {
		return Optional.ofNullable(value)
		.orElseThrow(() -> new ValidationException(String.format("%s Cannot Be null", name)));
	}
}
