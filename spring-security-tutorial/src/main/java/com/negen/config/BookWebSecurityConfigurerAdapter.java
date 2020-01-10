package com.negen.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.negen.common.ResponseEnum;
import com.negen.common.ServerResponse;
import com.negen.service.impl.BookUserDetailsService;
import com.negen.utils.PrintUtil;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BookWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Autowired
	BookUserDetailsService bookUserDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("AuthenticationManagerBuilder");
		auth.userDetailsService(bookUserDetailsService)
		.passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/user/register").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/login")
		.successHandler(successHandler())
		.failureHandler(authenticationFailureHandler())
		.permitAll()
		;
//		.anyRequest().permitAll();
	}
	
	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return new AuthenticationSuccessHandler(){
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
				PrintUtil.print(response, 
						ServerResponse.getInstance()
						.responseEnum(ResponseEnum.LOGIN_SUCCESS).toString());
			}};
	}
	
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				PrintUtil.print(response, 
						ServerResponse.getInstance()
						.responseEnum(ResponseEnum.LOGIN_FAILED).toString());
			}
		};
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
