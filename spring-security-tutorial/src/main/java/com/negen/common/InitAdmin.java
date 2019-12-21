package com.negen.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.negen.entity.Role;
import com.negen.entity.User;
import com.negen.repository.UserRepository;
import com.negen.service.IUserService;

@Component
public class InitAdmin implements CommandLineRunner{
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		//初始化管理员账号
		User admin = new User();
		List<Role> roles = new ArrayList<Role>();
		admin.setUserName("admin");
		admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
		Role role = new Role();
		role.setRoleName(RoleEnum.ROLE_ADMIN.getKey());
		role.setPermissions(RoleEnum.ROLE_ADMIN.getValue());
		roles.add(role);
		admin.setRoles(roles);
		userRepository.save(admin);
		System.out.println("》》》初始化超级管理员完成《《《");
	}

}
