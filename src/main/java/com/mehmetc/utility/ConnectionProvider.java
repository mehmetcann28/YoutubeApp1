package com.mehmetc.utility;

import java.sql.*;
import java.util.Optional;

import static com.mehmetc.utility.Constants.*;

public class ConnectionProvider {
	private static ConnectionProvider instance;
	private Connection conn;
	
	public ConnectionProvider() {
	}
	
	public static synchronized ConnectionProvider getInstance() {
		if (instance == null) {
			instance = new ConnectionProvider();
		}
		return instance;
	}
	
	private boolean openConnection() {
		if (conn != null) {
			return true; // Connection already opened
		}
		try {
			conn = DriverManager.getConnection("jdbc:postgresql://" + DB_HOSTNAME + ":" + DB_PORT + "/" + DB_NAME, DB_USERNAME, DB_PASSWORD);
			return true;
		} catch (SQLException e) {
			System.out.println("Bağlantı Hatası... " + e.getMessage());
			return false;
		}
	}
	
	public void closeConnection() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("Bağlantı kapatma Hatası... " + e.getMessage());
		} finally {
			conn = null;
		}
	}
	
	public boolean executeUpdate(String sql) {
		if (openConnection()) {
			try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
				preparedStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				System.out.println("Sorgu çalıştırmada hata: " + e.getMessage());
				return false;
			} finally {
				closeConnection(); // Ensure connection is closed after execution
			}
		} else {
			System.out.println("Bağlantı açmada hata meydana geldi.");
			return false;
		}
	}
	
	public Optional<ResultSet> executeQuery(String sql) {
		if (openConnection()) {
			try {
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
				ResultSet resultSet = preparedStatement.executeQuery();
				return Optional.ofNullable(resultSet);
			} catch (SQLException e) {
				System.out.println("Sorgu çalıştırmada hata: " + e.getMessage());
				return Optional.empty();
			}
		} else {
			System.out.println("Bağlantı açmada hata meydana geldi.");
			return Optional.empty();
		}
	}
	
	// Yeni metod: ResultSet işlemleri bittikten sonra çağrılmalı
	public void closeResources(ResultSet resultSet, PreparedStatement preparedStatement) {
		try {
			if (resultSet != null && !resultSet.isClosed()) {
				resultSet.close();
			}
			if (preparedStatement != null && !preparedStatement.isClosed()) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.out.println("Kaynak kapatma hatası: " + e.getMessage());
		} finally {
			closeConnection();
		}
	}
}