package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AdminInitPanel extends JPanel{
	//private UserInitController UserInitController;
	
			//public UserInitView(UserInitController UserInitController) {
			public AdminInitPanel() {
				//this.UserInitController = UserInitController;
				createUI();
			}
			
			public void createUI() {
				JFrame frame = new JFrame("환영합니다!");//초기 화면
				frame.setSize(800,600);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				JPanel panel = new JPanel();//화면에 패널 추가
				frame.add(panel);
				
				addUserInitComponent(panel);
				
				frame.setVisible(true);
				
				
			}

			private void addUserInitComponent(JPanel panel) {
				
				int presety = (600-145) / 2;
				int presetx = (800- 450) / 2 ;
				panel.setLayout(null);
				
				JLabel mainLabel = new JLabel("관리자 모드");
				mainLabel.setBounds(presetx+75, presety, 550, 60);
				mainLabel.setFont(mainLabel.getFont().deriveFont(60f));
				
				panel.add(mainLabel);
				
				
				JButton campingcarLookupButton = new JButton("Query/Delete/Modify DB");
				campingcarLookupButton.setBounds(presetx, 80+ presety,  200, 25);
				panel.add(campingcarLookupButton);
				
				JButton reservationLookupButton = new JButton("Lookup Car Repair Log");
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
