package dbcar.main.java.com.dbshindong.dbcar.ui.controller;

import javax.swing.JOptionPane;

import dbcar.main.java.com.dbshindong.dbcar.application.LoginService;
import dbcar.main.java.com.dbshindong.dbcar.application.factory.LoginServiceFactory;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class LoginController {

	private final LoginServiceFactory loginServiceFactory;
	private final AppCoordinator coordinator;

	public LoginController(LoginServiceFactory loginServiceFactory, AppCoordinator coordinator) {
		this.loginServiceFactory = loginServiceFactory;
		this.coordinator = coordinator;
	}

	public void handleLogin(String DBID, String DBPassword, String userId, String userPassword) {
		try {
			LoginService service = this.loginServiceFactory.create(DBID, DBPassword);
			if (DBID.equals("root")) {
				// this.coordinator.showAdminInitView();//중간 뷰이기 때문에 컨트롤러를 넣어줄 필요 없다.
			} else {
				if (service.login(userId, userPassword) == null) {
					JOptionPane.showMessageDialog(null, "잘못된 ID나 비밀번호를 입력하셨습니다.");
				} else {
					JOptionPane.showMessageDialog(null, "로그인 성공!");
					this.coordinator.setUser(userId);
					this.coordinator.showUserInitView();// 중간 뷰이기 때문에 컨트롤러를 넣어줄 필요 없다.

				}

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DB 연결 실패");
		}

	}

}
