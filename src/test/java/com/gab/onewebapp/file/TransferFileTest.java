package com.gab.onewebapp.file;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.gab.onewebapp.controller.FileController;


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
public class TransferFileTest {

	private static final Logger logger = LoggerFactory.getLogger(TransferFileTest.class);
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
	public void should_be_success_when_file_uploaded() {
		final String fileName = "test.txt";
		final byte[] content = "Hello Word".getBytes();
		MockMultipartFile mockMultipartFile = new MockMultipartFile("file", fileName, "text/plain", content);

		try {
			this.mockMvc.perform(fileUpload(FileController.ROUTE_UPLOAD_FILE).file(mockMultipartFile))
			.andExpect(status().isOk());
		} catch (Exception e) {
			logger.error("Exception raised in test method TransferFileTest.should_be_success_when_file_uploaded()");
			logger.error(e.toString());
			logger.error("stack trace: " + e.getStackTrace());
		}
	}

}
