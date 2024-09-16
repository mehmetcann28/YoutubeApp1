package com.mehmetc.service;

import com.mehmetc.entity.Like;
import com.mehmetc.entity.enums.EReactionType;
import com.mehmetc.repository.LikeRepository;
import com.mehmetc.utility.ConsoleTextUtils;
import com.mehmetc.utility.ICRUDService;

import java.util.List;
import java.util.Optional;

public class LikeService implements ICRUDService<Like> {
	
	private static LikeService instance;
	private final LikeRepository likeRepository;
	
	public LikeService() {
		this.likeRepository = LikeRepository.getInstance();
	}
	
	public static synchronized LikeService getInstance(){
		if (instance == null){
			instance = new LikeService();
		}
		return instance;
	}
	
	@Override
	public Optional<Like> save(Like like) {
		Optional<Like> savedLike = likeRepository.save(like);
		ConsoleTextUtils.printResultMessage(savedLike.isPresent(), "Tepki başarıyla kaydedildi.", "Tepki kaydedilemedi.");
		return savedLike;
	}
	
	@Override
	public boolean delete(Long silinecekId) {
		boolean result = likeRepository.delete(silinecekId);
		ConsoleTextUtils.printResultMessage(result, "Tepki başarıyla silindi.", "Tepki silinemedi.");
		return result;
	}
	
	@Override
	public Optional<Like> update(Like like) {
		Optional<Like> updatedLike = likeRepository.update(like);
		ConsoleTextUtils.printResultMessage(updatedLike.isPresent(), "Tepki başarıyla güncellendi.", "Tepki güncellenemedi.");
		return updatedLike;
	}
	
	@Override
	public List<Like> findAll() {
		List<Like> likes = likeRepository.findAll();
		ConsoleTextUtils.printResultMessage(!likes.isEmpty(), "Toplam " + likes.size() + " tepki bulundu.", "Tepki bulunamadı.");
		return likes;
	}
	
	@Override
	public Optional<Like> findById(Long bulunacakId) {
		Optional<Like> like = likeRepository.findById(bulunacakId);
		ConsoleTextUtils.printResultMessage(like.isPresent(), "Tepki bulundu.", "Tepki bulunamadı.");
		return like;
	}
	
	public int countLikesByVideoId(Integer videoId) {
		int count = likeRepository.countLikesByVideoId(videoId);
		ConsoleTextUtils.printResultMessage(count > 0, "Videoya ait toplam " + count + " beğeni bulundu.", "Videoya ait beğeni bulunamadı.");
		return count;
	}
	
	public int countDislikesByVideoId(Integer videoId) {
		int count = likeRepository.countDislikesByVideoId(videoId);
		ConsoleTextUtils.printResultMessage(count > 0, "Videoya ait toplam " + count + " beğenmeme bulundu.", "Videoya ait beğenmeme bulunamadı.");
		return count;
	}
	
	public Optional<Like> findByUserIdAndVideoId(Long userId, Long videoId) {
		Optional<Like> like = likeRepository.findByUserIdAndVideoId(userId, videoId);
		ConsoleTextUtils.printResultMessage(like.isPresent(), "Kullanıcının videoya verdiği tepki bulundu.", "Kullanıcının videoya verdiği tepki bulunamadı.");
		return like;
	}
	
	public boolean removeReaction(Long userId, Long videoId, EReactionType reactionType) {
		boolean result = likeRepository.removeReaction(userId, videoId, reactionType);
		ConsoleTextUtils.printResultMessage(result, "Tepki başarıyla kaldırıldı.", "Tepki kaldırılırken hata oluştu.");
		return result;
	}
}