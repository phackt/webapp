package com.gab.onewebapp.config;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;


@Component
@Configuration
@Profile(value={"default","dev"})
@PropertySource("classpath:config/application-dev.properties")
public class ApplicationConfig {

	public static final Integer MAX_FILENAME_LENGTH = 255;
	
	@NotNull
	@Value("${file.uploadDirPath}")
	private String uploadDirPath;

	public String getUploadDirPath() {
		return uploadDirPath;
	}

	public void setUploadDirPath(String uploadDirPath) {
		this.uploadDirPath = uploadDirPath;
	}
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
       return new PropertySourcesPlaceholderConfigurer();
    }

}
