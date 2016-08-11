package com.gab.onewebapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import com.gab.onewebapp.config.ApplicationCommonConfig;
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

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ApplicationCommonConfig appConfig;
	
	@Before
    public void setUp() {

        User principal = new User("user1","$2a$10$A0Hakc4SohjPw0q.UTCdIeLiN6J4bO7fPMBvdmnpRmwyVnGQIcubG", true, true, true, true, new ArrayList<GrantedAuthority>());
        TestingAuthenticationToken token = new TestingAuthenticationToken(principal, principal.getAuthorities());    
        SecurityContextHolder.getContext().setAuthentication(token);
		SecurityContextHolder.getContext().getAuthentication().setAuthenticated(true);
    }
	
	@After
	public void tearDown() throws IOException{
		String filesUploadedPath = this.appConfig.getUploadDirPath();
		FileUtils.cleanDirectory(new File(filesUploadedPath));
		new File(filesUploadedPath + File.separator + ".keep").createNewFile();
	}
	
	@Test
	public void shouldVersionFiles() throws IOException{
		String fileContent = "ceci est le contenu";
		assertEquals((long)this.fileService.getLastVersionFromCurrentUser("fichier1.txt"), 1L);
		this.fileService.saveOrUpdate(fileContent.getBytes(), "fichier1.txt", "fichier de test 1");
		assertEquals((long)this.fileService.getLastVersionFromCurrentUser("fichier1.txt"), 2L);
		assertEquals(this.fileService.findAllFromCurrentUser().size(),2);
	}
	
	@Test
	public void shouldDeleteFileWithId(){
		this.fileService.deleteFile(0L);
		assertNull(this.fileService.findById(0));
	}
	
}
