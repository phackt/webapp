package com.gab.onewebapp.model;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
public class FileEntityTest {

	private static final Logger logger = LoggerFactory.getLogger(FileEntityTest.class);

	@Test
	public void should_be_equals(){
		FileEntity fe1 = new FileEntity();
		
		UserEntity ue = new UserEntity();
		FileEntity fe2 = new FileEntity(ue,"fichier.txt","fichier.txt","fichier de test", new Date());
		
		fe1.setId(0);
		fe1.setOriginalFilename("fichier.txt");
		
		fe2.setId(0);
		fe2.setOriginalFilename("fichier.txt");
		
		assertTrue(fe1.equals(fe2));
	}
}
