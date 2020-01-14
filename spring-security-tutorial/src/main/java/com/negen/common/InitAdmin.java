package com.negen.common;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.negen.common.Constant.RoleConstant;
import com.negen.entity.Role;
import com.negen.entity.User;
import com.negen.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component

public class InitAdmin implements CommandLineRunner{
	private static final Logger log = LoggerFactory.getLogger(InitAdmin.class);
	@Autowired
	UserRepository userRepository;
	
	@Override
	public void run(String... args) throws Exception {
		//初始化管理员账号
		User admin = new User();
		List<Role> roles = new ArrayList<Role>();
		admin.setUserName("admin");
		admin.setPassword(new BCryptPasswordEncoder().encode("123456"));
		Role role = Constant.generateRole(RoleConstant.ADMIN);
		roles.add(role);
		admin.setRoles(roles);
		userRepository.save(admin);
		log.info("\n》》》初始化超级管理员完成《《《");
	}
}
