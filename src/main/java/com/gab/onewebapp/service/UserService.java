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
    private UserDao dao;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    @Transactional
    public void saveOrUpdate(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.saveOrUpdate(user);
    }
    
    @Transactional(readOnly = true)
    public UserEntity findById(int id) {
        return dao.findById(id);
    }
 
    @Transactional(readOnly = true)
    public UserEntity findByUsername(String username) {
        return dao.findByUsername(username);
    }
}
