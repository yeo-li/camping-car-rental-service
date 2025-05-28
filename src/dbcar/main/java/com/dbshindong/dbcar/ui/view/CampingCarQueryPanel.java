package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarQueryController;

import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarAvailableDateQueryController;
import dbcar.main.java.com.dbshindong.dbcar.application.CampingCarAvailableDateQueryService;



public class CampingCarQueryView {
	private CampingCarQueryController campingCarQueryController;
	private CampingCarAvailableDateQueryController campingCarAvailableDateQueryController;  
	
	public CampingCarQueryView(CampingCarQueryController campingCarQueryController, CampingCarAvailableDateQueryController campingCarAvailableDateQueryController) {
		this.campingCarQueryController = campingCarQueryController;
		this.campingCarAvailableDateQueryController = campingCarAvailableDateQueryController; 
		createUI();
	}
	
	public void createUI() {
		JFrame frame = new JFrame("캠핑카목록");//초기 화면
		frame.setSize(800,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();//화면에 패널 추가
		frame.add(panel);
		
		addCampingCarQueryComponent(panel);
		
		frame.setVisible(true);
	}
	
	private void addCampingCarQueryComponent(JPanel panel) {
		
		int presety = (600-100) / 2;
		int presetx = (800- 100) / 2 ;
		panel.setLayout(null);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(800-155-80, 0, 80, 25);
		panel.add(logoutButton);
		
		JTextField tableTitle = new JTextField("     캠핑카 목록");
		tableTitle.setBounds(presetx, 25, 100, 40);
		panel.add(tableTitle);
		
		
		List<CampingCar> carList = campingCarQueryController.handleQuery();
		
		String[] columnNames = {"모델명","차량번호","탑승인원","사진","가격","상세정보"};
		Object[][] data = new Object[carList.size()][6];
		
		for(int i = 0; i < carList.size(); i++) {
			CampingCar car = carList.get(i);
			data[i][0] = car.getName();
			data[i][1] = car.getPlate_number();
			data[i][2] = car.getCapacity() + "인승";
			data[i][3] = car.getImage();
			data[i][4] = car.getRental_price() + "원";
			data[i][5] = car.getDescription();
		}
		
		
		
		JTable table = new JTable(data, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table.getColumn("모델명").setPreferredWidth(70);
		table.getColumn("탑승인원").setPreferredWidth(50);
		table.getColumn("가격").setPreferredWidth(40);
		table.getColumn("상세정보").setPreferredWidth(240);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 100, 800, 500);
		panel.add(scrollPane);
		
		table.addMouseListener(new MouseAdapter() {
			int flag = 0;
			
			@Override
			
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				
				if(selectedRow >= 0 && flag == 0) {
					flag = 1;
					new CampingCarAvailableDateQueryView((JFrame) SwingUtilities.getWindowAncestor(panel), campingCarAvailableDateQueryController, carList.get(selectedRow).getCar_id());
					flag = 0;
				}
			}
		});
		
		
		
	}
	
}
