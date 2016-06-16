package com.gab.onewebapp.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;



public abstract class ApplicationCommonConfig {

	public static final Integer MAX_FILENAME_LENGTH = 255;
	
	@NotNull
	@Value("${file.uploadDirPath}")
	private String uploadDirPath;

	@NotNull
	@Value("${application.logPath}")
	private String appLogPath;
	
	public String getUploadDirPath() {
		return uploadDirPath;
	}

	public void setUploadDirPath(String uploadDirPath) {
		this.uploadDirPath = uploadDirPath;
	}
	
	public String getAppLogPath() {
		return appLogPath;
	}

	public void setAppLogPath(String appLogPath) {
		this.appLogPath = appLogPath;
	}

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

}
