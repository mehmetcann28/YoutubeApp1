package com.mehmetc.entity;

import com.mehmetc.entity.enums.EStatus;

public class User extends BaseEntity{
	private Integer user_id;
	private String username;
	private String email;
	private String password;
	private String profile_image_url;
	private String bio;
	private EStatus status;
	
	public User() {
	}
	
	public User(String username, String email, String password, String profile_image_url, String bio, EStatus status) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.profile_image_url = profile_image_url;
		this.bio = bio;
		this.status = status;
	}
	
	public User(Integer user_id, String username, String email, String password, String profile_image_url, String bio,
	            EStatus status,Long created_at, Long updated_at) {
		super(created_at,updated_at);
		this.user_id = user_id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.profile_image_url = profile_image_url;
		this.bio = bio;
		this.status = status;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getProfile_image_url() {
		return profile_image_url;
	}
	
	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}
	
	public String getBio() {
		return bio;
	}
	
	public void setBio(String bio) {
		this.bio = bio;
	}
	
	public EStatus getStatus() {
		return status;
	}
	
	public void setStatus(EStatus status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "User{" + "user_id=" + getUser_id() + ", username='" + getUsername() + '\'' + ", email='" + getEmail() + '\'' + ", password='" + getPassword() + '\'' + ", profile_image_url='" + getProfile_image_url() + '\'' + ", bio='" + getBio() + '\'' + ", status=" + getStatus() + ", created_at=" + getCreated_at() + ", updated_at=" + getUpdated_at() + '}';
	}
}