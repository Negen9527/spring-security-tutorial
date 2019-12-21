package com.negen.common;

import java.util.List;
import java.util.*;
import com.negen.entity.Permission;
import com.negen.common.PermissionEnum;

public enum RoleEnum {
	ROLE_ADMIN("ROLE_ADMIN", new ArrayList<Permission>() {{
		add(PermissionEnum.PERMISSION_CREATE.getValue());
		add(PermissionEnum.PERMISSION_QUERY.getValue());
		add(PermissionEnum.PERMISSION_UPDATE.getValue());
		add(PermissionEnum.PERMISSION_DELETE.getValue());
	}}),
	ROLE_MANAGER("ROLE_MANAGER",new ArrayList<Permission>() {{
		add(PermissionEnum.PERMISSION_CREATE.getValue());
		add(PermissionEnum.PERMISSION_QUERY.getValue());
		add(PermissionEnum.PERMISSION_UPDATE.getValue());
		add(PermissionEnum.PERMISSION_DELETE.getValue());
	}}),
	ROLE_USER("ROLE_USER",new ArrayList<Permission>() {{
		add(PermissionEnum.PERMISSION_QUERY.getValue());
	}})
	;
	
	private String key;
	private List<Permission> value;
	
	private RoleEnum(String key, List<Permission> value) {
		this.key = key;
		this.value = value;
	}
	
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public List<Permission> getValue() {
		return value;
	}

	public void setValue(List<Permission> value) {
		this.value = value;
	}
	

	public static RoleEnum getRoleEnum(String roleName) {
		switch (roleName) {
		case "ROLE_ADMIN":
			return ROLE_ADMIN;
		case "ROLE_MANAGER":
			return ROLE_MANAGER;	
		case "ROLE_USER":
			return ROLE_USER;
		}
		return null;
	}
	
	
}	
