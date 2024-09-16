package com.mehmetc.controller;

import com.mehmetc.entity.User;
import com.mehmetc.service.UserService;
import com.mehmetc.utility.ConsoleTextUtils;

import java.util.List;
import java.util.Optional;

public class UserController {
	
	private static UserController instance;
	private final UserService userService;
	private User currentUser;
	
	public UserController() {
		this.userService = UserService.getInstance();
		
	}
	
	public static synchronized UserController getInstance() {
		if (instance == null) {
			instance = new UserController();
		}
		return instance;
	}
	
	public void register() {
		ConsoleTextUtils.printTitle("Register");
		String username = ConsoleTextUtils.getStringUserInput("Kullanıcı adınızı giriniz:");
		String email = ConsoleTextUtils.getStringUserInput("Email adresinizi giriniz:");
		String password = ConsoleTextUtils.getStringUserInput("Şifrenizi giriniz:");
		String profileImageUrl = ConsoleTextUtils.getStringUserInput("Profil resminiz için URL giriniz:");
		String bio = ConsoleTextUtils.getStringUserInput("Bio metninizi giriniz:");
		
		User user = new User(null, username, email, password, profileImageUrl, bio, null, null, null);
		userService.save(user);
	}
	
	public Optional<User> login() {
		ConsoleTextUtils.printTitle("Login");
		
		String username = ConsoleTextUtils.getStringUserInput("Kullanıcı adınızı giriniz:");
		String password = ConsoleTextUtils.getStringUserInput("Şifrenizi giriniz:");
		
		Optional<User> loggedInUser = userService.login(username, password);
		ConsoleTextUtils.printResultMessage(loggedInUser.isPresent(), "Giriş Başarılı...",
		                                    "Kullanıcı adı veya şifre " + "hatalı.");
		return loggedInUser;
	}
	
	public void listUsersWithVideoCount(VideoController videoController) {
		List<User> users = userService.findAll();
		for (User user : users) {
			int videoCount = videoController.getUserVideoCount(user.getUser_id());
			System.out.println("Kullanıcı adı: " + user.getUsername() + " | Video Sayısı: " + videoCount);
		}
	}
	
	public void showAllUsers() {
		List<User> users = userService.findAll();
		if (users.isEmpty()) {
			ConsoleTextUtils.printErrorMessage("Kayıtlı kullanıcı bulunamadı.");
		}
		else {
			for (User user : users) {
				System.out.println("Kullanıcı adı: " + user.getUsername() + ", Email: " + user.getEmail() + ", Durum: "
						                   + user.getStatus());
			}
		}
	}
	
	
}