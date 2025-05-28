package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

public class UserInitPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private AppConfig ac = AppConfig.getInstance();
	
	public UserInitPanel() {// UI 선택지 제공자이므로 controller/service가 필요 없다.
		createUI();
	}

	public void createUI() {
		setLayout(null);
		addUserInitComponent(this);
	}

	private void addUserInitComponent(JPanel panel) {

		int presety = (600 - 145) / 2;
		int presetx = (800 - 450) / 2;
		panel.setLayout(null);

		JLabel mainLabel = new JLabel("환영합니다!");
		mainLabel.setBounds(presetx + 75, presety, 550, 60);
		mainLabel.setFont(mainLabel.getFont().deriveFont(60f));

		panel.add(mainLabel);

		JButton campingcarLookupButton = new JButton("Lookup Campingcars");
		campingcarLookupButton.setBounds(presetx, 80 + presety, 200, 25);
		panel.add(campingcarLookupButton);

		JButton reservationLookupButton = new JButton("Lookup Your Reservations");
		reservationLookupButton.setBounds(presetx + 250, 80 + presety, 200, 25);
		panel.add(reservationLookupButton);

		
		campingcarLookupButton.addActionListener(e -> { 
			ac.appCoordinator().showCampingCarQueryView();
		});
	

	}

}
