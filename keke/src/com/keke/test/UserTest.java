package com.keke.test;

import java.sql.Timestamp;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.keke.bean.user.User;
import com.keke.service.user.UserService;
import com.keke.util.MD5Util;

public class UserTest {
	
	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-*.xml");  
	UserService userService = (UserService) context.getBean("userService");
	
	
	
	@Test
	public void testSaveUser() {
		for (int i = 1; i < 30; i++) {
			User user = new User();
			
		}
		
		

	}
	

	
}
