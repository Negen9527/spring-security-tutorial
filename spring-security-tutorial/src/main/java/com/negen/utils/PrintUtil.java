package com.negen.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public class PrintUtil {

	public static void print(HttpServletResponse response, String msg) throws IOException {
		response.setContentType("application/json;charset=utf-8");
		PrintWriter printWriter = response.getWriter();
		printWriter.write(msg);
		printWriter.flush();
	}
	
}
