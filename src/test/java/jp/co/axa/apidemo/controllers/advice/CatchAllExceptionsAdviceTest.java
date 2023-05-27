package jp.co.axa.apidemo.controllers.advice;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jp.co.axa.apidemo.exceptions.APIResponseException;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class CatchAllExceptionsAdviceTest {

	@Test
	void apiExceptionTest() {
		CatchAllExceptionsAdvice ex = new CatchAllExceptionsAdvice();
		
		APIResponseException exep = new APIResponseException(HttpStatus.CONFLICT, "Record not found due to conflicts in request");
		assertNotNull(ex.apiException(exep));
	}

}
