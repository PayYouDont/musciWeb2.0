package com.payudon;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.payudon.user.dao.UserDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Resource
	private UserDAO userDao;
	
	@Test
	public void contextLoads() {
		System.out.println(userDao.selectByPrimaryKey(18));
	}
}
