package com.gab.onewebapp.core.enums;


public enum UserProfileType {
    USER("USER"),
    ADMIN("ADMIN");
     
    private String userProfileType;
     
    private UserProfileType(String userProfileType){
        this.userProfileType = userProfileType;
    }
    
    @Override
	public String toString() {
		return userProfileType;	
	}
}
