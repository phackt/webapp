package com.gab.onewebapp.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.config.ApplicationCommonConfig;
import com.gab.onewebapp.model.PermissionEntity;
import com.gab.onewebapp.model.UserEntity;
import com.gab.onewebapp.model.UserProfileEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
    private UserService userService;
	
	@Autowired
	private MessageSource messageSource;
     
	@Autowired
	private HttpServletRequest httpServletRequest;
	
	@Autowired
	private ApplicationCommonConfig appConfig;
	
    @Transactional(readOnly=true)
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	logger.info("---- Authentication access detected ----");
    	
    	//Logging client information if enabled
    	if(this.appConfig.isCurrentAuthUserLogged()){
    		
    		logger.info("remote ip adress: " + httpServletRequest.getRemoteAddr());
    		logger.info("remote user agent: " + httpServletRequest.getHeader("User-Agent"));
    		logger.info("remote referer: " + httpServletRequest.getHeader("Referer"));
    		logger.info("requested uri: " + httpServletRequest.getRequestURI());
    	}

    	logger.info("Looking for auth username: " + username);
        UserEntity user = userService.findByUsername(username);
        
        if(user==null){
            throw new UsernameNotFoundException(this.messageSource.getMessage("customUserDetailsService.error.user_not_found", null, LocaleContextHolder.getLocale()));
        }
        return new User(user.getUsername(),user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), this.getAuthorities(user));
    }
    
	private Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        //Add Roles
        for(UserProfileEntity userProfile : user.getUserProfiles()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getUserProfileType()));
            
            //Add Permission as Roles - for a best granularity check Spring sample project Contacts with ACL on each object
            for(PermissionEntity permissionEntity : userProfile.getPermissions()){
            	authorities.add(new SimpleGrantedAuthority(permissionEntity.getPermissionType().toString()));
            }
        }     
        
        return authorities;
	}
}
