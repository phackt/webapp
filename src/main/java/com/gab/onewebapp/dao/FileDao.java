package com.gab.onewebapp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.gab.onewebapp.model.FileEntity;

/**
 * @author gabriel
 * 
 */
@Repository
public class FileDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Criteria createFindAllByUserCriteria(String username){
		
		Criteria allFilesCriteria = this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class);
		
		if(!StringUtils.isEmpty(username)){
			allFilesCriteria.createAlias("user","u");
			allFilesCriteria.add(Restrictions.eq("u.username",username));
		}
		
		allFilesCriteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return allFilesCriteria;
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findAll(String username){
		return this.createFindAllByUserCriteria(username).list();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findAll(String username, int start, int maxElts){
		return this.createFindAllByUserCriteria(username)
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
	public List<FileEntity> findLike(String username, String originalFilename){
		return this.createFindAllByUserCriteria(username)
				.add(Restrictions.like("originalFilename","%" + originalFilename + "%"))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findByOriginalFilename(String username, String originalFilename){
		return this.createFindAllByUserCriteria(username)
				.add(Restrictions.eq("originalFilename",originalFilename))
				.list();
	}	
	
	@Transactional(readOnly=true)
	public long numberOfFiles(){
		return (Long)this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class).setProjection(Projections.rowCount()).uniqueResult();
		
	}

	@Transactional(readOnly=true)
	public Long getLastVersion(String username, String originalFilename) {
		return (Long)this.createFindAllByUserCriteria(username)
				.add(Restrictions.eq("originalFilename",originalFilename))
				.setProjection(Projections.max("version")).uniqueResult();
	}
	
}
