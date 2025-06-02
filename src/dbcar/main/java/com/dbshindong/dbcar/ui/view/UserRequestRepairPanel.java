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
import javax.swing.table.TableCellRenderer;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Customer;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.UserReservationQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.view.CampingCarQueryPanel.SwingImageRenderer;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;

public class UserRequestRepairPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private AppConfig ac = AppConfig.getInstance();
	
	private Rental rent;
	private String userId;
	
	public UserRequestRepairPanel(Rental rent, String userId) {
		this.rent = rent;
		this.userId = userId;
		createUI();
	}

	private void createUI() {
		setLayout(null);
		addUserRequestRepairComponent(this);
	}
	private void addUserRequestRepairComponent(JPanel panel) {
		int presety = (600 - 100) / 2;
		int presetx = (800 - 100) / 2;

		JLabel tableTitle = new JLabel("정비소 목록");
		tableTitle.setBounds(presetx, 25, 100, 40);
		add(tableTitle);
		
		try {
			List<ExternalRepairShop> shopList = ac.userRequestRepairController().findAllShop();
			
			String[] columnNames = {"업체명","주소","담당자","연락처","이메일"};
			Object[][] data = new Object[shopList.size()][5];
	
			for (int i = 0; i < shopList.size(); i++) {
				ExternalRepairShop shop = shopList.get(i);
				data[i][0] = shopList.get(i).getName();
				data[i][1] = shopList.get(i).getAddress();
				data[i][2] = shopList.get(i).getManager_name();
				data[i][3] = shopList.get(i).getPhone();
				data[i][4] = shopList.get(i).getManager_email();
			}
	
			JTable table = new JTable(data, columnNames) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
		     
			};
	
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds((800-750)/2, 100, 750, 450);
			
			add(scrollPane);

			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					int selectedRow = table.getSelectedRow();
					if (selectedRow >= 0) {
						try {
							boolean res;
							res = ac.userRequestRepairController().onShopSelected(shopList.get(selectedRow).getShop_id(), rent, userId);
							if(res) {
								JOptionPane.showMessageDialog(null, "정비 요청이 완료되었습니다.");
							}
							else {
								JOptionPane.showMessageDialog(null, "수리가 진행중입니다.");
							}
						} catch(Exception ex) {
							GlobalExceptionHandler.handle(ex);
						}
					}
				}
			});
		}catch(Exception ex) {
			GlobalExceptionHandler.handle(ex);
		}
	}
}
