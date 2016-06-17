package com.gab.onewebapp.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gab.onewebapp.beans.form.FileUploadForm;
import com.gab.onewebapp.service.FileService;

/**
 * @author gabriel
 * 
 */
@Controller
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
		
	public static final String ROUTE_UPLOAD_FILE = "/uploadFile";
	
	public static final String ROUTE_SHOW_FILES = "/showFiles";
	public static final String VIEW_SHOW_FILES = "showFiles";
	
	public static final String ROUTE_DELETE_FILE = "/deleteFile";
	public static final String ROUTE_UPDATE_FILE = "/updateFile";
	
	@Autowired
	public FileService fileService;
	
	//TODO: http://www.baeldung.com/role-and-privilege-for-spring-security-registration
	//TODO: http://www.mkyong.com/spring-security/spring-security-form-login-using-database/
	//TODO: access static route name in jsp
	@RequestMapping(value = ROUTE_UPLOAD_FILE, method = RequestMethod.POST)
	public ModelAndView uploadFile(Model model, 
			@Valid @ModelAttribute("fileUploadForm") FileUploadForm fileUploadForm,
			BindingResult result, 
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		if(!result.hasErrors()){
			
			try {

				MultipartFile file = fileUploadForm.getFile();
				
				if(!StringUtils.isEmpty(file.getOriginalFilename())){
					fileService.saveOrUpdate(file.getBytes(), file.getOriginalFilename(),fileUploadForm.getDescription());				
					model.addAttribute("msgFileController","Upload effectué: " + file.getOriginalFilename());
				}else{
					model.addAttribute("msgFileController","Please select a file!");
				}				
				
			} catch (IOException e) {
				logger.error("Exception raised:",e);
				result.rejectValue("exception", "ioexception.message", "Erreur lors de l'envoi du fichier.");
			}
			
		}
		
		return this.showFiles(model, httpServletRequest, httpServletResponse);		
	}	
	
	@RequestMapping(value = ROUTE_SHOW_FILES, method = RequestMethod.GET)
	public ModelAndView showFiles(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		ModelAndView modelAndView = new ModelAndView(VIEW_SHOW_FILES);

		modelAndView.addObject("listFiles", this.fileService.findAll());
		
		//On ajoute le formulaire d'envoi de fichiers si besoin
		if (!model.containsAttribute("fileUploadForm"))
			model.addAttribute("fileUploadForm", new FileUploadForm());
		
		return modelAndView;	
	}
	
	@PreAuthorize("hasAuthority('PERM_CACA')")
	@RequestMapping(value = ROUTE_DELETE_FILE, method = RequestMethod.GET)
	public ModelAndView deleteFile(Model model, @RequestParam("id")Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		this.fileService.deleteFile(id);
		model.addAttribute("msgFileController","Fichier supprimé avec succès");
		
		return this.showFiles(model, httpServletRequest, httpServletResponse);		
	}
}

