package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import java.awt.*;

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
		setLayout(new GridBagLayout()); // 화면 크기 변경에도 중앙 정렬 유지

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel userIDLabel = new JLabel("ID");
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(userIDLabel, gbc);

		JTextField userIDText = new JTextField(15);
		gbc.gridx = 1;
		formPanel.add(userIDText, gbc);

		JLabel userPWLabel = new JLabel("Password");
		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(userPWLabel, gbc);

		JTextField userPWText = new JTextField(15); // 비밀번호는 JPasswordField로 바꿔도 됨
		gbc.gridx = 1;
		formPanel.add(userPWText, gbc);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		JButton userLoginButton = new JButton("User Login");
		JButton adminLoginButton = new JButton("Admin Login");
		buttonPanel.add(userLoginButton);
		buttonPanel.add(adminLoginButton);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
		formPanel.add(buttonPanel, gbc);

		// formPanel을 이 LoginPanel 중앙에 추가
		add(formPanel);

		userLoginButton.addActionListener(e -> {
			String userID = userIDText.getText();
			String password = userPWText.getText();
			loginController.handleLogin("user1", "user1", userID, password);
		});

		adminLoginButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "관리자 접속");
			loginController.handleLogin("root", "1234", null, null);
			coordinator.setUser("root");
			coordinator.showAdminInitView();
		});
	}
}