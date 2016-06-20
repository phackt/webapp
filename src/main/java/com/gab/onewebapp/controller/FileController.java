package com.gab.onewebapp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;

import javax.servlet.ServletOutputStream;
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
import org.springframework.util.FileCopyUtils;
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
	public static final String ROUTE_DOWNLOAD_FILE = "/downloadFile";
	
	
	@Autowired
	public FileService fileService;
	
	//TODO: access static route name in jsp
	@PreAuthorize("hasAuthority('PERM_UPLOAD_FILE')")
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
	
	@PreAuthorize("hasAuthority('PERM_DELETE_FILE')")
	@RequestMapping(value = ROUTE_DELETE_FILE, method = RequestMethod.GET)
	public ModelAndView deleteFile(Model model, @RequestParam("id")Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		this.fileService.deleteFile(id);
		model.addAttribute("msgFileController","Fichier supprimé avec succès");
		
		return this.showFiles(model, httpServletRequest, httpServletResponse);		
	}
	
	@PreAuthorize("hasAuthority('PERM_DOWNLOAD_FILE')")
	@RequestMapping(value = ROUTE_DOWNLOAD_FILE, method = RequestMethod.GET)
	public void downloadFile(Model model, @RequestParam("id")Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		File fileDownload = this.fileService.findStoredOnServerById(id);
		
		if(!fileDownload.exists()){
			
			String errorMessage = "Sorry. The file you are looking for does not exist";
			logger.error("File with id " + id + " does not exist");
			OutputStream outputStream; 
			
			try{
				outputStream = httpServletResponse.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
			}catch(IOException e){
				logger.error("Exception raised:",e);
			}
			
		}else{
			
			String mimeType= URLConnection.guessContentTypeFromName(fileDownload.getName());
			
			if(mimeType==null){
				mimeType = "application/octet-stream";
			}

			logger.info("mimetype : "+mimeType);
			httpServletResponse.setContentType(mimeType);
			httpServletResponse.setHeader("Content-Disposition", "attachment;filename="+fileDownload.getName());

			httpServletResponse.setContentLength((int)fileDownload.length());
			
			InputStream inputStream;
			try {
				inputStream = new BufferedInputStream(new FileInputStream(fileDownload));
				FileCopyUtils.copy(inputStream, httpServletResponse.getOutputStream());	
			} catch (IOException e) {
				logger.error("Exception raised:",e);
			}
		}
	}
}

