package com.gab.onewebapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gab.onewebapp.dao.UserDao;
import com.gab.onewebapp.model.UserEntity;

@Service
public class UserService {

	@Autowired
    private UserDao dao;
     
    @Autowired
    private PasswordEncoder passwordEncoder;
 
     
    public void saveOrUpdate(UserEntity user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.saveOrUpdate(user);
    }
     
    public UserEntity findById(int id) {
        return dao.findById(id);
    }
 
    public UserEntity findByLogin(String sso) {
        return dao.findByLogin(sso);
    }
}
