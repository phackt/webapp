package com.gab.onewebapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.exception.ExceptionUtils;
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
 * Handles requests for the application home page.
 */
@Controller
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Autowired
	private FileDao fileDao;
	
	public static final String ROUTE_UPLOAD_FILE = "/uploadFile";
	public static final String ROUTE_SHOW_FILES = "/showFiles";
	public static final String ROUTE_DISPLAY_FILE_UPLOAD_FORM = "/displayFileUploadForm";
	
	@RequestMapping(value = ROUTE_UPLOAD_FILE, method = RequestMethod.POST)
	public ModelAndView uploadFile(Model model, 
			@Valid @ModelAttribute("fileUploadForm") FileUploadForm fileUploadForm,
			BindingResult result, 
			HttpServletRequest req) {

		logger.info("calling route " + ROUTE_UPLOAD_FILE);
		
		String route = ROUTE_DISPLAY_FILE_UPLOAD_FORM;
		
		if(!result.hasErrors()){
			
			try {
				MultipartFile file = fileUploadForm.getFile();
				byte[] bytes= file.getBytes();
				
				String dirPath = req.getSession().getServletContext().getRealPath("") + "/resources/files";
				String serverFile = dirPath + File.separator + file.getOriginalFilename();
				
				//Creation du fichier sur le serveur
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(serverFile)));
				bos.write(bytes);
				bos.close();
				
				fileDao.saveOrUpdate(new FileEntity(file.getOriginalFilename(),fileUploadForm.getDescription()));
				
				model.addAttribute("msgUpload","Upload effectué: " + file.getOriginalFilename());
				
				route = ROUTE_SHOW_FILES;
			} catch (IOException e) {
				logger.error("Exception raised in test method FileController.uploadFile()");
				logger.error(e.toString());
				logger.error("stack trace: " + ExceptionUtils.getStackTrace(e));
				model.addAttribute("msgUpload","Erreur lors de l'envoi du fichier.");
			}
			
		}
		
		return new ModelAndView(route, model.asMap());
		
	}	
}

