package com.gab.onewebapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author gabriel
 * 
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public static final String ROUTE_ROOT = "/";
	public static final String ROUTE_HOME = "/home";
	public static final String VIEW_HOME = "home";
	
	@RequestMapping(value = {ROUTE_HOME, ROUTE_ROOT}, method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		return VIEW_HOME;	
	}	
	
}

