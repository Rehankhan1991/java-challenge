package jp.co.axa.apidemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import lombok.Generated;

//@EnableSwagger2
@SpringBootApplication
@EnableCaching
public class ApiDemoApplication {

	@Generated
	public static void main(String[] args) {
		SpringApplication.run(ApiDemoApplication.class, args);
	}

}
