package com.negen.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.negen.entity.Permission;
import com.negen.entity.Role;
import com.negen.entity.User;
import com.negen.repository.UserRepository;

@Service
public class BookUserDetailsService implements UserDetailsService {
	private static final Logger log = LoggerFactory.getLogger(BookUserDetailsService.class);

	@Autowired
	UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//查找用户是否存在并分配权限
		User user = userRepository.findByUserName(username);
		if(null == user) {
			//用户不存在
			log.info("\n用户:"+username+"不存在");
			throw new UsernameNotFoundException(username);
		}else {
			//用户存在
			List<SimpleGrantedAuthority> authorities = 
					new ArrayList<SimpleGrantedAuthority>();
			for (Role role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
				for (Permission permission : role.getPermissions()) {
					authorities.add(new SimpleGrantedAuthority(permission.getPermissionName()));
				}
			}
			log.info("\n" + authorities.toString());
			return new org.springframework.security.core.userdetails.User(username, user.getPassword(), authorities);
		}
		
	}

}
