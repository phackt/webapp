package com.gab.onewebapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

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

import com.gab.onewebapp.model.FileEntity;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;

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
@DatabaseSetup("/dbtest/sample-fileDaoTest.xml")
public class FileDaoTest {

	private static final Logger logger = LoggerFactory.getLogger(FileDaoTest.class);

	@Autowired
	private FileDao fileDao;
	
	@Test
	public void should_find_files(){
		assertNotNull(fileDao.findAll());
		assertEquals(fileDao.findAll().get(0).getName(), "fichier.txt");
		
		FileEntity fileEntity = fileDao.find("fichier.txt").get(0);
		assertNotNull(fileEntity);
		assertNotNull(fileDao.find(fileEntity.getId()));
		assertNotNull(fileDao.findLike("fichier").get(0));
		assertNotNull(fileDao.findAll(0,1).get(0));	
	}
	
	@Test
	@Transactional
	public void should_update_files(){
		FileEntity fileEntity = fileDao.find("fichier.txt").get(0);
		fileEntity.setDescription("updated");
		fileDao.saveOrUpdate(fileEntity);
		assertEquals(fileDao.find(fileEntity.getId()).getDescription(), "updated");
	}
	
	@Test
	public void should_return_number_of_files(){
		assertEquals(fileDao.numberOfFiles(), 1);
	}
	
	@Test
	@Transactional
	public void should_delete_files(){
		FileEntity fileEntity = fileDao.find("fichier.txt").get(0);
		Long id = fileEntity.getId();
		fileDao.delete(fileEntity);
		
		List<FileEntity> list = fileDao.findAll();
		assertNull(fileDao.find(id));
	}
}
