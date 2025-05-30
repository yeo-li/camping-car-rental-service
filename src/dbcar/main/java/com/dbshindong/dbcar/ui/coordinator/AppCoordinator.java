package dbcar.main.java.com.dbshindong.dbcar.ui.coordinator;

import java.awt.BorderLayout;
import java.awt.CardLayout;

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
	private String user;
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
		AdminInitPanel view = new AdminInitPanel();
		frame.setContentPane(view);
		frame.revalidate();
	}
	
	public void showAllTableView() {
		AllTableViewerPanel panel = new AllTableViewerPanel();
		frame.setContentPane(panel);
		frame.revalidate();
	}

	public void showTableEntrySelectorView() {
		JPanel mainPanel = new JPanel(new BorderLayout());

		// 상단: 테이블 선택 패널
		TableEntrySelectorPanel selectorPanel = new TableEntrySelectorPanel(this::handleTableSelection);
		mainPanel.add(selectorPanel, BorderLayout.NORTH);

		// 하단: 입력 패널을 담을 컨테이너 (CardLayout)
		JPanel contentPanel = new JPanel(new CardLayout());
		mainPanel.add(contentPanel, BorderLayout.CENTER);

		// 참조 저장 (이후 패널 전환 시 필요)
		this.tableContentPanel = contentPanel;

		// 프레임에 표시
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

	// 표준
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
		SqlQueryPanel view = new SqlQueryPanel();
		frame.setContentPane(view);
		frame.revalidate();
	}

	public void showDeleteUpdateView() {
		DeleteUpdatePanel view = new DeleteUpdatePanel();
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
