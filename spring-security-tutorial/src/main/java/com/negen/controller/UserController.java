package com.negen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.negen.common.ServerResponse;
import com.negen.entity.User;
import com.negen.service.IUserService;

@RestController
public class UserController {

	@Autowired
	IUserService userService;
	
	@RequestMapping(value = "/user/login")
	public ServerResponse userLogin() {
		return ServerResponse.buildFail("账号未登录");
	}
	
	@RequestMapping(value = "/user/register")
	public ServerResponse userReg(@RequestBody User user) {
		userService.addUser(user);
		return ServerResponse.buildSuccess("注册成功");
	}
	
	
	@PreAuthorize("hasAuthority('create')")
	@RequestMapping(value = "/hello")
	public ServerResponse hello() {
		return ServerResponse.buildSuccess("ok", "hhahah");
	}
}
