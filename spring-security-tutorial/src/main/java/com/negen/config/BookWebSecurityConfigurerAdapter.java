package com.negen.config;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alibaba.fastjson.JSONObject;
import com.negen.common.ResponseEnum;
import com.negen.common.ServerResponse;
import com.negen.entity.User;
import com.negen.repository.UserRepository;
import com.negen.service.impl.BookUserDetailsService;
import com.negen.utils.PrintUtil;
import com.negen.utils.TokenUtil;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BookWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Autowired
	BookUserDetailsService bookUserDetailsService;
	@Autowired
	UserRepository userRepository;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(bookUserDetailsService)
//		.passwordEncoder(passwordEncoder());
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/user/register",
					 "/swagger*//**",
	                 "/v2/api-docs",
	                 "/webjars*//**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/user/login")
		.loginProcessingUrl("/login")
		.successHandler(authenticationSuccessHandler())
		.failureHandler(authenticationFailureHandler())
		.permitAll()
		;
	}
	
    @Bean
   public DaoAuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
       provider.setHideUserNotFoundExceptions(false);     //设置是否隐藏 UserNotFoundException
       provider.setUserDetailsService(bookUserDetailsService);
       provider.setPasswordEncoder(passwordEncoder());
       return provider;
   }


	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		//认证（登录）成功
		return new AuthenticationSuccessHandler(){
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
				Authentication authentication) throws IOException, ServletException {
				JSONObject jsonData = new JSONObject();
				//生成token，写入数据库并返回给前端
				String userName = authentication.getName();
				User user = userRepository.findByUserName(userName);
				String userId = Long.toString(user.getId());
				String token = "";
				try {
					token = TokenUtil.createToken(userId, userName);
				} catch (Exception e) {
					e.printStackTrace();
				}
				user.setToken(token);
				userRepository.save(user);
				jsonData.put("token", token);
				PrintUtil.print(response, 
						ServerResponse.getInstance()
						.responseEnum(ResponseEnum.LOGIN_SUCCESS).data(jsonData).toString());
			}};
	}
	
	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new AuthenticationFailureHandler() {
			
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException exception) throws IOException, ServletException {
				if (exception instanceof UsernameNotFoundException) {
					//账号不存在
					PrintUtil.print(response, 
							ServerResponse.getInstance()
							.responseEnum(ResponseEnum.ACCOUNT_NOT_FOUND).toString());
					return;
				}
				//密码错误
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
