package com.gab.onewebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.dao.UserDao;
import com.gab.onewebapp.model.UserEntity;

@Service
public class UserService {

	@Autowired
    private UserDao userDao;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Transactional
    public void saveOrUpdate(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.saveOrUpdate(user);
    }
 
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }
    
	@Transactional
	public void deleteUser(Long id) {
		UserEntity fileToDelete = this.userDao.findById(id);
		this.userDao.delete(fileToDelete);
	}
		
	@Transactional(readOnly=true)
	public UserEntity findById(long id){
		return this.userDao.findById(id); 
	}
    
}
