package com.negen.common;

import com.alibaba.fastjson.JSONObject;

public class ServerResponse<T> {
	String code;
	String msg;
	T data;
	
	
	public ServerResponse (String code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
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
