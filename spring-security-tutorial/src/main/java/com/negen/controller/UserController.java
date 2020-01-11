package com.negen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.negen.common.ResponseEnum;
import com.negen.common.ServerResponse;
import com.negen.entity.User;
import com.negen.service.IUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
@RestController
public class UserController {

	@Autowired
	IUserService userService;
	
	@GetMapping(value = "/user/login")
	public ServerResponse userLogin() {
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.ACCOUT_NOT_LOGIN);
	}
	
	@ApiOperation(value = "用户注册")
	@PostMapping(value = "/user/register")
	public ServerResponse userReg(
			@ApiParam(name = "user", value = "{\"userName\":\"test3\",\"password\":\"13212\"}", defaultValue = "{\"userName\":\"test3\",\"password\":\"13212\"}")
			@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@PreAuthorize("hasAuthority('query')")
	@GetMapping(value = "/user/list")
	public ServerResponse userList(){
		return userService.listAllUser();
	}
	
	@PreAuthorize("hasRole('admin')")
	@PostMapping(value = "/user/role/modify")
	public ServerResponse modifyRole(@RequestBody JSONObject obj) {
		Long userid = obj.getLongValue("userid");
		String roleName = obj.getString("roleName");
		if(null == userid || null == roleName
				|| 0 == userid || "".equals(roleName))
			return ServerResponse.getInstance()
					.responseEnum(ResponseEnum.INVALID_PARAM);
		return userService.modifyUserRoleAndPermission(userid, roleName);
	}
}
