package com.gab.onewebapp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.model.FileEntity;
import com.gab.onewebapp.model.UserEntity;

/**
 * @author gabriel
 * 
 */
@Repository
public class FileDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void FileEntity(){	
	}
	
	private Criteria createFindAllByUserCriteria(UserEntity user){
		
		Criteria allFilesCriteria = this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class);
		
		if(user != null){
			allFilesCriteria.add(Restrictions.eq("user",user));
		}
		
		return allFilesCriteria;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findAll(UserEntity user){
		return this.createFindAllByUserCriteria(user).list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findAll(UserEntity user, int start, int maxElts){
		return this.createFindAllByUserCriteria(user)
				.setFirstResult(start)
				.setMaxResults(maxElts).list();
	}
	
	@Transactional
	public void delete(FileEntity f){
		this.sessionFactory.getCurrentSession().delete(f);
	}
	
	@Transactional
	public void saveOrUpdate(FileEntity f){
		this.sessionFactory.getCurrentSession().saveOrUpdate(f);
	}
		
	@Transactional(readOnly=true)
	public FileEntity findById(long id){
		return (FileEntity)this.sessionFactory.getCurrentSession().get(FileEntity.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findLike(UserEntity user, String originalFilename){
		return this.createFindAllByUserCriteria(user)
				.add(Restrictions.like("originalFilename","%" + originalFilename + "%"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findByOriginalFilename(UserEntity user, String originalFilename){
		return this.createFindAllByUserCriteria(user)
				.add(Restrictions.eq("originalFilename",originalFilename))
				.list();
	}	
	
	@Transactional(readOnly=true)
	public long numberOfFiles(){
		return (Long)this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class).setProjection(Projections.rowCount()).uniqueResult();
		
	}

	@Transactional(readOnly=true)
	public Long getLastVersion(UserEntity user, String originalFilename) {
		return (Long)this.createFindAllByUserCriteria(user)
				.add(Restrictions.eq("originalFilename",originalFilename))
				.setProjection(Projections.max("version")).uniqueResult();
	}
	
}
