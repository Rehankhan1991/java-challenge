package jp.co.axa.apidemo.controllers.advice;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.axa.apidemo.exceptions.ValidationException;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ValidationExceptionAdviceTest {

	@Test
	void apiExceptionTest() {
		ValidationExceptionAdvice ex = new ValidationExceptionAdvice();
		
		ValidationException exep = new ValidationException("RequestParam is missing");
		assertNotNull(ex.exception(exep));
	}

}
