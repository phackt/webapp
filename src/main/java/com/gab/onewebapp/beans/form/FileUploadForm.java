package com.gab.onewebapp.beans.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadForm {
	
	@NotNull
	private MultipartFile file;
	
	@Size(min=4,max=200, message="Description trop longue.")
	private String description;

	public FileUploadForm(){
		
	}

	public FileUploadForm(MultipartFile file, String description) {
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

