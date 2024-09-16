package com.mehmetc.repository;

import com.mehmetc.entity.User;
import com.mehmetc.entity.enums.EStatus;
import com.mehmetc.utility.ConnectionProvider;
import com.mehmetc.utility.ICRUD;
import com.mehmetc.utility.SQLQueryBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements ICRUD<User> {
	
	private static UserRepository instance;
	private String sql = "";
	private boolean success;
	private final ConnectionProvider connectionProvider;
	
	public UserRepository() {
		this.connectionProvider = ConnectionProvider.getInstance();
	}
	
	public static synchronized UserRepository getInstance(){
		if (instance == null){
			instance = new UserRepository();
		}
		return instance;
	}
	
	@Override
	public Optional<User> save(User user) {
		sql = SQLQueryBuilder.generateInsert(user, "users");
		success = connectionProvider.executeUpdate(sql);
		return success ? Optional.of(user) : Optional.empty();
	}
	
	@Override
	public boolean delete(Long silinecekId) {
		sql = SQLQueryBuilder.generateDelete(User.class, "users", silinecekId);
		return connectionProvider.executeUpdate(sql);
	}
	
	@Override
	public Optional<User> update(User user) {
		sql = SQLQueryBuilder.generateUpdate(user, "users");
		success = connectionProvider.executeUpdate(sql);
		return success ? Optional.of(user) : Optional.empty();
	}
	
	@Override
	public List<User> findAll() {
		sql = "SELECT * FROM users ORDER BY user_id";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.map(rs -> SQLQueryBuilder.generateList(User.class, "users", rs)).orElseGet(ArrayList::new);
	}
	
	@Override
	public Optional<User> findById(Long userId) {
		sql = "SELECT * FROM users WHERE user_id = " + userId;
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		return resultSet.flatMap(rs -> SQLQueryBuilder.findById(User.class, "users", userId, rs));
	}
	
	public boolean existsByUsername(String username) {
		sql = "SELECT * FROM users WHERE username = '" + username + "'";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		if (resultSet.isPresent()) {
			try {
				return resultSet.get().next();
			}
			catch (SQLException e) {
				System.out.println("existsByUsername metodu çalışırken hata oluştu: " + e.getMessage());
			}
		}
		return false;
	}
	
	public boolean existsByEmail(String email) {
		sql = "SELECT * FROM users WHERE email = '" + email + "'";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		if (resultSet.isPresent()) {
			try {
				return resultSet.get().next();
			}
			catch (SQLException e) {
				System.out.println("existsByEmail metodu çalışırken hata oluştu: " + e.getMessage());
			}
		}
		return false;
	}
	
	public Optional<User> doLogin(String username, String password) {
		sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";
		Optional<ResultSet> resultSet = connectionProvider.executeQuery(sql);
		ResultSet rs = null;
		try {
			if (resultSet.isPresent()) {
				rs = resultSet.get();
				if (rs.next()) {
					// Enum değerini kontrol etmeden önce null veya boş olup olmadığını kontrol edin.
					String statusString = rs.getString("status");
					EStatus status = (statusString != null && !statusString.isEmpty()) ?
							EStatus.valueOf(statusString.toUpperCase()) : EStatus.ACTIVE; // Varsayılan olarak ACTIVE veriyoruz.
					
					User user = new User(
							rs.getInt("user_id"),
							rs.getString("username"),
							rs.getString("email"),
							rs.getString("password"),
							rs.getString("profile_image_url"),
							rs.getString("bio"),
							status,
							rs.getLong("created_at"),
							rs.getLong("updated_at")
					);
					return Optional.of(user);
				}
			}
		} catch (SQLException e) {
			System.out.println("doLogin metodu çalışırken hata oluştu: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			System.out.println("Enum dönüşüm hatası: " + e.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					System.out.println("ResultSet kapatma hatası: " + e.getMessage());
				}
			}
			connectionProvider.closeConnection(); // Bağlantıyı manuel kapatmak.
		}
		return Optional.empty();
	}
}