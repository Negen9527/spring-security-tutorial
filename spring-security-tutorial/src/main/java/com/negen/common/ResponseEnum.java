package com.negen.common;


/**
 * 统一响应枚举
 * @Author Negen
 * @Date 2020年1月10日 上午9:42:52
 */
public enum ResponseEnum {
	INNER_ERROR(2001, "内部错误"),
	LOGIN_SUCCESS(2000, "登录成功"),
	LOGIN_FAILED(3000, "登录失败，账号或密码错误"),
	ACCOUNT_NOT_FOUND(2002, "账号不存在，请先注册"),
	ACCOUT_NOT_LOGIN(4000, "账号未登录"),
	INVALID_PARAM(40001, "参数非法"),
	NO_PERMISSION(40003, "无权操作"),
	USERNAME_EXSIT(3002, "用户名已存在"),
	REGISTE_SUCCESS(2000, "注册成功"),
	UPDATE_SUCCESS(2000, "修改成功"),
	GET_SUCCESS(2000, "数据获取成功"),
	;
	Integer code;		//响应码 
	String message;		//响应信息
	
	ResponseEnum(Integer code, String message){
		this.code = code;
		this.message = message;
	}
}
