package com.gab.onewebapp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView showLogin(Model model, 
			@RequestParam(value = "action", required = false) String action,
			HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) {

		logger.info("calling url " + httpServletRequest.getRequestURL().toString());

		String msgLoginController = "";
		boolean isErrorClassActive = false;

		if(action != null){
			switch(action){
			case "error":
				msgLoginController = this.messageSource.getMessage("loginController.error.invalid_user_pass", null, LocaleContextHolder.getLocale());
				isErrorClassActive = true;
				break;
			case "logout":
				msgLoginController = this.messageSource.getMessage("loginController.label.logout_success", null, LocaleContextHolder.getLocale());
				break;
			default:
				msgLoginController = this.messageSource.getMessage("generic.error", null, LocaleContextHolder.getLocale());
				isErrorClassActive = true;
			}
		}
		
		  
		model.addAttribute("msgLoginController", msgLoginController);
		model.addAttribute("isErrorClassActive", isErrorClassActive);
		ModelAndView modelAndView = new ModelAndView(VIEW_LOGIN, model.asMap());
		
		return modelAndView;
	}
}

