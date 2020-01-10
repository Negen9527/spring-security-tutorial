package com.negen;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.negen.common.RoleEnum;
import com.negen.entity.Role;
import com.negen.entity.User;
import com.negen.service.IUserService;
import com.negen.service.impl.UserService;

@EnableWebSecurity
@SpringBootApplication
public class SpringSecurityTutorialApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityTutorialApplication.class, args);
	}
}
