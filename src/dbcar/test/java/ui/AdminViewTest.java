package dbcar.test.java.ui;

import dbcar.main.java.com.dbshindong.dbcar.ui.view.AdminInitPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class AdminViewTest {
	public static void main(String[] args) {
		AppCoordinator coordinator = new AppCoordinator();
		coordinator.showAdminInitView();
	}
}
