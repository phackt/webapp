package com.gab.onewebapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author gabriel
 * 
 */
@Entity
@Table(name = "TUSER")
public class UserEntity {

	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="username", nullable = false, unique = true, length = 255)
	private String username;
	
	@Column(name="password", nullable = false, length = 255)
	private String password;
	
	@Column(name="enabled", nullable = false)
	private boolean enabled = true;
	
	@Column(name="locked", nullable = false)
	private boolean locked = false;
	
	@Column(name="expired", nullable = false)
	private boolean expired = false;
	
	@Column(name="credentials_expired", nullable = false)
	private boolean credentialsExpired = false;
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "TUSER_PROFILE", joinColumns = { 
			@JoinColumn(name = "user_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { 
			@JoinColumn(name = "profile_id", nullable = false, updatable = false) })
	private Set<UserProfileEntity> userProfiles = new HashSet<UserProfileEntity>();
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	private Set<FileEntity> files;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<UserProfileEntity> getUserProfiles() {
		return userProfiles;
	}

	public void setUserProfiles(Set<UserProfileEntity> userProfiles) {
		this.userProfiles = userProfiles;
	}

	public boolean isEnabled() {
		return this.enabled;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public boolean isAccountNonLocked() {
		return !this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isAccountNonExpired() {
		return !this.expired;
	}
	
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public boolean isCredentialsNonExpired() {
		return !this.credentialsExpired;
	}

	public Set<FileEntity> getFiles() {
		return files;
	}

	public void setFiles(Set<FileEntity> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}

	@Override
	public int hashCode() {
		return (int) (this.id * this.username.hashCode());
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		UserEntity other = (UserEntity) obj;
		return new EqualsBuilder()
			.append(this.getId(), other.getId())
			.append(this.getUsername(), other.getUsername())
			.isEquals();
	}
}

