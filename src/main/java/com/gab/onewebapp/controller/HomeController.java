package com.gab.onewebapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gab.onewebapp.beans.form.FileUploadForm;
import com.gab.onewebapp.dao.FileDao;
import com.gab.onewebapp.model.FileEntity;

/**
 * @author gabriel
 * 
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	public static final String ROUTE_HOME = "/home";
	public static final String VIEW_HOME = "home";
	
	@RequestMapping(value = ROUTE_HOME, method = RequestMethod.GET)
	public ModelAndView home(Model model, HttpServletRequest req, HttpServletResponse httpServletResponse) {

		logger.info("calling route " + ROUTE_HOME);
		
		return new ModelAndView(VIEW_HOME);	
	}	
	
}

