package com.mehmetc.repository;

import com.mehmetc.entity.Like;
import com.mehmetc.entity.enums.EReactionType;
import com.mehmetc.utility.ConnectionProvider;
import com.mehmetc.utility.ConsoleTextUtils;
import com.mehmetc.utility.ICRUD;
import com.mehmetc.utility.SQLQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LikeRepository implements ICRUD<Like> {
	
	private static LikeRepository instance;
	private String sql = "";
	private boolean success;
	private final ConnectionProvider connectionProvider;
	
	public LikeRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	public static synchronized LikeRepository getInstance(){
		if (instance == null){
			instance = new LikeRepository();
		}
		return instance;
	}
	
	@Override
	public Optional<Like> save(Like like) {
		if (like.getUser_id() == null || like.getVideo_id() == null) {
			ConsoleTextUtils.printErrorMessage("User ID ve Video ID boş olamaz.");
			return Optional.empty();
		}
		
		sql = SQLQueryBuilder.generateInsert(like, "likes");
		
		// SQL sorgusunu kontrol için konsola yazdırın
		System.out.println("Generated SQL: " + sql);
		
		success = connectionProvider.executeUpdate(sql);
		
		return success ? Optional.of(like) : Optional.empty();
	}
	
	@Override
	public boolean delete(Long likeId) {
		sql = SQLQueryBuilder.generateDelete(Like.class, "likes", likeId);
		return connectionProvider.executeUpdate(sql);
	}
	
	@Override
	public Optional<Like> update(Like like) {
		sql = SQLQueryBuilder.generateUpdate(like, "likes");
		success = connectionProvider.executeUpdate(sql);
		return success ? Optional.of(like) : Optional.empty();
	}
	
	@Override
	public List<Like> findAll() {
		sql = "SELECT * FROM likes ORDER BY like_id";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Like.class, "likes", rs)).orElseGet(ArrayList::new);
	}
	
	@Override
	public Optional<Like> findById(Long likeId) {
		sql = "SELECT * FROM likes WHERE like_id = " + likeId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.flatMap(rs -> SQLQueryBuilder.findById(Like.class, "likes", likeId, rs));
	}
	
	public int countLikesByVideoId(Integer videoId) {
		sql = "SELECT COUNT(*) FROM likes WHERE video_id = " + videoId + " AND reaction_type = 'like'";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		try {
			if (resultSet.isPresent() && resultSet.get().next()) {
				return resultSet.get().getInt(1);
			}
		}
		catch (SQLException e) {
			System.out.println("countLikesByVideoId metodu çalışırken hata oluştu: " + e.getMessage());
		}
		return 0;
	}
	
	public int countDislikesByVideoId(Integer videoId) {
		sql = "SELECT COUNT(*) FROM likes WHERE video_id = " + videoId + " AND reaction_type = 'dislike'";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		try {
			if (resultSet.isPresent() && resultSet.get().next()) {
				return resultSet.get().getInt(1);
			}
		}
		catch (SQLException e) {
			System.out.println("countDislikesByVideoId metodu çalışırken hata oluştu: " + e.getMessage());
		}
		return 0;
	}
	
	public Optional<Like> findByUserIdAndVideoId(Long userId, Long videoId) {
		sql = "SELECT * FROM likes WHERE user_id = " + userId + " AND video_id = " + videoId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.flatMap(rs -> SQLQueryBuilder.findById(Like.class, "likes", videoId, rs));
	}
	
	public boolean removeReaction(Long userId, Long videoId, EReactionType reactionType) {
		// Enum'un ismini kullanarak reaction_type değerini String'e çeviriyoruz.
		String sql = "DELETE FROM likes WHERE user_id = " + userId +
				" AND video_id = " + videoId +
				" AND reaction_type = '" + reactionType.name() + "'";
		return connectionProvider.executeUpdate(sql);
	}
}