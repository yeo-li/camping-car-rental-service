package ui.view;
import javax.swing.*;

public class LoginView {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Login");//초기 화면
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();//화면에 패널 추가
		frame.add(panel);
		
		addLoginComponent(panel);
		
		frame.setVisible(true);
		
		
	}

	private static void addLoginComponent(JPanel panel) {
		
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
		
	}

}
