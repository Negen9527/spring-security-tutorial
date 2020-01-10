package com.negen.common;

import java.util.Arrays;
import java.util.List;

import com.negen.entity.Permission;
import com.negen.entity.Role;

public class Constant {
	
	/**
	 * 角色常量
	 * @Author Negen
	 * @Date 2020年1月10日 下午3:52:01
	 */
	public static class RoleConstant{
		public final static String ADMIN = "admin";
		public final static String MANAGER = "manager";
		public final static String USER = "user";
	}
	
	
	/**
	 * 权限常量
	 * @Author Negen
	 * @Date 2020年1月10日 下午3:51:49
	 */
	public static class PermissionConstant{
		public final static String CREATE = "create";
		public final static String QUERY = "query";
		public final static String UPDATE = "update";
		public final static String DELETE = "delete";
	}
	
	
	/**
	 * 按角色生成响应的权限
	 * @param roleName
	 * @return
	 */
	public static Role generateRole(String roleName) {
		List<Permission> permissions = null;
		Role role = new Role();
		role.setRoleName(roleName);
		Permission pCreate = new Permission("create");
		Permission pQuery = new Permission("query");
		Permission pUpdate = new Permission("update");
		Permission pDelete = new Permission("delete");
		
		switch (roleName) {
		case RoleConstant.ADMIN:
			permissions = Arrays.asList(pCreate,pQuery,pUpdate,pDelete); 
			break;
		case RoleConstant.MANAGER:
			permissions = Arrays.asList(pCreate,pQuery,pUpdate,pDelete); 
			break;
		case RoleConstant.USER:
			permissions = Arrays.asList(pQuery); 
			break;
		default:
			break;
		}
		role.setPermissions(permissions);
		return role;
	}
}
