package com.gab.onewebapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

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
@DatabaseSetup("/dbtest/sample-fileDaoTest.xml")
public class FileServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(FileServiceTest.class);

	@Autowired
	private FileService fileService;
	
	@Before
    public void setUp() {

        User principal = new User("user1","$2a$10$A0Hakc4SohjPw0q.UTCdIeLiN6J4bO7fPMBvdmnpRmwyVnGQIcubG", true, true, true, true, new ArrayList<GrantedAuthority>());
        TestingAuthenticationToken token = new TestingAuthenticationToken(principal, principal.getAuthorities());    
        SecurityContextHolder.getContext().setAuthentication(token);
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
    }
	
	@Test
	public void should_version_files(){
		try {
			
			String fileContent = "ceci est le contenu";
			assertEquals((long)this.fileService.getLastVersionFromCurrentUser("fichier1.txt"), 1L);
			this.fileService.saveOrUpdate(fileContent.getBytes(), "fichier1.txt", "fichier de test 1");
			assertEquals((long)this.fileService.getLastVersionFromCurrentUser("fichier1.txt"), 2L);
			assertEquals(this.fileService.findAllFromCurrentUser().size(),2);
			
		} catch (IOException e) {
			logger.error("Exception raised:",e);
		}
	}
	
	@Test
	public void should_delete_file_with_id(){
		this.fileService.deleteFile(0L);
		assertNull(this.fileService.findById(0));
	}
	
}
