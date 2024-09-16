package com.mehmetc.repository;

import com.mehmetc.entity.Comment;
import com.mehmetc.utility.ConnectionProvider;
import com.mehmetc.utility.ICRUD;
import com.mehmetc.utility.SQLQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentRepository implements ICRUD<Comment> {
	
	private static CommentRepository instance;
	private String sql = "";
	private boolean success;
	private final ConnectionProvider connectionProvider;
	
	public CommentRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	public static synchronized CommentRepository getInstance(){
		if (instance == null){
			instance = new CommentRepository();
		}
		return instance;
	}
	
	@Override
	public Optional<Comment> save(Comment comment) {
		sql = SQLQueryBuilder.generateInsert(comment, "comments");
		success = connectionProvider.executeUpdate(sql);
		return success ? Optional.of(comment) : Optional.empty();
	}
	
	@Override
	public boolean delete(Long commentId) {
		sql = SQLQueryBuilder.generateDelete(Comment.class, "comments", commentId);
		return connectionProvider.executeUpdate(sql);
	}
	
	@Override
	public Optional<Comment> update(Comment comment) {
		sql = SQLQueryBuilder.generateUpdate(comment, "comments");
		success = connectionProvider.executeUpdate(sql);
		return success ? Optional.of(comment) : Optional.empty();
	}
	
	@Override
	public List<Comment> findAll() {
		sql = "SELECT * FROM comments ORDER BY comment_id";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Comment.class, "comments", rs)).orElseGet(ArrayList::new);
	}
	
	@Override
	public Optional<Comment> findById(Long commentId) {
		sql = "SELECT * FROM comments WHERE comment_id = " + commentId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.flatMap(rs -> SQLQueryBuilder.findById(Comment.class, "comments", commentId, rs));
	}
	
	public List<Comment> findByVideoId(Integer videoId) {
		sql = "SELECT * FROM comments WHERE video_id = " + videoId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Comment.class, "comments", rs)).orElseGet(ArrayList::new);
	}
	
	public int countCommentsByVideoId(Long videoId) {
		sql = "SELECT COUNT(*) FROM comments WHERE video_id = " + videoId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		try {
			if (resultSet.isPresent() && resultSet.get().next()) {
				return resultSet.get().getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("countCommentsByVideoId metodu çalışırken hata oluştu: " + e.getMessage());
		}
		return 0;
	}
	
	public List<Comment> findReplies(Long parentCommentId) {
		sql = "SELECT * FROM comments WHERE parent_comment_id = " + parentCommentId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(Comment.class, "comments", rs)).orElseGet(ArrayList::new);
	}
}