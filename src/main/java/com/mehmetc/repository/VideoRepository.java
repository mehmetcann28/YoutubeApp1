package com.mehmetc.repository;

import com.mehmetc.entity.Video;
import com.mehmetc.entity.enums.EKategori;
import com.mehmetc.utility.ConnectionProvider;
import com.mehmetc.utility.ICRUD;
import com.mehmetc.utility.SQLQueryBuilder;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class VideoRepository implements ICRUD<Video> {
	
	private static VideoRepository instance;
	private String sql = "";
	private boolean success;
	private final ConnectionProvider connectionProvider;
	
	public VideoRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	public static synchronized VideoRepository getInstance(){
		if (instance == null){
			instance = new VideoRepository();
		}
		return instance;
	}
	
	@Override
	public Optional<Video> save(Video video) {
		return insertVideo(video);
	}
	
	private Optional<Video> insertVideo(Video video) {
		// SQL sorgusu oluşturma
		String tagsArraySQL = Arrays.stream(video.getTags())
		                            .map(tag -> "'" + tag.replace("'", "''") + "'")
		                            .collect(Collectors.joining(", ", "ARRAY[", "]"));
		
		sql = "INSERT INTO videos (user_id, title, description, video_url, thumbnail_url, category, tags, views, likes_count, dislikes_count) " +
				"VALUES (" +
				video.getUser_id() + ", " +
				"'" + video.getTitle().replace("'", "''") + "', " +
				"'" + video.getDescription().replace("'", "''") + "', " +
				"'" + video.getVideo_url().replace("'", "''") + "', " +
				"'" + video.getThumbnail_url().replace("'", "''") + "', " +
				"'" + video.getCategory().name() + "', " +
				tagsArraySQL + ", " + // Dizi burada doğru formatta SQL'e ekleniyor
				video.getViews() + ", " +
				video.getLikes_count() + ", " +
				video.getDislikes_count() +
				")";
		
		// SQL'i çalıştırma
		success = connectionProvider.executeUpdate(sql);
		
		return success ? Optional.of(video) : Optional.empty();
	}
	
	@Override
	public boolean delete(Long silinecekId) {
		sql = SQLQueryBuilder.generateDelete(Video.class, "videos", silinecekId);
		return connectionProvider.executeUpdate(sql);
	}
	
	@Override
	public Optional<Video> update(Video video) {
		sql = SQLQueryBuilder.generateUpdate(video, "videos");
		success = connectionProvider.executeUpdate(sql);
		return success ? Optional.of(video) : Optional.empty();
	}
	
	@Override
	public List<Video> findAll() {
		sql = "SELECT * FROM videos ORDER BY video_id";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Video.class, "videos", rs)).orElseGet(ArrayList::new);
	}
	
	@Override
	public Optional<Video> findById(Long videoId) {
		sql = "SELECT * FROM videos WHERE video_id = " + videoId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.flatMap(rs -> SQLQueryBuilder.findById(Video.class, "videos", videoId, rs));
	}
	
	public List<Video> findByCategory(EKategori kategori) {
		sql = "SELECT * FROM videos WHERE category = '" + kategori.name() + "'";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Video.class, "videos", rs)).orElseGet(ArrayList::new);
	}
	
	public List<Video> findByUserId(Integer userId) {
		sql = "SELECT * FROM videos WHERE user_id = " + userId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Video.class, "videos", rs)).orElseGet(ArrayList::new);
	}
	
	public List<Video> findMostLikedVideos(int limit) {
		sql = "SELECT * FROM videos ORDER BY likes_count DESC LIMIT " + limit;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Video.class, "videos", rs)).orElseGet(ArrayList::new);
	}
	
	public boolean incrementViews(Long videoId) {
		sql = "UPDATE videos SET views = views + 1 WHERE video_id = " + videoId;
		return connectionProvider.executeUpdate(sql);
	}
}