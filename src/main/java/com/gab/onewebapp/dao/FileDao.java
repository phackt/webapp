package com.gab.onewebapp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gab.onewebapp.model.FileEntity;

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
		
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findAll(){
		return (List<FileEntity>)this.sessionFactory.getCurrentSession().createQuery("From FileEntity").list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findAll(int start, int maxElts){
		Query q = this.sessionFactory
				.getCurrentSession().createQuery("FROM FileEntity")
				.setFirstResult(start)
				.setMaxResults(maxElts);
		return q.list();
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
	public List<FileEntity> findLike(String originalFilename){
		return (List<FileEntity>)this.sessionFactory.getCurrentSession().createQuery("FROM FileEntity WHERE originalFilename LIKE :originalFilename")
				.setParameter("originalFilename", "%" + originalFilename + "%")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findByOriginalFilename(String originalFilename){
		return (List<FileEntity>)this.sessionFactory.getCurrentSession().createQuery("FROM FileEntity WHERE originalFilename=:originalFilename")
				.setParameter("originalFilename", originalFilename)
				.list();
	}	
	
	@Transactional(readOnly=true)
	public long numberOfFiles(){
		return (Long)this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class).setProjection(Projections.rowCount()).uniqueResult();
		
	}

	@Transactional(readOnly=true)
	public Long getLastVersion(String originalFilename) {
		return (Long)this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class).add(Restrictions.eq("originalFilename",originalFilename)).setProjection(Projections.max("version")).uniqueResult();

	}
	
}
