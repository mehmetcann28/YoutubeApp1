package com.mehmetc.entity;

public abstract class BaseEntity {
	private Long created_at;
	private Long updated_at;
	
	public BaseEntity() {
		long now = System.currentTimeMillis() / 1000; // Epoch zamanında al (saniye cinsinden)
		this.created_at = now;
		this.updated_at = now;
	}
	
	public BaseEntity(Long created_at, Long updated_at) {
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
	public Long getCreated_at() {
		return created_at;
	}
	
	public void setCreated_at(Long created_at) {
		this.created_at = created_at;
	}
	
	public Long getUpdated_at() {
		return updated_at;
	}
	
	public void setUpdated_at(Long updated_at) {
		this.updated_at = updated_at;
	}
}