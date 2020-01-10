package com.negen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class SpringSecurityTutorialApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityTutorialApplication.class, args);
	}
}
