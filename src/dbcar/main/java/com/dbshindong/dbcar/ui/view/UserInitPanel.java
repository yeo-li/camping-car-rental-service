package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;

public class UserInitPanel extends JPanel {
	
		public UserInitPanel() {//UI 선택지 제공자이므로 controller/service가 필요 없다.
		
			createUI();
		}
		
		public void createUI() {
			
			setLayout(null);
			
			addUserInitComponent(this);
			
			
			
		}

		private void addUserInitComponent(JPanel panel) {
			
			int presety = (600-145) / 2;
			int presetx = (800- 450) / 2 ;
			panel.setLayout(null);
			
			JLabel mainLabel = new JLabel("환영합니다!");
			mainLabel.setBounds(presetx+75, presety, 550, 60);
			mainLabel.setFont(mainLabel.getFont().deriveFont(60f));
			
			panel.add(mainLabel);
			
			
			JButton campingcarLookupButton = new JButton("Lookup Campingcars");
			campingcarLookupButton.setBounds(presetx, 80+ presety,  200, 25);
			panel.add(campingcarLookupButton);
			
			JButton reservationLookupButton = new JButton("Lookup Your Reservations");
			reservationLookupButton.setBounds(presetx + 250, 80+ presety, 200, 25);
			panel.add(reservationLookupButton);
			
			/*userLoginButton.addActionListener(e -> {
				String userID = userIDText.getText();
				String password = userPWText.getText();
				
				boolean success = loginController.handleLogin(userID, password);
				if(success) {//다른 뷰 생성 이후 연결 예정
					JOptionPane.showMessageDialog(null, "로그인 성공!");
				}
				else {
					JOptionPane.showMessageDialog(null, "등록되지 않은 ID나 잘못된 비밀번호를 입력하였습니다.");
				}
			});*/
			
		}

}
