package com.mehmetc.controller;

import com.mehmetc.entity.Comment;
import com.mehmetc.entity.Like;
import com.mehmetc.entity.User;
import com.mehmetc.entity.Video;
import com.mehmetc.entity.enums.EKategori;
import com.mehmetc.entity.enums.EReactionType;
import com.mehmetc.service.CommentService;
import com.mehmetc.service.LikeService;
import com.mehmetc.service.VideoService;
import com.mehmetc.utility.ConsoleTextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VideoController {
	
	private final VideoService videoService;
	private final LikeService likeService;
	private final CommentService commentService;
	private final User currentUser;
	
	public VideoController(User currentUser) {
		this.videoService = VideoService.getInstance();
		this.likeService = LikeService.getInstance();
		this.commentService = CommentService.getInstance();
		this.currentUser = currentUser;
	}
	
	public void showAllVideos() {
		List<Video> videos = videoService.findAll();
		if (videos.isEmpty()) {
			ConsoleTextUtils.printErrorMessage("Gösterilecek video bulunamadı.");
			return;
		}
		
		for (Video video : videos) {
			displayVideoDetails(video);
		}
	}
	
	public void showUserVideos() {
		List<Video> videos = videoService.findByUserId(currentUser.getUser_id());
		if (videos.isEmpty()) {
			ConsoleTextUtils.printErrorMessage("Kullanıcıya ait video bulunamadı.");
			return;
		}
		
		for (Video video : videos) {
			displayVideoDetails(video);
		}
	}
	
	public void addVideo() {
		ConsoleTextUtils.printTitle("Yeni Video Ekle");
		String title = ConsoleTextUtils.getStringUserInput("Video Başlığı:");
		String description = ConsoleTextUtils.getStringUserInput("Video Açıklaması:");
		String videoUrl = ConsoleTextUtils.getStringUserInput("Video URL:");
		String thumbnailUrl = ConsoleTextUtils.getStringUserInput("Küçük Resim URL:");
		
		// EKategori enum'undan sayısal seçim yaptırma
		ConsoleTextUtils.printTitle("Kategori Seçimi");
		EKategori[] categories = EKategori.values();
		for (int i = 0; i < categories.length; i++) {
			System.out.println((i + 1) + "- " + categories[i].name());
		}
		int categoryChoice = ConsoleTextUtils.getIntUserInput("Kategori numarasını seçiniz:");
		EKategori selectedCategory = categories[categoryChoice - 1];  // Seçimi enum ile eşleştir
		
		// Etiketleri ekleme
		ConsoleTextUtils.printTitle("Etiketler Ekle");
		List<String> tags = new ArrayList<>();
		while (true) {
			String tag = ConsoleTextUtils.getStringUserInput("Etiket girin (Çıkmak için boş bırakın):");
			if (tag.isEmpty()) {
				break;
			}
			tags.add(tag);
		}
		
		// SQL Sorgusunda kullanılmak üzere tags dizisini uygun formatta hazırlama
		String tagsArraySQL = tags.stream()
		                          .map(tag -> "'" + tag.replace("'", "''") + "'") // Tek tırnakları escape et
		                          .collect(Collectors.joining(", ", "ARRAY[", "]"));
		
		// Video nesnesi oluşturma
		Video video = new Video(null, currentUser.getUser_id(), title, description, videoUrl, thumbnailUrl, selectedCategory, tags.toArray(new String[0]), 0, 0, 0);
		
		Optional<Video> savedVideo = videoService.save(video);
		
		if (savedVideo.isPresent()) {
			ConsoleTextUtils.printSuccessMessage("Video başarıyla eklendi.");
		} else {
			ConsoleTextUtils.printErrorMessage("Video eklenirken bir hata oluştu.");
		}
	}
	
	public void displayVideoDetails(Video video) {
		ConsoleTextUtils.printTitle(video.getTitle());
		System.out.println("Açıklama: " + video.getDescription());
		System.out.println("Beğeniler: " + likeService.countLikesByVideoId(video.getVideo_id()));
		System.out.println("Beğenmeme: " + likeService.countDislikesByVideoId(video.getVideo_id()));
		System.out.println("İzlenme: " + video.getViews());
		
		// Videoya ait yorumları gösterme
		List<Comment> comments = commentService.findByVideoId(video.getVideo_id());
		if (!comments.isEmpty()) {
			ConsoleTextUtils.printTitle("Yorumlar");
			for (Comment comment : comments) {
				System.out.println(comment.getComment_text() + " - Kullanıcı ID: " + comment.getUser_id());
			}
		} else {
			ConsoleTextUtils.printErrorMessage("Bu video için yorum bulunamadı.");
		}
		
		ConsoleTextUtils.printMenuOptions("Beğeni Durumunuzu Belirtin", "Yorum Yap", "Geri Dön");
		int choice = ConsoleTextUtils.getIntUserInput("Seçiminiz:");
		
		switch (choice) {
			case 1:
				handleLike(video.getVideo_id());
				break;
			case 2:
				handleComment(video.getVideo_id());
				break;
			case 3:
				return;
			default:
				ConsoleTextUtils.printErrorMessage("Geçersiz seçim, lütfen tekrar deneyin.");
		}
	}
	
	private void handleLike(Integer videoId) {
		ConsoleTextUtils.printTitle("Beğeni Durumunuzu Belirtin");
		ConsoleTextUtils.printMenuOptions("Beğen (1)", "Beğenme (2)", "Geri Dön (3)");
		
		int reactionChoice = ConsoleTextUtils.getIntUserInput("Seçiminiz:");
		
		switch (reactionChoice) {
			case 1:
				saveLike(videoId, EReactionType.LIKE);
				break;
			case 2:
				saveLike(videoId, EReactionType.DISLIKE);
				break;
			case 3:
				return;
			default:
				ConsoleTextUtils.printErrorMessage("Geçersiz seçim, lütfen tekrar deneyin.");
		}
	}
	
	private void saveLike(Integer videoId, EReactionType reactionType) {
		Like like = new Like(currentUser.getUser_id(), videoId, reactionType);
		Optional<Like> savedLike = likeService.save(like);
		
		if (savedLike.isPresent()) {
			ConsoleTextUtils.printSuccessMessage("Tepki başarıyla kaydedildi.");
		} else {
			ConsoleTextUtils.printErrorMessage("Tepki kaydedilemedi.");
		}
	}
	
	
	private void handleComment(Integer videoId) {
		ConsoleTextUtils.printTitle("Yorum Yap");
		String commentText = ConsoleTextUtils.getStringUserInput("Yorumunuzu giriniz:");
		Comment comment = new Comment(videoId, currentUser.getUser_id(), commentText, null, 0, 0);
		Optional<Comment> savedComment = commentService.save(comment);
		
		if (savedComment.isPresent()) {
			ConsoleTextUtils.printSuccessMessage("Yorum başarıyla eklendi.");
		} else {
			ConsoleTextUtils.printErrorMessage("Yorum kaydedilemedi.");
		}
	}
	
	public int getUserVideoCount(Integer userId) {
		return videoService.findByUserId(userId).size();
	}
}