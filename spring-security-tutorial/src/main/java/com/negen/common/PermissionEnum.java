package com.negen.common;

import com.negen.entity.Permission;

public enum PermissionEnum {
	PERMISSION_CREATE("CREATE", new Permission("CREATE")),
	PERMISSION_QUERY("QUERY", new Permission("QUERY")),
	PERMISSION_UPDATE("UPDATE", new Permission("UPDATE")),
	PERMISSION_DELETE("DELETE", new Permission("DELETE")),
	;
	private String key;
	private Permission value;
	
	
	private PermissionEnum(String key, Permission value) {
		this.key = key;
		this.value = value;
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public Permission getValue() {
		return value;
	}

	public void setValue(Permission value) {
		this.value = value;
	}

	
	
	
}


