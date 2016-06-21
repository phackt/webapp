package com.gab.onewebapp.beans.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author gabriel
 * 
 */
public class FileUpload {
	
	@NotNull
	private MultipartFile file;
	
	@Size(max=200, message="Description trop longue.")
	private String description = "";

	public FileUpload(){
		
	}

	public FileUpload(MultipartFile file, String description) {
		this.file=file;
		this.description=description;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

