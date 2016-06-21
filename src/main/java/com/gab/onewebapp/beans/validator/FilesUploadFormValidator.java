package com.gab.onewebapp.beans.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.gab.onewebapp.beans.form.FileUpload;
import com.gab.onewebapp.beans.form.FilesUploadForm;

@Component
public class FilesUploadFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
        return FilesUploadForm.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		FilesUploadForm filesUploadForm = (FilesUploadForm)target;
		
		boolean allFilesEmpty=true;
		for(FileUpload fileUpload : filesUploadForm.getFilesUploaded()){
			if(!StringUtils.isEmpty(fileUpload.getFile().getOriginalFilename())){
				allFilesEmpty=false;
				break;
			}
		}
		
		if(allFilesEmpty)errors.rejectValue("filesUploaded","filesUploaded.empty","At least one file should be uploaded!");
	}

}
