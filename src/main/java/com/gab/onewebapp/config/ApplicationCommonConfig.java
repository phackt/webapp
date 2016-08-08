package com.gab.onewebapp.config;

import java.io.File;

import javax.servlet.ServletContext;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public abstract class ApplicationCommonConfig {

	public static final Integer MAX_FILENAME_LENGTH = 255;
	
	@Autowired
	ServletContext servletContext;
	
	@NotNull
	@Value("${file.uploadDirPath}")
	private String uploadDirPath;
	
	@NotNull
	@Value("${application.currentAuthUserLogged}")
	private String currentAuthUserLogged;
	
	public String getUploadDirPath() {
		return servletContext.getRealPath("WEB-INF") + File.separator + uploadDirPath;
	}

	public Boolean isCurrentAuthUserLogged() {
		return Boolean.parseBoolean(this.currentAuthUserLogged);
	}

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

}
