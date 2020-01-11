package com.negen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.negen.common.Constant;
import com.negen.common.Constant.RoleConstant;
import com.negen.common.ResponseEnum;
import com.negen.common.ServerResponse;
import com.negen.entity.Permission;
import com.negen.entity.Role;
import com.negen.entity.User;
import com.negen.repository.PermissionRepository;
import com.negen.repository.RoleRepository;
import com.negen.repository.UserRepository;
import com.negen.service.IUserService;
@Service
@Transactional
public class UserService implements IUserService{
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PermissionRepository permissionRepository;
	
	@Override
	public ServerResponse addUser(User user) {
		//验证用户名是否存在
		User existUser = userRepository.findByUserName(user.getUserName());
		if(null != existUser) {
			//用户名已存在
			return ServerResponse.getInstance()
					.responseEnum(ResponseEnum.USERNAME_EXSIT);
		}
		//初始化角色为 user
		List<Permission> permissions = new ArrayList<Permission>();
		List<Role> roles = new ArrayList<Role>();
		Role role = Constant.generateRole(RoleConstant.USER);
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(user);
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.REGISTE_SUCCESS);
	}

	@Override
	public ServerResponse listAllUser() {
		JSONArray jsonUserArray = new JSONArray();
		List<User> users = userRepository.findAll();
		for (User user : users) {
			JSONObject jsonUser = new JSONObject();
			jsonUser.put("userid", user.getId());
			jsonUser.put("username", user.getUserName());
			jsonUser.put("roleid", user.getRoles().get(0).getId());
			jsonUser.put("role", user.getRoles().get(0).getRoleName());
			jsonUserArray.add(jsonUser);
		}
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.GET_SUCCESS)
				.data(jsonUserArray);
	}

	@Transactional
	@Override
	public ServerResponse modifyUserRole(long userid, String roleName) {
		User user  = userRepository.findById(userid).get();
		if(roleName.equals(user.getRoles().get(0).getRoleName())) {
			return ServerResponse.getInstance()
					.responseEnum(ResponseEnum.UPDATE_SUCCESS);
		}
		for (Role role : user.getRoles()) {
			for (Permission permission : role.getPermissions()) {
				permissionRepository.delete(permission);
			}
			roleRepository.delete(role);
		}
		List<Role> roles = new ArrayList<Role>();
		Role role = Constant.generateRole(roleName);
		roles.add(role);
		user.setRoles(roles);
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.UPDATE_SUCCESS);
	}
	
	@Override
	public ServerResponse modifyUserPermission(long userid, JSONArray permissionNames) {
		List<Permission> permissions = null;
		User user = userRepository.findById(userid).get();
		if(null != user) {
			Role role = user.getRoles().get(0);
			permissionRepository.deleteAll(role.getPermissions());
			if(permissionNames.size() > 0) {
				permissions = new ArrayList<Permission>();
				for (int i = 0; i < permissionNames.size(); i++) {
					Permission permission = new Permission(permissionNames.get(i).toString());
					permissions.add(permission);
				}
			}
			role.setPermissions(permissions);
			userRepository.save(user);
		}
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.UPDATE_SUCCESS);
	}
	
	
}
