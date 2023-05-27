package jp.co.axa.apidemo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

	public List<RequestParameter> customRequestHeaders() {

		RequestParameter params = new RequestParameterBuilder().in(ParameterType.HEADER)
				.name("PASSWORD").required(true)
				.query(param -> param.model(model -> model.scalarModel(ScalarType.STRING))).build();
		return Arrays.asList(params);

	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2).globalRequestParameters(customRequestHeaders())
				.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

}
