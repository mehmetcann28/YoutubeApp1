package com.mehmetc.menus;

import com.mehmetc.controller.UserController;
import com.mehmetc.entity.User;
import com.mehmetc.utility.ConsoleTextUtils;

import java.util.Optional;

public class LoginMenu {
	private final UserController userController;
	
	public LoginMenu() {
		this.userController = UserController.getInstance();
	}
	
	public void display() {
		while (true) {
			ConsoleTextUtils.printTitle("Youtube App");
			ConsoleTextUtils.printMenuOptions("Register Ol", "Giriş Yap", "Çıkış");
			
			int choice = ConsoleTextUtils.getIntUserInput("Seçiminiz:");
			
			switch (choice) {
				case 1:
					userController.register();
					break;
				case 2:
					Optional<User> loggedInUser = userController.login();
					if (loggedInUser.isPresent()) {
						new MainMenu(loggedInUser.get()).display(); // Başarılı girişte ana menüye geçiş
					}
					break;
				case 3:
					ConsoleTextUtils.printSuccessMessage("Çıkış yapılıyor...");
					return; // Ana menüden çık
				default:
					ConsoleTextUtils.printErrorMessage("Geçersiz seçim, lütfen tekrar deneyin.");
			}
		}
	}
}