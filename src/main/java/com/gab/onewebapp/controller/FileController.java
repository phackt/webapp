package com.gab.onewebapp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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

import com.gab.onewebapp.beans.form.FileUpload;
import com.gab.onewebapp.beans.form.FilesUploadForm;
import com.gab.onewebapp.beans.validator.FilesUploadFormValidator;
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
	
	@Autowired
	public FilesUploadFormValidator filesUploadFormValidator;
	
	@Autowired
	private MessageSource messageSource;
	
	@PreAuthorize("hasAuthority('PERM_UPLOAD_FILE')")
	@RequestMapping(value = ROUTE_UPLOAD_FILE, method = RequestMethod.POST)
	public ModelAndView uploadFile(Model model, 
			@Valid @ModelAttribute("filesUploadForm") FilesUploadForm filesUploadForm,
			BindingResult result, 
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {

		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		//La validation vérifie qu'il y a à minima un fichier à uploader parmi tous les input file
		this.filesUploadFormValidator.validate(filesUploadForm, result);
		
		if(!result.hasErrors()){
			
			List<String> msgListFileController = new ArrayList<String>();
			model.addAttribute("msgListFileController",msgListFileController);
			
			try {
				
				//On boucle sur tous les fichiers uploadés
				for(FileUpload fileUpload : filesUploadForm.getFilesUploaded())
				{
					MultipartFile file = fileUpload.getFile();
					
					//On vérifie que l'input file ne soit pas vide
					if(!StringUtils.isEmpty(file.getOriginalFilename())){
						fileService.saveOrUpdate(file.getBytes(), file.getOriginalFilename(),fileUpload.getDescription());
						
						msgListFileController = (ArrayList<String>)model.asMap().get("msgListFileController");
						msgListFileController.add(this.messageSource.getMessage("fileController.label.upload_success", new Object[] {file.getOriginalFilename()},LocaleContextHolder.getLocale()));
						model.addAttribute("msgListFileController",msgListFileController);
					}
				}
				
			} catch (IOException e) {
				logger.error("Exception raised:",e);
				result.rejectValue("filesUploaded", "fileController.exception.upload_file");
			}
			
		}
		
		return this.showFiles(model, httpServletRequest, httpServletResponse);		
	}	
	
	@RequestMapping(value = ROUTE_SHOW_FILES, method = RequestMethod.GET)
	public ModelAndView showFiles(Model model, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		ModelAndView modelAndView = new ModelAndView(VIEW_SHOW_FILES);

		modelAndView.addObject("listFiles", this.fileService.findAllFromCurrentUser());
		
		//On ajoute le formulaire d'envoi de fichiers si besoin
		if (!model.containsAttribute("filesUploadForm"))
			model.addAttribute("filesUploadForm", new FilesUploadForm());
		
		return modelAndView;	
	}
	
	@PreAuthorize("hasAuthority('PERM_DELETE_FILE')")
	@RequestMapping(value = ROUTE_DELETE_FILE, method = RequestMethod.GET)
	public ModelAndView deleteFile(Model model, @RequestParam("id")Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		String originalFilename = this.fileService.findById(id).getOriginalFilename();
		this.fileService.deleteFile(id);
		List<String> msgListFileController = new ArrayList<String>();
		
		msgListFileController.add(this.messageSource.getMessage("fileController.label.delete_success", new Object[] {originalFilename},LocaleContextHolder.getLocale()));
		model.addAttribute("msgListFileController",msgListFileController);
		
		return this.showFiles(model, httpServletRequest, httpServletResponse);		
	}
	
	@PreAuthorize("hasAuthority('PERM_DOWNLOAD_FILE')")
	@RequestMapping(value = ROUTE_DOWNLOAD_FILE, method = RequestMethod.GET)
	public void downloadFile(Model model, @RequestParam("id")Long id, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		
		logger.info("calling url " + httpServletRequest.getRequestURL().toString());
		
		File fileDownload = this.fileService.findStoredOnServerById(id);
		
		if(!fileDownload.exists()){
			
			
			String errorMessage = this.messageSource.getMessage("fileController.label.download_error", null, LocaleContextHolder.getLocale());
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

