package com.mehmetc.service;

import com.mehmetc.entity.Comment;
import com.mehmetc.repository.CommentRepository;
import com.mehmetc.utility.ConsoleTextUtils;
import com.mehmetc.utility.ICRUDService;

import java.util.List;
import java.util.Optional;

public class CommentService implements ICRUDService<Comment> {
	
	private static CommentService instance;
	private final CommentRepository commentRepository;
	
	public CommentService() {
		this.commentRepository = CommentRepository.getInstance();
	}
	
	public static synchronized CommentService getInstance(){
		if (instance == null){
			instance = new CommentService();
		}
		return instance;
	}
	
	@Override
	public Optional<Comment> save(Comment comment) {
		Optional<Comment> savedComment = commentRepository.save(comment);
		ConsoleTextUtils.printResultMessage(savedComment.isPresent(), "Yorum başarıyla kaydedildi.", "Yorum kaydedilemedi.");
		return savedComment;
	}
	
	@Override
	public boolean delete(Long silinecekId) {
		boolean result = commentRepository.delete(silinecekId);
		ConsoleTextUtils.printResultMessage(result, "Yorum başarıyla silindi.", "Yorum silinemedi.");
		return result;
	}
	
	@Override
	public Optional<Comment> update(Comment comment) {
		Optional<Comment> updatedComment = commentRepository.update(comment);
		ConsoleTextUtils.printResultMessage(updatedComment.isPresent(), "Yorum başarıyla güncellendi.", "Yorum güncellenemedi.");
		return updatedComment;
	}
	
	@Override
	public List<Comment> findAll() {
		List<Comment> comments = commentRepository.findAll();
		ConsoleTextUtils.printResultMessage(!comments.isEmpty(), "Toplam " + comments.size() + " yorum bulundu.", "Yorum bulunamadı.");
		return comments;
	}
	
	@Override
	public Optional<Comment> findById(Long bulunacakId) {
		Optional<Comment> comment = commentRepository.findById(bulunacakId);
		ConsoleTextUtils.printResultMessage(comment.isPresent(), "Yorum bulundu.", "Yorum bulunamadı.");
		return comment;
	}
	
	public List<Comment> findByVideoId(Integer videoId) {
		List<Comment> comments = commentRepository.findByVideoId(videoId);
		ConsoleTextUtils.printResultMessage(!comments.isEmpty(), "Videoya ait toplam " + comments.size() + " yorum bulundu.", "Videoya ait yorum bulunamadı.");
		return comments;
	}
	
	public int countCommentsByVideoId(Long videoId) {
		int count = commentRepository.countCommentsByVideoId(videoId);
		ConsoleTextUtils.printResultMessage(count > 0, "Videoya ait toplam " + count + " yorum bulundu.", "Videoya ait yorum bulunamadı.");
		return count;
	}
	
	public List<Comment> findReplies(Long parentCommentId) {
		List<Comment> replies = commentRepository.findReplies(parentCommentId);
		ConsoleTextUtils.printResultMessage(!replies.isEmpty(), "Yanıtlara ait toplam " + replies.size() + " yorum bulundu.", "Yanıtlar bulunamadı.");
		return replies;
	}
}