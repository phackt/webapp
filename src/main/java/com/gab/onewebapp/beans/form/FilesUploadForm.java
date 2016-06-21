package com.gab.onewebapp.beans.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author gabriel
 * 
 */
public class FilesUploadForm {
	
	@Valid
	@NotNull
	@Size(min = 1, message="Vous devez choisir au moins un fichier!")
	private List<FileUpload> filesUploaded;

	public List<FileUpload> getFilesUploaded() {
		return filesUploaded;
	}

	public void setFilesUploaded(List<FileUpload> filesUploaded) {
		this.filesUploaded = filesUploaded;
	}
	
}

