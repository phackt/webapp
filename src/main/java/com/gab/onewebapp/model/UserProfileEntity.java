package com.gab.onewebapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gab.onewebapp.core.enums.UserProfileType;

/**
 * @author gabriel
 * 
 */
@Entity
@Table(name = "TPROFILE")
public class UserProfileEntity {

	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="user_profile_type", nullable = false)
    private UserProfileType userProfileType;
	
	@ManyToMany(mappedBy = "userProfiles")
	private Set<UserEntity> userEntities = new HashSet<UserEntity>();
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "TPROFILE_PERMISSION", joinColumns = { 
			@JoinColumn(name = "profile_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { 
			@JoinColumn(name = "permission_id", nullable = false, updatable = false) })
	private Set<PermissionEntity> permissions = new HashSet<PermissionEntity>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserProfileType getUserProfileType() {
		return userProfileType;
	}

	public void setUserProfileType(UserProfileType userProfileType) {
		this.userProfileType = userProfileType;
	}

	public Set<UserEntity> getUserEntities() {
		return userEntities;
	}

	public void setUserEntities(Set<UserEntity> userEntities) {
		this.userEntities = userEntities;
	}

    
	public Set<PermissionEntity> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<PermissionEntity> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}

	@Override
	public int hashCode() {
		return (int) (this.id * this.userProfileType.toString().hashCode());
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		UserProfileEntity other = (UserProfileEntity) obj;
		return new EqualsBuilder()
			.append(this.getId(), other.getId())
			.append(this.userProfileType.toString(), other.getUserProfileType().toString())
			.isEquals();
	}
}

