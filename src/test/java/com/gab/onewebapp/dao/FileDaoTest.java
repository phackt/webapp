package com.gab.onewebapp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
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

import com.gab.onewebapp.model.FileEntity;
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
public class FileDaoTest {

	@Autowired
	private FileDao fileDao;
	
	@Autowired
	private UserDao userDao;
	
	private UserEntity user1;
	private UserEntity user2;
	
	@Before
    public void setUp() {
		this.user1 = this.userDao.findByUsername("user1");
		this.user2 = this.userDao.findByUsername("user2");
    }
	
	@Test
	public void should_find_files(){
				
		assertNotNull(fileDao.findAll(null));
		assertEquals(fileDao.findAll(user1.getUsername()).get(0).getOriginalFilename(), "fichier1.txt");
		assertEquals(fileDao.findAll(user2.getUsername()).get(0).getOriginalFilename(), "fichier2.txt");

		FileEntity fileEntity = fileDao.findByOriginalFilename(user1.getUsername(),"fichier1.txt").get(0);
		assertNotNull(fileEntity);
		assertNotNull(fileDao.findById(fileEntity.getId()));
		
		List<FileEntity> fileEntities = fileDao.findByOriginalFilename(user1.getUsername(),"fichier2.txt");
		assertTrue(fileEntities.isEmpty());
		assertNotNull(fileDao.findLike(null,"fichier").get(0));
		assertNotNull(fileDao.findLike(user1.getUsername(),"fichier").get(0));
		assertNotNull(fileDao.findLike(user2.getUsername(),"fichier").get(0));
		assertNotNull(fileDao.findAll(null,0,1).get(0));	
		assertNotNull(fileDao.findAll(user1.getUsername(),0,1).get(0));
		assertNotNull(fileDao.findAll(user2.getUsername(),0,1).get(0));
	}
	
	@Test
	public void should_return_max_version_of_files(){

		assertEquals((long)fileDao.getLastVersion(user1.getUsername(),"fichier1.txt"), 1L);
		assertEquals((long)fileDao.getLastVersion(user2.getUsername(),"fichier2.txt"), 1L);
	}
	
	@Test
	public void should_update_files(){

		FileEntity fileEntity = fileDao.findByOriginalFilename(user1.getUsername(),"fichier1.txt").get(0);
		fileEntity.setDescription("updated");
		fileDao.saveOrUpdate(fileEntity);
		assertEquals(fileDao.findById(fileEntity.getId()).getDescription(), "updated");
	}
	
	@Test
	public void should_return_number_of_files(){
		assertEquals(fileDao.numberOfFiles(), 2);
	}
	
	@Test
	public void should_delete_files(){
		
		FileEntity fileEntity = fileDao.findByOriginalFilename(user1.getUsername(),"fichier1.txt").get(0);
		Long id = fileEntity.getId();
		fileDao.delete(fileEntity);
		
		assertNull(fileDao.findById(id));
	}
}
