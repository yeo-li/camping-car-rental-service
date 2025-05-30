package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellRenderer;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.UserReservationQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.CampingCarQueryPanel.SwingImageRenderer;

public class UserReservationQueryPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private AppConfig ac = AppConfig.getInstance();
	private UserReservationQueryController userReservationQueryController;
	
	public UserReservationQueryPanel() {
		createUI();
	}
	
	public void createUI() {
		setLayout(null);
		addLoginComponent(this);
	}

	private void addLoginComponent(JPanel panel) {

		int presety = (600 - 100) / 2;
		int presetx = (800 - 100) / 2;
		List<Rental> rentList;
		JTable table;

		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(800 - 155 - 80, 0, 80, 25);
		add(logoutButton);

		JLabel tableTitle = new JLabel("예약 목록");
		tableTitle.setBounds(presetx, 25, 100, 40);
		add(tableTitle);
		try {
			rentList = ac.userReservationQueryController().hendleQuery(ac.appCoordinator.getUser());
			
			String[] columnNames = {"삭제", "수정", "상세", "모델명", "시작일", "기간", "가격","추가요금","추가요금내역", "납입기한"};
			Object[][] data = new Object[rentList.size()][10];
	
			for (int i = 0; i < rentList.size(); i++) {
				CampingCar car = ac.userReservationQueryController().findCarById(rentList.get(i).getCar_id());
				data[i][0] = Boolean.FALSE;
				data[i][1] = "[수정]";
				data[i][2] = "[조회]";
				data[i][3] = car.getName();
				data[i][4] = rentList.get(i).getStart_date();
				data[i][5] = rentList.get(i).getRental_period();
				data[i][6] = rentList.get(i).getTotal_charge();
				data[i][7] = rentList.get(i).getExtra_charge();
				data[i][8] = rentList.get(i).getExtra_charge_detail();
				data[i][9] = rentList.get(i).getDue_date();
				
			}
	
			table = new JTable(data, columnNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 0;
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
	
			table.getColumn("삭제").setPreferredWidth(40);
			table.getColumn("수정").setPreferredWidth(40);
			table.getColumn("상세").setPreferredWidth(40);
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds((800-750)/2, 100, 750, 450);
			
			add(scrollPane);
	
			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int row = table.rowAtPoint(e.getPoint());
			        int col = table.columnAtPoint(e.getPoint());
			        
			        if (row >= 0) {
			            String columnName = table.getColumnName(col);

			            if ("상세".equals(columnName)) {
			                // 조회 버튼처럼 동작
			                JOptionPane.showMessageDialog(null, "상세조회: " + rentList.get(row).getCar_id());
			                // ac.campingCarQueryController().onCarSelected(...); 와 연동 가능

			            } else if ("수정".equals(columnName)) {
			                // 수정 버튼처럼 동작
			                JOptionPane.showMessageDialog(null, "수정하기: " + rentList.get(row).getCar_id());
			                // 수정 뷰 띄우는 로직 넣으면 됨
			            }
			        }
				}
			});
		}catch(Exception ex) {
			GlobalExceptionHandler.handle(ex);
		}
	}
	
}
