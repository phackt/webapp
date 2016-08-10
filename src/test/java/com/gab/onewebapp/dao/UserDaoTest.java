package com.gab.onewebapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

import com.gab.onewebapp.model.UserEntity;
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
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
	DirtiesContextTestExecutionListener.class,
	TransactionalTestExecutionListener.class,
	DbUnitTestExecutionListener.class })
@Transactional
@DatabaseSetup("/dbtest/sample-fileDaoTest.xml")
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void should_find_all(){
		assertEquals(userDao.findAll().size(), 2);
	}
	
	@Test
	public void should_return_number_of_users(){
		assertEquals(userDao.numberOfUsers(),2);
	}
	
	@Test
	public void should_save_or_update(){
		UserEntity userEntity = userDao.findByUsername("user1");
		userEntity.setUsername("user_updated");
		userEntity.setEnabled(true);
		userEntity.setLocked(true);
		userEntity.setExpired(true);
		userEntity.setCredentialsExpired(true);	
		userDao.saveOrUpdate(userEntity);
		UserEntity userEntityUpdated = userDao.findByUsername("user_updated");
		assertNotNull(userEntityUpdated);	
		assertTrue(userEntityUpdated.isEnabled());
		assertFalse(userEntityUpdated.isAccountNonLocked());
		assertFalse(userEntityUpdated.isAccountNonExpired());
		assertFalse(userEntityUpdated.isCredentialsNonExpired());
	}
}
