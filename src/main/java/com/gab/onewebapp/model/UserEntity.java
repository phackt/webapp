package com.gab.onewebapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author gabriel
 * 
 */
@Entity
public class UserEntity {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false, unique = true, length = 255)
	private String login;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false)
	private boolean enabled = true;
	
	public UserEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}

	@Override
	public int hashCode() {
		return (int) (this.id * this.login.hashCode());
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
		UserEntity other = (UserEntity) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(this.getId(), other.getId())
			.append(this.getLogin(), other.getLogin())
			.isEquals();
	}
}

