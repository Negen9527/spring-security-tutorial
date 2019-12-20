package com.negen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
	public void addUser(User user) {
		//初始化角色、权限
		List<Permission> permissions = new ArrayList<Permission>();
		List<Role> roles = new ArrayList<Role>();
		Role role = new Role();
		Permission permission = new Permission();
		permission.setPermissionName("create");
		permissions.add(permission);
		
		role.setRoleName("admin");
		role.setPermissions(permissions);
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepository.save(user);
//		roleRepository.save(role);
//		permissionRepository.save(permission);
	}
}
