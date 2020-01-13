package com.negen.service;

import com.alibaba.fastjson.JSONArray;
import com.negen.common.ServerResponse;
import com.negen.entity.User;

public interface IUserService {

	//新增用户
	ServerResponse addUser(User user);
	//获取所有用户
	ServerResponse listAllUser();
	//分页获取用户
	ServerResponse pageableListUser(int page, int size);
	//修改账号角色信息、权限信息
	ServerResponse modifyUserRole(long userid, String roleName);
	//修改账号权限
	ServerResponse modifyUserPermission(long userid,JSONArray permissionNames);
	
}
