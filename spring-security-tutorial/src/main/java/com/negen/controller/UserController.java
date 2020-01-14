package com.negen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
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
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;
import springfox.documentation.annotations.ApiIgnore;
@Api("用户模块")
@RestController
@RequestMapping("user")
public class UserController {

	@Autowired
	IUserService userService;
	
	@ApiIgnore
	@GetMapping(value = "/login")
	public ServerResponse userLogin() {
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.ACCOUT_NOT_LOGIN);
	}
	
	
	@PostMapping(value = "/logout")
	public ServerResponse userLogout() {
		return ServerResponse.getInstance()
				.code(20000).data("success");
	}
	
	@ApiOperation(value = "用户注册")
	@PostMapping(value = "/register")
	public ServerResponse userReg(
			@ApiParam(name = "user", value = "{\"userName\":\"test3\",\"password\":\"13212\"}", defaultValue = "{\"userName\":\"test3\",\"password\":\"13212\"}")
			@RequestBody User user) {
		return userService.addUser(user);
	}
	
	@ApiOperation(value = "获取用户信息")
	@GetMapping("/info")
	public ServerResponse getUserInfo() {
		return userService.getUserInfo("");
	}
	
	
	@ApiOperation(value = "获取用户列表")
	@PreAuthorize("hasAuthority('query')")
	@GetMapping(value = "/list")
	public ServerResponse userList(){
		return userService.listAllUser();
	}
	
	@ApiOperation(value = "获取用户列表（分页）")
	@PreAuthorize("hasAuthority('query')")
	@GetMapping(value = "/list/{page}/{size}")
	public ServerResponse pageableUserList(
			@PathVariable("page")int page, 
			@PathVariable("size")int size){
		return userService.pageableListUser(page, size);
	}
	
	@ApiOperation(value = "修改用户角色")
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping(value = "/role/modify")
	public ServerResponse modifyRole(@RequestBody JSONObject obj) {
		Long userid = obj.getLongValue("userid");
		String roleName = obj.getString("roleName");
		if(null == userid || null == roleName
				|| 0 == userid || "".equals(roleName))
			return ServerResponse.getInstance()
					.responseEnum(ResponseEnum.INVALID_PARAM);
		return userService.modifyUserRole(userid, roleName);
	}
	
	@ApiOperation(value = "修改用户权限")
	@PreAuthorize("hasAuthority('admin')")
	@PostMapping(value = "/permission/modify")
	public ServerResponse modifyPermission(@RequestBody JSONObject obj) {
		Long userid = obj.getLongValue("userid");
		JSONArray permissionNames = obj.getJSONArray("permissionNames");
		if(null == userid || null == permissionNames
				|| 0 == userid || permissionNames.size() <= 0)
			return ServerResponse.getInstance()
					.responseEnum(ResponseEnum.INVALID_PARAM);
		return userService.modifyUserPermission(userid, permissionNames);
	}
}
