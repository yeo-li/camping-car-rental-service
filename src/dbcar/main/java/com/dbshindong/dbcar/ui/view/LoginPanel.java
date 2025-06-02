package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;


import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.LoginController;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class LoginPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private LoginController loginController;
	private final AppCoordinator coordinator;

	public LoginPanel(LoginController loginController, AppCoordinator coordinator) {
		this.loginController = loginController;
		this.coordinator = coordinator;
		createUI();
	}

	public void createUI() {
		setLayout(null);
		addLoginComponent(this);
	}

	private void addLoginComponent(JPanel panel) {

		int presety = (600 - 145) / 2;
		int presetx = (800 - 220) / 2;
		panel.setLayout(null);

		JLabel userIDLabel = new JLabel("ID");
		userIDLabel.setBounds(presetx, presety, 100, 25);
		panel.add(userIDLabel);

		JTextField userIDText = new JTextField(20);
		userIDText.setBounds(presetx + 60, presety, 160, 25);
		panel.add(userIDText);

		JLabel userPWLabel = new JLabel("Password");
		userPWLabel.setBounds(presetx, 40 + presety, 100, 25);
		panel.add(userPWLabel);

		JTextField userPWText = new JTextField(20);
		userPWText.setBounds(presetx + 60, 40 + presety, 160, 25);
		panel.add(userPWText);

		JButton userLoginButton = new JButton("User Login");
		userLoginButton.setBounds(presetx, 80 + presety, 105, 25);
		panel.add(userLoginButton);

		JButton adminLoginButton = new JButton("Admin Login");
		adminLoginButton.setBounds(presetx + 115, 80 + presety, 105, 25);
		panel.add(adminLoginButton);

		userLoginButton.addActionListener(e -> {
			String userID = userIDText.getText();
			String password = userPWText.getText();
			try {
				loginController.handleLogin("user1", "user1", userID, password);// 추후 user1으로 수정 필요
			} catch(Exception ex) {
				GlobalExceptionHandler.handle(ex);
			}

		});

		adminLoginButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "관리자 접속");
			try {
			loginController.handleLogin("root", "1234", null, null);
        coordinator.setUser("root");
			coordinator.showAdminInitView();

			} catch (Exception ex) {
				GlobalExceptionHandler.handle(ex);
			}

		});

	}
	
	

}
