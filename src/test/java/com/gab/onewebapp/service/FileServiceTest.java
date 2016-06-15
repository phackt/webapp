package com.gab.onewebapp.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.gab.onewebapp.dao.FileDao;
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
public class FileServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(FileServiceTest.class);

	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileDao fileDao;
	
	@Test
	public void should_version_files(){
		try {
			
			String fileContent = "ceci est le contenu";
			assertEquals((long)fileDao.getLastVersion("fichier.txt"), 1L);
			this.fileService.saveOrUpdate(fileContent.getBytes(), "fichier.txt", "fichier de test");
			assertEquals((long)fileDao.getLastVersion("fichier.txt"), 2L);
			
		} catch (IOException e) {
			logger.error("Exception raised:",e);
		}
	}
	
	@Test
	public void should_delete_file_with_id(){
		this.fileService.deleteFile(0L);
		assertNull(this.fileDao.findById(0));
	}
	
}
