package com.negen;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.negen.entity.User;
import com.negen.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void testUserPageable() {
		PageRequest pageable = PageRequest.of(0, 3);
		Page<User> page = userRepository.findAll(pageable);
		System.out.println("总页数：" + page.getTotalPages());
		System.out.println("当前页用户数：" + page.getContent().size());
		List<User> users = page.getContent();
		users.forEach(System.out::println);
		System.out.println("getTotalElements===>" + page.getTotalElements());   //总记录数
	}
}
