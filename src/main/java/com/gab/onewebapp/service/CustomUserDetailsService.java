package com.gab.onewebapp.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.model.PermissionEntity;
import com.gab.onewebapp.model.UserEntity;
import com.gab.onewebapp.model.UserProfileEntity;

@Service
public class CustomUserDetailsService implements UserDetailsService {

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
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), true, true, true, getGrantedAuthorities(user));
    }
 
     
    private List<GrantedAuthority> getGrantedAuthorities(UserEntity user){
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
