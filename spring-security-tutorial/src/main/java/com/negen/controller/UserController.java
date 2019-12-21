package com.negen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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
		return userService.addUser(user);
	}
	
	@PreAuthorize("hasAuthority('QUERY')")
	@RequestMapping(value = "/user/list")
	public ServerResponse userList(){
		return userService.listAllUser();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/user/role/modify")
	public ServerResponse modifyRole(@RequestBody JSONObject obj) {
		Long userid = obj.getLongValue("userid");
		String roleName = obj.getString("roleName");
		if(null == userid || null == roleName
				|| 0 == userid || "".equals(roleName))
			return ServerResponse.buildFail("参数非法");
		return userService.modifyUserRoleAndPermission(userid, roleName);
	}
}
