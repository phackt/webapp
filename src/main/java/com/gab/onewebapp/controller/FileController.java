package com.gab.onewebapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	
	public static final String ROUTE_UPLOAD_FILE = "/uploadFile";
	public static final String ROUTE_SHOW_FILES = "/showFiles";
	
	@RequestMapping(value = ROUTE_UPLOAD_FILE, method = RequestMethod.POST)
	public ModelAndView uploadFile(Model model, HttpServletRequest req, @RequestParam("file")MultipartFile file) {

		logger.info("calling route " + ROUTE_UPLOAD_FILE);
		
		if(file.isEmpty()){
			model.addAttribute("msgUpload", "Fichier vide");
		}else{
			try {
				byte[] bytes= file.getBytes();
				
				String dirPath = req.getSession().getServletContext().getRealPath("") + "/resources/files";
				String serverFile = dirPath + File.separator + file.getOriginalFilename();
				
				//Creation du fichier sur le serveur
				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(serverFile)));
				bos.write(bytes);
				bos.close();
				model.addAttribute("msgUpload","Upload effectué: " + file.getOriginalFilename());
				
			} catch (IOException e) {
				model.addAttribute("msgUpload","Erreur: " + e.getMessage());
			}
			
		}
		
		return new ModelAndView(ROUTE_SHOW_FILES, model.asMap());
		
	}
	
}

