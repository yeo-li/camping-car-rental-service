package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.infrastructure.customer.RentalRepository;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarAvailableDateQueryController;


public class CampingCarAvailableDateQueryPanel extends JPanel {
	
	private int car_id;
	private AppConfig ac = AppConfig.getInstance();
	
	public CampingCarAvailableDateQueryPanel(int car_id) {

		this.car_id = car_id;
		
		
		
		
		createUI();
	}
	
	public void createUI() {
		setLayout(null);
		addCampingCarAvailableDateQueryComponent(this);
	}
private void addCampingCarAvailableDateQueryComponent(JPanel panel) {
		
		int presety = (600-100) / 2;
		int presetx = (800- 100) / 2 ;
		panel.setLayout(null);
		
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(800-80, 0, 80, 25);
		panel.add(logoutButton);
		
		JButton reservationButton = new JButton("reservation");
		reservationButton.setBounds(presetx - 32, 65, 155, 25);
		panel.add(reservationButton);
		
		JLabel tableTitle = new JLabel("대여 가능 일자");
		tableTitle.setHorizontalAlignment(SwingConstants.CENTER);
		tableTitle.setBounds(presetx, 25, 100, 40);
		tableTitle.setFont(new Font("SansSerif", Font.BOLD, 14));

		panel.add(tableTitle);
		
		
		List<LocalDate> availList = ac.campingCarAvailableDateQueryController().handleQuery(car_id);
		String[] columnNames = {"선택","날짜"};
		Object[][] data = new Object[availList.size()][2];
		
		for(int i = 0; i < availList.size(); i++) {
			LocalDate date = availList.get(i);
			data[i][0] = Boolean.FALSE;
			data[i][1] = date.toString();
		}
		
		
		
		JTable table = new JTable(data, columnNames) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return column == 0; // 선택 열만 수정 가능
		    }

		    @Override
		    public Class<?> getColumnClass(int column) {
		        if (column == 0) {
		            return Boolean.class; // 체크박스 열
		        } else {
		            return String.class;
		        }
		    }
		};
		table.getColumn("선택").setPreferredWidth(40);
		table.getColumn("날짜").setPreferredWidth(760);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(0, 100, 800, 500);
		panel.add(scrollPane);
		
		reservationButton.addActionListener( e -> {
			int result = JOptionPane.showConfirmDialog(
					null, "예약을 확정하시겠습니까?","예약 확인",JOptionPane.YES_NO_OPTION
					);
					
			if(result == JOptionPane.YES_OPTION) {
				List<LocalDate> selected = new ArrayList<>();
				for (int i = 0; i < table.getRowCount(); i++) {
				    Boolean isChecked = (Boolean) table.getValueAt(i, 0);
				    if (Boolean.TRUE.equals(isChecked)) {
				        String dateStr = (String) table.getValueAt(i, 1);
				        selected.add(LocalDate.parse(dateStr)); // yyyy-MM-dd 형식 파싱
				    }
				}
				ac.campingCarAvailableDateQueryController().saveReservation(selected, car_id);
				JOptionPane.showMessageDialog(null, "예약에 성공했습니다.");
				ac.appCoordinator().showCampingCarQueryView();
			}
			else {
				JOptionPane.showMessageDialog(null, "작업을 취소하셨습니다.");
			}
					
		});
		
		
		
	}
}
