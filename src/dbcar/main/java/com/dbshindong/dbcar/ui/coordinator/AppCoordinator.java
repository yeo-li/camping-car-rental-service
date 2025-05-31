package dbcar.main.java.com.dbshindong.dbcar.ui.coordinator;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.*;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.CampingCarCompanyInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.CampingCarInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.CustomerInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.EmployeeInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.ExternalRepairRecordInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.ExternalRepairShopInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.InternalRepairRecordInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.PartInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.RentalInsertPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert.TableEntrySelectorPanel;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.admin.SqlQueryPanel;

public class AppCoordinator {

	private final JFrame frame;
	private String user = null;
	private AppConfig ac = AppConfig.getInstance();
	private JPanel tableContentPanel;

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
		AdminInitPanel panel = new AdminInitPanel();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buildTopBar(), BorderLayout.NORTH);
		mainPanel.add(panel, BorderLayout.CENTER);

		frame.setContentPane(mainPanel);
		frame.revalidate();
	}

	public void showAllTableView() {
		AllTableViewerPanel panel = new AllTableViewerPanel();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buildTopBar(), BorderLayout.NORTH);
		mainPanel.add(panel, BorderLayout.CENTER);

		frame.setContentPane(mainPanel);
		frame.revalidate();
	}

	public void showRepairRecordView() {
		RepairRecordPanel panel = new RepairRecordPanel();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buildTopBar(), BorderLayout.NORTH);
		mainPanel.add(panel, BorderLayout.CENTER);

		frame.setContentPane(mainPanel);
		frame.revalidate();
	}

	public void showTableEntrySelectorView() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel topWrapperPanel = new JPanel(new BorderLayout());
		topWrapperPanel.add(buildTopBar(), BorderLayout.NORTH);
		topWrapperPanel.add(new TableEntrySelectorPanel(this::handleTableSelection), BorderLayout.SOUTH);

		mainPanel.add(topWrapperPanel, BorderLayout.NORTH);

		JPanel contentPanel = new JPanel(new CardLayout());
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		this.tableContentPanel = contentPanel;

		frame.setContentPane(mainPanel);
		frame.revalidate();
	}

	private void handleTableSelection(String tableName) {
		switch (tableName) {
		case "CampingCarCompany" -> showCampingCarCompanyInsertPanel();
		case "CampingCar" -> showCampingCarInsertPanel();
		case "Customer" -> showCustomerInsertPanel();
		case "Employee" -> showEmployeeInsertPanel();
		case "ExternalRepairRecord" -> showExternalRepairRecordInsertPanel();
		case "ExternalRepairShop" -> showExternalRepairShopInsertPanel();
		case "InternalRepairRecord" -> showInternalRepairRecordInsertPanel();
		case "Part" -> showPartInsertPanel();
		case "Rental" -> showRentalInsertPanel();
		}
	}

	private void showCustomerInsertPanel() {
		CustomerInsertPanel customerPanel = new CustomerInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(customerPanel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showCampingCarCompanyInsertPanel() {
		CampingCarCompanyInsertPanel panel = new CampingCarCompanyInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showCampingCarInsertPanel() {
		CampingCarInsertPanel panel = new CampingCarInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showEmployeeInsertPanel() {
		EmployeeInsertPanel panel = new EmployeeInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showExternalRepairRecordInsertPanel() {
		ExternalRepairRecordInsertPanel panel = new ExternalRepairRecordInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showExternalRepairShopInsertPanel() {
		ExternalRepairShopInsertPanel panel = new ExternalRepairShopInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showInternalRepairRecordInsertPanel() {
		InternalRepairRecordInsertPanel panel = new InternalRepairRecordInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showPartInsertPanel() {
		PartInsertPanel panel = new PartInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	private void showRentalInsertPanel() {
		RentalInsertPanel panel = new RentalInsertPanel();

		tableContentPanel.removeAll(); // 기존 하단 패널 제거
		tableContentPanel.add(panel); // 새로운 패널 추가
		tableContentPanel.revalidate(); // 레이아웃 재계산
		tableContentPanel.repaint(); // 화면 다시 그리기
	}

	public void clearContentPanel() {
		tableContentPanel.removeAll();
		tableContentPanel.revalidate();
		tableContentPanel.repaint();
	}

	public void showSqlQueryView() {
		SqlQueryPanel panel = new SqlQueryPanel();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buildTopBar(), BorderLayout.NORTH);
		mainPanel.add(panel, BorderLayout.CENTER);

		frame.setContentPane(mainPanel);
		frame.revalidate();
	}

	public void showDeleteUpdateView() {
		DeleteUpdatePanel panel = new DeleteUpdatePanel();
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(buildTopBar(), BorderLayout.NORTH);
		mainPanel.add(panel, BorderLayout.CENTER);

		frame.setContentPane(mainPanel);
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

	private JPanel buildTopBar() {
		JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton homeButton = new JButton("🏠 홈");
		homeButton.addActionListener(e -> {
			if (user.equals("root"))
				showAdminInitView();
			else
				showUserInitView();
		});

		JButton logoutButton = new JButton("🔒 로그아웃");
		logoutButton.addActionListener(e -> {
			setUser(null);
			showLoginView();
		});

		topBar.add(homeButton);
		topBar.add(logoutButton);

		return topBar;
	}

}
