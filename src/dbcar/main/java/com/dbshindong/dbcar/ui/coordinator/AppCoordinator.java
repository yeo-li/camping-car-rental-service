package dbcar.main.java.com.dbshindong.dbcar.ui.coordinator;

import javax.swing.JFrame;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.*;

public class AppCoordinator {

	private final JFrame frame;
	private String user;
	private AppConfig ac = AppConfig.getInstance();

	public AppCoordinator() {
		this.frame = new JFrame("CampingCar App");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); // 중앙 정렬
	}

	public void start() {
		showLoginView();
		frame.setVisible(true);
	}
	
	public void setUser(String userID) {
		this.user = userID;
	}
	public String getUser() {
		return this.user;
	}

	public void showLoginView() {
		LoginPanel view = new LoginPanel(ac.loginController(), ac.appCoordinator());
		frame.setContentPane(view);
		frame.revalidate();
	}

	public void showUserInitView() {
		UserInitPanel view = new UserInitPanel();
		frame.setContentPane(view);
		frame.revalidate();
	}

	public void showAdminInitView() {
		AdminInitPanel view = new AdminInitPanel();
		frame.setContentPane(view);
		frame.revalidate();
	}

	
	public void showCampingCarQueryView() { 
		CampingCarQueryPanel view = new CampingCarQueryPanel();
		frame.setContentPane(view);
		frame.revalidate();
		
	}
	
	public void showCampingCarAvailableDateQueryView(int id) {
		CampingCarAvailableDateQueryPanel view = new CampingCarAvailableDateQueryPanel(id);
		frame.setContentPane(view);
		frame.revalidate();
	}
	
}
