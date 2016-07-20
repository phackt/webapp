package com.gab.onewebapp.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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
public class HomeControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(HomeControllerTest.class);
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
	
	@Test
	@Transactional
	public void should_be_successfully_logged() throws Exception {
		
		this.mockMvc.perform(get(HomeController.ROUTE_HOME).with(user("user").password("user").roles("USER")))
		.andExpect(status().isOk())
		.andExpect(view().name(HomeController.VIEW_HOME));			
	}	
}
