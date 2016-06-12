package com.gab.onewebapp.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.gab.onewebapp.utils.DateUtils;

/**
 * @author gabriel
 * 
 */
@Entity
public class FileEntity{

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false, length = 255)
	private String name;
	
	@Column(length = 255)
	private String description;

	@Temporal(TemporalType.DATE)
	private Date dateUpload = new Date();

	@Column(nullable = false)
	private Integer version = 1;
	
	public FileEntity() {
	}

	public FileEntity(String name, String description){
		this.name = name;
		this.description = description;
	}
	
	public FileEntity(String name, String description, Date dateUpload){
		this(name, description);
		this.dateUpload = dateUpload;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "File [name=" + name + ", description=" + description + ", dateUpload=" + DateUtils.formatDate(dateUpload) + "]";
	}

	@Override
	public int hashCode() {
		return (int) (this.id * this.name.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileEntity other = (FileEntity) obj;
		if (id != other.id)
			return false;
		return true;
	}
}

