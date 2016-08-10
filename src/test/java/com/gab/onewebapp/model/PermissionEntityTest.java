package com.gab.onewebapp.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.gab.onewebapp.core.enums.PermissionType;

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
public class PermissionEntityTest {

	@Test
	public void should_be_equals(){
		PermissionEntity pe1 = new PermissionEntity();
		PermissionEntity pe2 = new PermissionEntity();
		
		pe1.setId(0);
		pe1.setPermissionType(PermissionType.PERM_UPLOAD_FILE);
		
		pe2.setId(0);
		pe2.setPermissionType(PermissionType.PERM_UPLOAD_FILE);
		
		assertTrue(pe1.equals(pe2));
	}
}
