package com.gab.onewebapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author gabriel
 * 
 */
@Entity
public class FileEntity {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false, length = 255)
	private String originalFilename;
	
	@Column(nullable = false, length = 255)
	private String storedFilename;
	
	@Column(length = 255)
	private String description;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dateUpload = new Date();

	@Column(nullable = false)
	private long version = 1;
	
	public FileEntity() {
	}

	public FileEntity(String originalFilename, String storedFilename, String description){
		this.originalFilename = originalFilename;
		this.storedFilename = storedFilename;
		this.description = description;
	}
	
	public FileEntity(String originalFilename, String storedFilename, String description, Date dateUpload){
		this(originalFilename, storedFilename, description);
		this.dateUpload = dateUpload;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public String getStoredFilename() {
		return storedFilename;
	}

	public void setStoredFilename(String storedFilename) {
		this.storedFilename = storedFilename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDateUpload() {
		return dateUpload;
	}

	public void setDateUpload(Date dateUpload) {
		this.dateUpload = dateUpload;
	}
		
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}

	@Override
	public int hashCode() {
		return (int) (this.id * this.originalFilename.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		/*
		 * Une solution serait d'utiliser la réflection avec la lib apache mais + lent
		 * et il n'est pas nécessaire de tester tous les champs, à minima ceux utilisé
		 * dans le hashCode
		 * 
		 * return EqualsBuilder.reflectionEquals(this, obj);
		 */
		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		FileEntity other = (FileEntity) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(this.getId(), other.getId())
			.append(this.getOriginalFilename(), other.getOriginalFilename())
			.isEquals();
	}
}

