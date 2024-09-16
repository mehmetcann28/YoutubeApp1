package com.mehmetc.entity;

import com.mehmetc.entity.enums.EReactionType;

public class Like extends BaseEntity{
	private Long like_id;
	private Integer video_id;
	private Integer user_id;
	private EReactionType reaction_type;
	
	public Like() {
	}
	
	public Like(Integer video_id, Integer user_id, EReactionType reaction_type) {
		this.video_id = video_id;
		this.user_id = user_id;
		this.reaction_type = reaction_type;
	}
	
	public Like(Long like_id, Integer video_id, Integer user_id, EReactionType reaction_type) {
		super();
		this.like_id = like_id;
		this.video_id = video_id;
		this.user_id = user_id;
		this.reaction_type = reaction_type;
	}
	
	public Long getLike_id() {
		return like_id;
	}
	
	public void setLike_id(Long like_id) {
		this.like_id = like_id;
	}
	
	public Integer getVideo_id() {
		return video_id;
	}
	
	public void setVideo_id(Integer video_id) {
		this.video_id = video_id;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
	public EReactionType getReaction_type() {
		return reaction_type;
	}
	
	public void setReaction_type(EReactionType reaction_type) {
		this.reaction_type = reaction_type;
	}
	
	@Override
	public String toString() {
		return "Like{" + "like_id=" + getLike_id() + ", video_id=" + getVideo_id() + ", user_id=" + getUser_id() + ", reaction_type=" + getReaction_type() + ", created_at=" + getCreated_at() + ", updated_at=" + getUpdated_at() + '}';
	}
}