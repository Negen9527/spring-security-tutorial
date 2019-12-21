package com.negen.service;

import com.negen.common.ServerResponse;
import com.negen.entity.User;

public interface IUserService {

	//新增用户
	ServerResponse addUser(User user);
	//获取所有用户
	ServerResponse listAllUser();
	//修改账号角色信息、权限信息
	ServerResponse modifyUserRoleAndPermission(long userid, String roleName);
}
