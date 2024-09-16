package com.mehmetc.service;

import com.mehmetc.entity.User;
import com.mehmetc.repository.UserRepository;
import com.mehmetc.utility.ConsoleTextUtils;
import com.mehmetc.utility.ICRUDService;

import java.util.List;
import java.util.Optional;

public class UserService implements ICRUDService<User> {
	
	private static UserService instance;
	private final UserRepository userRepository;
	
	public UserService() {
		this.userRepository = UserRepository.getInstance();
	}
	
	public static synchronized UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
		}
		return instance;
	}
	
	@Override
	public Optional<User> save(User user) {
		handleUniqueConstraints(user);
		Optional<User> savedUser = userRepository.save(user);
		ConsoleTextUtils.printResultMessage(savedUser.isPresent(), "Kullanıcı başarılı bir şekilde kaydedildi.", "Kayıt işlemi başarısız oldu.");
		return savedUser;
	}
	
	@Override
	public boolean delete(Long silinecekId) {
		boolean result = userRepository.delete(silinecekId);
		ConsoleTextUtils.printResultMessage(result, "Kullanıcı başarıyla silindi.", "Kullanıcı silinemedi.");
		return result;
	}
	
	@Override
	public Optional<User> update(User user) {
		Optional<User> updatedUser = userRepository.update(user);
		ConsoleTextUtils.printResultMessage(updatedUser.isPresent(), "Kullanıcı başarıyla güncellendi.", "Kullanıcı güncellenemedi.");
		return updatedUser;
	}
	
	@Override
	public List<User> findAll() {
		List<User> users = userRepository.findAll();
		ConsoleTextUtils.printResultMessage(!users.isEmpty(), "Toplam " + users.size() + " kullanıcı bulundu.", "Kullanıcı bulunamadı.");
		return users;
	}
	
	@Override
	public Optional<User> findById(Long bulunacakId) {
		Optional<User> user = userRepository.findById(bulunacakId);
		ConsoleTextUtils.printResultMessage(user.isPresent(), "Kullanıcı bulundu.", "Kullanıcı bulunamadı.");
		return user;
	}
	
	public Optional<User> login(String username, String password) {
		Optional<User> user = userRepository.doLogin(username, password);
		ConsoleTextUtils.printResultMessage(user.isPresent(), "Giriş başarılı.", "Kullanıcı adı veya şifre hatalı.");
		return user;
	}
	
	// Kullanıcı adı ve email eşsizliği kontrolü ve uygun kullanıcı girdisi
	private void handleUniqueConstraints(User user) {
		while (userRepository.existsByUsername(user.getUsername())) {
			user.setUsername(ConsoleTextUtils.getStringUserInput("Lütfen başka bir kullanıcı adı giriniz:"));
		}
		
		while (userRepository.existsByEmail(user.getEmail())) {
			user.setEmail(ConsoleTextUtils.getStringUserInput("Lütfen başka bir email giriniz:"));
		}
	}
}