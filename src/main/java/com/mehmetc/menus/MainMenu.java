package com.mehmetc.menus;

import com.mehmetc.controller.UserController;
import com.mehmetc.controller.VideoController;
import com.mehmetc.entity.User;
import com.mehmetc.utility.ConsoleTextUtils;

public class MainMenu {
	private final User currentUser;
	private final VideoController videoController;
	private final UserController userController;
	
	public MainMenu(User currentUser) {
		this.currentUser = currentUser;
		this.videoController = new VideoController(currentUser);
		this.userController = UserController.getInstance();
	}
	
	public void display() {
		while (true) {
			ConsoleTextUtils.printTitle("Hoşgeldiniz, " + currentUser.getUsername());
			ConsoleTextUtils.printMenuOptions("Ana Sayfa", "Video Paylaş", "Kendi Videolarını Görüntüle", "Kullanıcıları Listele", "Çıkış Yap");
			
			int choice = ConsoleTextUtils.getIntUserInput("Seçiminiz:");
			
			switch (choice) {
				case 1:
					videoController.showAllVideos();
					break;
				case 2:
					videoController.addVideo();
					break;
				case 3:
					videoController.showUserVideos();
					break;
				case 4:
					userController.listUsersWithVideoCount(videoController);
					break;
				case 5:
					ConsoleTextUtils.printSuccessMessage("Çıkış yapılıyor...");
					return; // Ana menüden çık
				default:
					ConsoleTextUtils.printErrorMessage("Geçersiz seçim, lütfen tekrar deneyin.");
			}
		}
	}
}