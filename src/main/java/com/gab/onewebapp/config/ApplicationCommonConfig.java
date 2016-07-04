package com.gab.onewebapp.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


//TODO: variabiliser les fichiers log4j en fonction de l'environnement
//ne pas mettre les variables log4j dans ApplicationCommonConfig
public abstract class ApplicationCommonConfig {

	public static final Integer MAX_FILENAME_LENGTH = 255;
	
	@NotNull
	@Value("${file.uploadDirPath}")
	private String uploadDirPath;
	
	@NotNull
	@Value("${application.currentAuthUserLogged}")
	private String currentAuthUserLogged;
	
	public String getUploadDirPath() {
		return uploadDirPath;
	}

	public Boolean isCurrentAuthUserLogged() {
		return Boolean.parseBoolean(this.currentAuthUserLogged);
	}

	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

}
