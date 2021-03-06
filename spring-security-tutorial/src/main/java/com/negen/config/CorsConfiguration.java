package com.negen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
//public class CorsConfiguration implements WebMvcConfigurer {
//	 @Override
//	 public void addCorsMappings(CorsRegistry registry) {
//	// 设置允许跨域的路径
//	registry.addMapping("/**")
//	    // 设置允许跨域请求的域名
//	    .allowedOrigins("*")
//	    // 是否允许证书 不再默认开启
//	    .allowCredentials(true)
//	    // 设置允许的方法
//	    .allowedMethods("*")
//	    // 跨域允许时间
//	    .maxAge(3600);
//	}
//}
@Configuration
public class CorsConfiguration {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                          .allowedOrigins("*")  
                          .allowCredentials(true)
                          .allowedMethods("GET", "POST", "DELETE", "PUT","PATCH")
                          .maxAge(3600);  
            }
        };
    }
}