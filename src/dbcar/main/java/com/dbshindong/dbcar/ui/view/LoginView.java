package dbcar.main.java.com.dbshindong.dbcar.ui.view;
import javax.swing.*;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.LoginController;

public class LoginView {
	private LoginController loginController;
	
	public LoginView(LoginController loginController) {
		this.loginController = loginController;
		createUI();
	}
	
	public void createUI() {
		JFrame frame = new JFrame("Login");//초기 화면
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();//화면에 패널 추가
		frame.add(panel);
		
		addLoginComponent(panel);
		
		frame.setVisible(true);
		
		
	}

	private void addLoginComponent(JPanel panel) {
		
		int presety = (600-145) / 2;
		int presetx = (800- 220) / 2 ;
		panel.setLayout(null);
		
		JLabel userIDLabel = new JLabel("ID");
		userIDLabel.setBounds(presetx, presety, 100, 25);
		panel.add(userIDLabel);
		
		JTextField userIDText= new JTextField(20);
		userIDText.setBounds(presetx + 60,presety, 160, 25);
		panel.add(userIDText);
		
		JLabel userPWLabel = new JLabel("Password");
		userPWLabel.setBounds(presetx, 40+ presety, 100, 25);
		panel.add(userPWLabel);
		
		JTextField userPWText= new JTextField(20);
		userPWText.setBounds(presetx + 60, 40+ presety, 160, 25);
		panel.add(userPWText);
		
		JButton userLoginButton = new JButton("User Login");
		userLoginButton.setBounds(presetx, 80+ presety, 105, 25);
		panel.add(userLoginButton);
		
		JButton adminLoginButton = new JButton("Admin Login");
		adminLoginButton.setBounds(presetx + 115, 80+ presety, 105, 25);
		panel.add(adminLoginButton);
		
		userLoginButton.addActionListener(e -> {
			String userID = userIDText.getText();
			String password = userPWText.getText();
			
			boolean success = loginController.handleLogin(userID, password);
			if(success) {
				JOptionPane.showMessageDialog(null, "로그인 성공!");
				SwingUtilities.getWindowAncestor(panel).dispose();
				new UserInitView();//중간 뷰이기 때문에 컨트롤러를 넣어줄 필요 없다.
			}
			else {
				JOptionPane.showMessageDialog(null, "등록되지 않은 ID나 잘못된 비밀번호를 입력하였습니다.");
			}
		});
		
		adminLoginButton.addActionListener(e -> {
			JOptionPane.showMessageDialog(null, "관리자 접속");
			SwingUtilities.getWindowAncestor(panel).dispose();
			new AdminInitView();//중간 뷰이기 때문에 컨트롤러를 넣어줄 필요 없다.
		});
		
	}

}
