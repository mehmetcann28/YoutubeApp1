package com.mehmetc.entity.enums;

public enum EReactionType {
	LIKE("like"),
	DISLIKE("dislike");
	
	private final String value;
	
	EReactionType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}