package jp.co.axa.apidemo.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class ResponseTest {

	@Test
	void nullItemsCaseTest() {
		Response resp = Response.newResponse(null);
		assertNotNull(resp);
		assertNotNull(resp.getMeta());
		assertNull(resp.getItems());
	}

}
