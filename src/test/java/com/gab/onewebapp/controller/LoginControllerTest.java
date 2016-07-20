package com.gab.onewebapp.controller;

import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.Matchers.not;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.hamcrest.core.IsNot;
import org.hamcrest.text.IsEmptyString;
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
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"),
	@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/security-context.xml")
})
public class LoginControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(LoginControllerTest.class);
	
	@Autowired
    private WebApplicationContext wac;
	 
	private MockMvc mockMvc;
	
	@Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
        		.apply(springSecurity())
                .build();
    }
	
	@Test
	@Transactional
	public void should_display_login_page_no_action() throws Exception {
		
		this.mockMvc.perform(get(LoginController.ROUTE_LOGIN))
		.andExpect(status().isOk())
		.andExpect(model().attribute("msgLoginController", isEmptyOrNullString()))
		.andExpect(view().name(LoginController.VIEW_LOGIN));			
	}
	
	@Test
	@Transactional
	public void should_display_login_page_error() throws Exception {
		
		this.mockMvc.perform(get(LoginController.ROUTE_LOGIN)
				.param("action", "error")
				)
		.andExpect(status().isOk())
		.andExpect(model().attribute("msgLoginController", not(isEmptyOrNullString())))
		.andExpect(view().name(LoginController.VIEW_LOGIN));			
	}
	
	@Test
	@Transactional
	public void should_display_login_page_logout() throws Exception {
		
		this.mockMvc.perform(get(LoginController.ROUTE_LOGIN)
				.param("action", "logout")
				)
		.andExpect(status().isOk())
		.andExpect(model().attribute("msgLoginController", not(isEmptyOrNullString())))
		.andExpect(view().name(LoginController.VIEW_LOGIN));			
	}
}
