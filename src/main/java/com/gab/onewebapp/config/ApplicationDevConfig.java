package com.gab.onewebapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@Configuration
@Profile(value={"default","dev"})
@PropertySource("classpath:config/application-dev.properties")
public class ApplicationDevConfig extends ApplicationCommonConfig {

	//TODO:hibernate tools lancer les creation de table à partir des sql et insérer le user Admin
	//TODO:effectuer la hiérarchie des roles USER/ADMIN
	
//	@Autowired
//	DataSource dataSource;
//
//	@Bean
//	public JdbcTemplate getJdbcTemplate(){
//	  return new JdbcTemplate(dataSource);
//	}
//	
//	//default username : sa, password : ''
//	@PostConstruct
//	public void getDbManager(){
//	   DatabaseManagerSwing.main(
//		new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", ""});
//	}

}
