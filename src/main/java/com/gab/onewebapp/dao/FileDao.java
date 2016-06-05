package com.gab.onewebapp.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
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
	public FileEntity find(long id){
		return (FileEntity)this.sessionFactory.getCurrentSession().get(FileEntity.class, id);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> findLike(String name){
		return (List<FileEntity>)this.sessionFactory.getCurrentSession().createQuery("FROM FileEntity WHERE name LIKE :name")
				.setParameter("name", "%" + name + "%")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	public List<FileEntity> find(String name){
		return (List<FileEntity>)this.sessionFactory.getCurrentSession().createQuery("FROM FileEntity WHERE name=:name")
				.setParameter("name", name)
				.list();
	}	
	
	@Transactional(readOnly=true)
	public long numberOfFiles(){
		return (Long)this.sessionFactory.getCurrentSession().createCriteria(FileEntity.class).setProjection(Projections.rowCount()).uniqueResult();
		
	}
	
}
