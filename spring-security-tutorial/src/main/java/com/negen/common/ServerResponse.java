package com.negen.common;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
@Data
public class ServerResponse<T> {
	int code;
	String msg;
	T data;
	
	public ServerResponse (int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	public ServerResponse (int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static <T> ServerResponse<T> buildSuccess(String msg) {
		return new ServerResponse<T>(200, msg);
	}
	
	public static <T> ServerResponse<T> buildSuccess(String msg, T data) {
		return new ServerResponse<T>(200, msg, data);
	}
	
	public static <T> ServerResponse<T> buildFail(String msg) {
		return new ServerResponse<T>(300, msg);
	}
	
	@Override
	public String toString() {
		JSONObject resultJson = new JSONObject();
		resultJson.put("code", this.code);
		resultJson.put("msg", this.msg);
		resultJson.put("data", this.data);
		return resultJson.toString();
	}

	
	
	
	
	
}
