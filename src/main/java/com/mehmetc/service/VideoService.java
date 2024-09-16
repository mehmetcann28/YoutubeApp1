package com.mehmetc.service;

import com.mehmetc.entity.Video;
import com.mehmetc.entity.enums.EKategori;
import com.mehmetc.repository.VideoRepository;
import com.mehmetc.utility.ConsoleTextUtils;
import com.mehmetc.utility.ICRUDService;
import java.util.List;
import java.util.Optional;


public class VideoService implements ICRUDService<Video> {
	
	private static VideoService instance;
	private final VideoRepository videoRepository;
	
	public VideoService() {
		this.videoRepository = VideoRepository.getInstance();
	}
	
	public static synchronized VideoService getInstance(){
		if (instance == null){
			instance = new VideoService();
		}
		return instance;
	}
	
	@Override
	public Optional<Video> save(Video video) {
		Optional<Video> savedVideo = videoRepository.save(video);
		ConsoleTextUtils.printResultMessage(savedVideo.isPresent(), "Video başarıyla kaydedildi.", "Video kaydedilemedi.");
		return savedVideo;
	}
	
	@Override
	public boolean delete(Long silinecekId) {
		boolean result = videoRepository.delete(silinecekId);
		ConsoleTextUtils.printResultMessage(result, "Video başarıyla silindi.", "Video silinemedi.");
		return result;
	}
	
	@Override
	public Optional<Video> update(Video video) {
		Optional<Video> updatedVideo = videoRepository.update(video);
		ConsoleTextUtils.printResultMessage(updatedVideo.isPresent(), "Video başarıyla güncellendi.", "Video güncellenemedi.");
		return updatedVideo;
	}
	
	@Override
	public List<Video> findAll() {
		List<Video> videos = videoRepository.findAll();
		ConsoleTextUtils.printResultMessage(!videos.isEmpty(), "Toplam " + videos.size() + " video bulundu.", "Video bulunamadı.");
		return videos;
	}
	
	@Override
	public Optional<Video> findById(Long bulunacakId) {
		Optional<Video> video = videoRepository.findById(bulunacakId);
		ConsoleTextUtils.printResultMessage(video.isPresent(), "Video bulundu.", "Video bulunamadı.");
		return video;
	}
	
	public List<Video> findByCategory(EKategori kategori) {
		List<Video> videos = videoRepository.findByCategory(kategori);
		ConsoleTextUtils.printResultMessage(!videos.isEmpty(), "Kategoriye ait toplam " + videos.size() + " video bulundu.", "Kategoriye ait video bulunamadı.");
		return videos;
	}
	
	public List<Video> findByUserId(Integer userId) {
		List<Video> videos = videoRepository.findByUserId(userId);
		ConsoleTextUtils.printResultMessage(!videos.isEmpty(), "Kullanıcıya ait toplam " + videos.size() + " video bulundu.", "Kullanıcıya ait video bulunamadı.");
		return videos;
	}
	
	public List<Video> findMostLikedVideos(int limit) {
		List<Video> videos = videoRepository.findMostLikedVideos(limit);
		ConsoleTextUtils.printResultMessage(!videos.isEmpty(), "En çok beğenilen toplam " + videos.size() + " video bulundu.", "En çok beğenilen video bulunamadı.");
		return videos;
	}
	
	public boolean incrementViews(Long videoId) {
		boolean result = videoRepository.incrementViews(videoId);
		ConsoleTextUtils.printResultMessage(result, "Video izlenme sayısı başarıyla artırıldı.", "İzlenme sayısı artırılırken hata oluştu.");
		return result;
	}
}