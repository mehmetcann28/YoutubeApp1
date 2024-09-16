package com.mehmetc.entity;

public class Comment extends BaseEntity{
	private Long comment_id;
	private Integer video_id;
	private Integer user_id;
	private String comment_text;
	private Long parent_comment_id; // Yanıtlanan yorumun ID'si
	private Integer likes_count;
	private Integer dislikes_count;
	
	public Comment() {
	}
	
	public Comment(Integer video_id, Integer user_id, String comment_text, Long parent_comment_id, Integer likes_count,
	               Integer dislikes_count) {
		this.video_id = video_id;
		this.user_id = user_id;
		this.comment_text = comment_text;
		this.parent_comment_id = parent_comment_id;
		this.likes_count = likes_count;
		this.dislikes_count = dislikes_count;
	}
	
	public Comment(Long comment_id, Integer video_id, Integer user_id, String comment_text, Long parent_comment_id,
	               Integer likes_count, Integer dislikes_count) {
		super();
		this.comment_id = comment_id;
		this.video_id = video_id;
		this.user_id = user_id;
		this.comment_text = comment_text;
		this.parent_comment_id = parent_comment_id;
		this.likes_count = likes_count;
		this.dislikes_count = dislikes_count;
	}
	
	public Long getComment_id() {
		return comment_id;
	}
	
	public void setComment_id(Long comment_id) {
		this.comment_id = comment_id;
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
	
	public String getComment_text() {
		return comment_text;
	}
	
	public void setComment_text(String comment_text) {
		this.comment_text = comment_text;
	}
	
	public Long getParent_comment_id() {
		return parent_comment_id;
	}
	
	public void setParent_comment_id(Long parent_comment_id) {
		this.parent_comment_id = parent_comment_id;
	}
	
	public int getLikes_count() {
		return likes_count;
	}
	
	public void setLikes_count(Integer likes_count) {
		this.likes_count = likes_count;
	}
	
	public int getDislikes_count() {
		return dislikes_count;
	}
	
	public void setDislikes_count(Integer dislikes_count) {
		this.dislikes_count = dislikes_count;
	}
	
	@Override
	public String toString() {
		return "Comment{" + "comment_id=" + getComment_id() + ", video_id=" + getVideo_id() + ", user_id=" + getUser_id() + ", comment_text='" + getComment_text() + '\'' + ", parent_comment_id=" + getParent_comment_id() + ", likes_count=" + getLikes_count() + ", dislikes_count=" + getDislikes_count() + ", created_at=" + getCreated_at() + ", updated_at=" + getUpdated_at() + '}';
	}
}