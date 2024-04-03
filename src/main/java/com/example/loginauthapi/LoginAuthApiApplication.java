package com.example.loginauthapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "login-auth-api", version = "1", description = "API focused on user authentication"))
public class LoginAuthApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginAuthApiApplication.class, args);
	}

}
