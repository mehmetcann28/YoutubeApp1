package com.mehmetc.controller;

import com.mehmetc.entity.User;
import com.mehmetc.menus.LoginMenu;
import com.mehmetc.menus.MainMenu;
import com.mehmetc.utility.ConsoleTextUtils;

import java.util.Optional;

public class MainController {
	
	private static MainController instance;
	private final UserController userController;
	
	public MainController() {
		this.userController = UserController.getInstance();
	}
	
	public static synchronized MainController getInstance(){
		if (instance == null){
			instance = new MainController();
		}
		return instance;
	}
	
	public void start() {
		new LoginMenu().display();
	}
	
	public void homePage(User currentUser) {
		new MainMenu(currentUser).display();
	}
}