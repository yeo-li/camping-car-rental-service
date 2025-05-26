package dbcar.main.java.com.dbshindong.dbcar.ui.coordinator;

import java.sql.Connection;

import javax.swing.JFrame;

import dbcar.main.java.com.dbshindong.dbcar.application.*;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.DBConnection;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.company.CampingCarRepository;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.*;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.*;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.*;
public class AppCoordinator {
	
	private final JFrame frame;
	private String user;
	private Connection conn;
	
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
	public Connection getConnection() {
		return this.conn;
	}
    public void showLoginView() {
    	LoginController loginController = new LoginController(this);
        LoginPanel view = new LoginPanel(loginController, this);
        frame.setContentPane(view);
        frame.revalidate();
    }
    
    public void showUserInitView() {
    	UserInitPanel view = new UserInitPanel();
    	frame.setContentPane(view);
        frame.revalidate();
    }
    /*public void showAdminInitView() {
    	AdminInitPanel view = new UserInitPanel();
    	frame.setContentPane(view);
        frame.revalidate();
    }
    */
    /*public void showCampingCarQueryView(Customer user) {
    	CampingCarRepository campingCarRepository = new CampingCarRepository(this.conn); 
        CampingCarQueryService service = new CampingCarQueryService(campingCarRepository);
        CampingCarQueryController controller = new CampingCarQueryController(service);
        CampingCarQueryPanel view = new CampingCarQueryPanel(user, controller, this);

        frame.setContentPane(view);
        frame.revalidate();
    }*/
}
