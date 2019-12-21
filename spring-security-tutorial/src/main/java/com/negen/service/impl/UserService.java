package com.negen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.negen.common.RoleEnum;
import com.negen.common.ServerResponse;
import com.negen.entity.Permission;
import com.negen.entity.Role;
import com.negen.entity.User;
import com.negen.repository.PermissionRepository;
import com.negen.repository.RoleRepository;
import com.negen.repository.UserRepository;
import com.negen.service.IUserService;
@Service
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
			return ServerResponse.buildSuccess("用户名已存在");
		}
		//初始化角色为 user
		List<Permission> permissions = new ArrayList<Permission>();
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		
		role.setRoleName(RoleEnum.ROLE_USER.getKey()); 
		role.setPermissions(RoleEnum.ROLE_USER.getValue());
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(user);
		return ServerResponse.buildSuccess("注册成功");
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
		return ServerResponse.buildSuccess("获取成功", jsonUserArray);
	}

	@Transactional
	@Override
	public ServerResponse modifyUserRoleAndPermission(long userid, String roleName) {
		User user  = userRepository.findById(userid).get();
		if(roleName.equals(user.getRoles().get(0).getRoleName())) {
			return ServerResponse.buildSuccess("修改成功");
		}
		for (Role role : user.getRoles()) {
			for (Permission permission : role.getPermissions()) {
				permissionRepository.delete(permission);
			}
			roleRepository.delete(role);
		}
		RoleEnum roleEnum = RoleEnum.getRoleEnum(roleName);
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		role.setRoleName(roleEnum.getKey());
		role.setPermissions(roleEnum.getValue());
		roles.add(role);
		user.setRoles(roles);
		return ServerResponse.buildSuccess("修改成功");
	}
	
	
	
	
}
