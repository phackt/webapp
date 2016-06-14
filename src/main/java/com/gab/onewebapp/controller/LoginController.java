package com.gab.onewebapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	public static final String ROUTE_LOGIN = "/login";
	public static final String VIEW_LOGIN = "login";
	
	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = ROUTE_LOGIN, method = RequestMethod.GET)
	public String showLogin(Model model, 
			@RequestParam(value = "action", required = false) String action,
			HttpServletRequest req, 
			HttpServletResponse resp) {

		String msgLoginController = "Please entre your username and password:";
		
		if(action != null){
			switch(action){
			case "error":
				msgLoginController = "Invalid username and password!";
				break;
			case "logout":
				msgLoginController = "You've been logged out successfully!";
				break;
			}
		}
		
		  
		model.addAttribute("msgLoginController", msgLoginController);
		
		return VIEW_LOGIN;
	}
}

