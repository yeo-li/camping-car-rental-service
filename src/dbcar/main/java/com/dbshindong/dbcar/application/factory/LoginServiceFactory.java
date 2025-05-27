package dbcar.main.java.com.dbshindong.dbcar.application.factory;

import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

public class LoginServiceFactory {
	private AppConfig ac = AppConfig.getInstance();

	public LoginServiceFactory() {
	}

	public LoginService create(String DBId, String password) {
		System.out.println(DBId + password);
		ac.dbConnection().setConnection(DBId, password);
		return ac.loginService();

	}
}