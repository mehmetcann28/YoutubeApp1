package com.mehmetc.entity;

import com.mehmetc.entity.enums.EKategori;

import java.util.Arrays;

public class Video extends BaseEntity{
	private Integer video_id;
	private Integer user_id;
	private String title;
	private String description;
	private String video_url;
	private String thumbnail_url;
	private EKategori category;
	private String[] tags;
	private Integer views;
	private Integer likes_count;
	private int dislikes_count;
	
	public Video() {
	}
	
	public Video(Integer user_id, String title, String description, String video_url, String thumbnail_url, EKategori category, String[] tags, Integer views, Integer likes_count, Integer dislikes_count) {
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.video_url = video_url;
		this.thumbnail_url = thumbnail_url;
		this.category = category;
		this.tags = tags;
		this.views = views;
		this.likes_count = likes_count;
		this.dislikes_count = dislikes_count;
	}
	
	public Video(Integer video_id, Integer user_id, String title, String description, String video_url, String thumbnail_url, EKategori category, String[] tags, Integer views, Integer likes_count, Integer dislikes_count) {
		super();
		this.video_id = video_id;
		this.user_id = user_id;
		this.title = title;
		this.description = description;
		this.video_url = video_url;
		this.thumbnail_url = thumbnail_url;
		this.category = category;
		this.tags = tags;
		this.views = views;
		this.likes_count = likes_count;
		this.dislikes_count = dislikes_count;
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
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVideo_url() {
		return video_url;
	}
	
	public void setVideo_url(String video_url) {
		this.video_url = video_url;
	}
	
	public String getThumbnail_url() {
		return thumbnail_url;
	}
	
	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}
	
	public EKategori getCategory() {
		return category;
	}
	
	public void setCategory(EKategori category) {
		this.category = category;
	}
	
	public String[] getTags() {
		return tags;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	
	public Integer getViews() {
		return views;
	}
	
	public void setViews(Integer views) {
		this.views = views;
	}
	
	public Integer getLikes_count() {
		return likes_count;
	}
	
	public void setLikes_count(Integer likes_count) {
		this.likes_count = likes_count;
	}
	
	public Integer getDislikes_count() {
		return dislikes_count;
	}
	
	public void setDislikes_count(Integer dislikes_count) {
		this.dislikes_count = dislikes_count;
	}
	
	@Override
	public String toString() {
		return "Video{" + "video_id=" + getVideo_id() + ", user_id=" + getUser_id() + ", title='" + getTitle() + '\'' + ", description='" + getDescription() + '\'' + ", video_url='" + getVideo_url() + '\'' + ", thumbnail_url='" + getThumbnail_url() + '\'' + ", category=" + getCategory() + ", tags=" + Arrays.toString(getTags()) + ", views=" + getViews() + ", likes_count=" + getLikes_count() + ", dislikes_count=" + getDislikes_count() + ", created_at=" + getCreated_at() + ", updated_at=" + getUpdated_at() + '}';
	}
}