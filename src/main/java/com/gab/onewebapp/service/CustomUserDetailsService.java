package com.gab.onewebapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.model.UserEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	//TODO: ajouter ManyToOne FileEntity => UserEntity nullable = false
	//TODO: modifier les services
	//TODO: modifier les tests
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
    private UserService userService;
     
    @Transactional(readOnly=true)
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	logger.info("Looking for username: " + username);
        UserEntity user = userService.findByUsername(username);
        
        if(user==null){
            throw new UsernameNotFoundException("Username not found");
        }
        return user;
    }
}
