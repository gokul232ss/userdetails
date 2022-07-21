package com.fse.userdetails;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@EnableSwagger2
@SpringBootApplication
public class UserdetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserdetailsApplication.class, args);
	}

	@Bean
	@Scope(scopeName = SCOPE_PROTOTYPE)
	public JwtBuilder jwtBuilder() {
		return new DefaultJwtBuilder();
	}

	@Bean
	@Scope(scopeName = SCOPE_PROTOTYPE)
	public JwtParser jwtParser() {
		return new DefaultJwtParser();
	}
}
