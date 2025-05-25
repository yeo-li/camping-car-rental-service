package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;

public class LoginController {
	private final LoginService loginService;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public boolean handleLogin(String userID, String password) {
		return loginService.login(userID, password);
	}
	
}
