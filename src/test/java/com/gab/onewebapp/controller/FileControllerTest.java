package com.gab.onewebapp.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.gab.onewebapp.dao.FileDao;


/**
 * @author gabriel
 * This allows performing requests and generating responses without the need for running in a Servlet container.
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml"),
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml")
})
public class FileControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(FileControllerTest.class);
	
	@Autowired
    private WebApplicationContext wac;
	
	@Autowired
	private FileDao fileDao;
	
	private MockMvc mockMvc;
	
	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@After
	public void tearDown(){
		String filesUploadedPath = wac.getServletContext().getRealPath("") + "/resources/files";
		try {
			FileUtils.cleanDirectory(new File(filesUploadedPath));
		} catch (IOException e) {
			logger.error("Exception raised in test method FileControllerTest.tearDown()");
			logger.error(e.toString());
			logger.error("stack trace: " + ExceptionUtils.getStackTrace(e));
		}
	}
	
	@Test
	@Transactional
	public void should_be_success_when_file_uploaded() {
		final String fileName = "test.txt";
		final byte[] content = "Hello Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", content);
		
		try {
			this.mockMvc.perform(fileUpload(FileController.ROUTE_UPLOAD_FILE).file(mockMultipartFile).param("description", "fichier test"))
			.andExpect(status().isOk())
			.andExpect(view().name(FileController.ROUTE_SHOW_FILES));
			
			assertFalse(fileDao.find(fileName).isEmpty());
			assertEquals(fileDao.find(fileName).get(0).getDescription(),"fichier test");
			
		} catch (Exception e) {
			logger.error("Exception raised in test method FileControllerTest.should_be_success_when_file_uploaded()");
			logger.error(e.toString());
			logger.error("stack trace: " + ExceptionUtils.getStackTrace(e));
		}
	}
}
