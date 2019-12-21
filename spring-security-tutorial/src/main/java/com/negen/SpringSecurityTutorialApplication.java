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
	
	


//	public void init() {
//		//初始化管理员账号
//		UserService userService = new UserService();
//		User admin = new User();
//		List<Role> roles = new ArrayList<Role>();
//		admin.setUserName("admin");
//		admin.setPassword("123456");
//		Role role = new Role();
//		role.setRoleName(RoleEnum.ROLE_ADMIN.getKey());
//		role.setPermissions(RoleEnum.ROLE_ADMIN.getValue());
//		roles.add(role);
//		admin.setRoles(roles);
//		userService.addUser(admin);
//	}
	
}
