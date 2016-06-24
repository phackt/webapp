package com.gab.onewebapp.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.model.UserEntity;

/**
 * @author gabriel
 * 
 */
@Repository
public class UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void UserDao(){	
	}
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<UserEntity> findAll(){
		return (List<UserEntity>)this.sessionFactory.getCurrentSession().createQuery("FROM UserEntity").list();
	}
	
	@Transactional
	public void delete(UserEntity u){
		this.sessionFactory.getCurrentSession().delete(u);
	}
	
	@Transactional
	public void saveOrUpdate(UserEntity u){
		this.sessionFactory.getCurrentSession().saveOrUpdate(u);
	}
	
	
	@Transactional(readOnly=true)
	public UserEntity findById(long id){
		return (UserEntity)this.sessionFactory.getCurrentSession().get(UserEntity.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public UserEntity findByUsername(String username){
		return (UserEntity)this.sessionFactory.getCurrentSession().createQuery("FROM UserEntity WHERE username=:username")
				.setParameter("username", username)
				.uniqueResult();
	}	
	
	@Transactional(readOnly=true)
	public long numberOfUsers(){
		return (Long)this.sessionFactory.getCurrentSession().createCriteria(UserEntity.class).setProjection(Projections.rowCount()).uniqueResult();
		
	}
}
