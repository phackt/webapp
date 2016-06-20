package com.gab.onewebapp.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gab.onewebapp.core.enums.PermissionType;

/**
 * @author gabriel
 * 
 */
@Entity
@Table(name = "TPERMISSION")
public class PermissionEntity {

	@Id
	@GeneratedValue
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="permission_type", nullable = false)
    private PermissionType permissionType;
	
	@ManyToMany(mappedBy = "permissions")
	private Set<UserProfileEntity> userProfileEntities = new HashSet<UserProfileEntity>();
		
	public PermissionEntity() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionType permissionType) {
		this.permissionType = permissionType;
	}

	public Set<UserProfileEntity> getUserProfileEntities() {
		return userProfileEntities;
	}

	public void setUserProfileEntities(Set<UserProfileEntity> userProfileEntities) {
		this.userProfileEntities = userProfileEntities;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);	
	}

	@Override
	public int hashCode() {
		return (int) (this.id * this.permissionType.toString().hashCode());
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) { return false; }
		if (obj == this) { return true; }
		if (obj.getClass() != this.getClass()) {
			return false;
		}
		PermissionEntity other = (PermissionEntity) obj;
		return new EqualsBuilder()
			.appendSuper(super.equals(obj))
			.append(this.getId(), other.getId())
			.append(this.permissionType.toString(), other.getPermissionType().toString())
			.isEquals();
	}
}

