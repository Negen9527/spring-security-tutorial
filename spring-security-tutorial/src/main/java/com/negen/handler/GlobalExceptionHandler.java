package com.negen.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.negen.common.ResponseEnum;
import com.negen.common.ServerResponse;

/**
 * 统一异常处理
 * @Author Negen
 * @Date 2020年1月10日 下午2:17:16
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ServerResponse globalExceptionHandler(Exception e) {
		e.printStackTrace();	//打印错误信息
		if (e instanceof AccessDeniedException) {
			return ServerResponse.getInstance()
					.responseEnum(ResponseEnum.NO_PERMISSION);
		}
		
		return ServerResponse.getInstance()
				.responseEnum(ResponseEnum.INNER_ERROR);
	}
}
