package com.gab.onewebapp.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gab.onewebapp.core.enums.UserProfileType;

/**
 * @author gabriel
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml"),
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"),
})
public class UserProfileEntityTest {

	private static final Logger logger = LoggerFactory.getLogger(UserProfileEntityTest.class);

	@Test
	public void should_be_equals(){
		
		UserProfileEntity upe1 = new UserProfileEntity();
		UserProfileEntity upe2 = new UserProfileEntity();
		
		upe1.setId(0);
		upe1.setUserProfileType(UserProfileType.USER);
		
		upe2.setId(0);
		upe2.setUserProfileType(UserProfileType.USER);
		
		assertTrue(upe1.equals(upe2));
	}
}
