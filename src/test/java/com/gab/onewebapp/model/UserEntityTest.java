package com.gab.onewebapp.model;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gab.onewebapp.core.enums.PermissionType;
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
public class UserEntityTest {

	@Test
	public void should_be_equals(){
		UserEntity ue1 = new UserEntity();
		UserEntity ue2 = new UserEntity();
		
		UserProfileEntity upe1 = new UserProfileEntity();
		PermissionEntity pe1 = new PermissionEntity();
		pe1.setPermissionType(PermissionType.PERM_UPLOAD_FILE);
		Set<PermissionEntity> listPerms = new HashSet<>();
		listPerms.add(pe1);
		upe1.setUserProfileType(UserProfileType.USER);
		upe1.setPermissions(listPerms);
		
		
		ue1.setId(0);
		ue1.setUsername("user1");
		
		ue2.setId(0);
		ue2.setUsername("user1");
		
		assertTrue(ue1.equals(ue2));
	}
}
