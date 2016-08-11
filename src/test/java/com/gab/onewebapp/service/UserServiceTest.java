package com.gab.onewebapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.core.enums.UserProfileType;
import com.gab.onewebapp.model.UserEntity;
import com.gab.onewebapp.model.UserProfileEntity;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

/**
 * @author gabriel
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml"),
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"),
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/security-context.xml")
})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup("/dbtest/sample-userDaoTest.xml")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void shouldGetUserRole(){
		
		UserEntity userEntity = this.userService.findByUsername("user");
		Set<UserProfileEntity> userProfiles = userEntity.getUserProfiles();
		
		assertFalse(userProfiles.isEmpty());
		assertEquals(userProfiles.iterator().next().getUserProfileType(), UserProfileType.USER);
	}
	
	@Test
	public void shouldDeleteUserWithId(){
		this.userService.deleteUser(0L);
		assertNull(this.userService.findById(0));
	}
	
	@Test
	public void shouldSaveOrUpdate(){
		UserEntity userEntity = this.userService.findByUsername("user");
		userEntity.setUsername("user_updated");
		this.userService.saveOrUpdate(userEntity);
		assertNotNull(this.userService.findByUsername("user_updated"));
	}
	
}
