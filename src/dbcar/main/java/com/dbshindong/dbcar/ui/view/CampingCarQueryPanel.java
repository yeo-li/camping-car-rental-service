package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.List;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.ui.controller.CampingCarQueryController;
import dbcar.main.java.com.dbshindong.dbcar.ui.coordinator.AppCoordinator;

public class CampingCarQueryPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private AppConfig ac = AppConfig.getInstance();
	
	public CampingCarQueryPanel() {
		createUI();
	}

	private void createUI() {
		setLayout(null);
		addCarQueryComponent(this);
	}
	private void addCarQueryComponent(JPanel panel) {
		int presety = (600 - 100) / 2;
		int presetx = (800 - 100) / 2;

		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(800 - 155 - 80, 0, 80, 25);
		add(logoutButton);

		JLabel tableTitle = new JLabel("캠핑카 목록");
		tableTitle.setBounds(presetx, 25, 100, 40);
		add(tableTitle);

		List<CampingCar> carList = ac.campingCarQueryController().handleQuery();

		String[] columnNames = {"모델명", "차량번호", "탑승인원", "사진", "가격", "상세정보"};
		Object[][] data = new Object[carList.size()][6];

		for (int i = 0; i < carList.size(); i++) {
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
			@Override
	        public TableCellRenderer getCellRenderer(int row, int column) {
	            if (column == 3) return new SwingImageRenderer();
	            return super.getCellRenderer(row, column);
	        }
		};

		table.setRowHeight(60);
		table.getColumn("모델명").setPreferredWidth(70);
		table.getColumn("탑승인원").setPreferredWidth(50);
		table.getColumn("가격").setPreferredWidth(40);
		table.getColumn("상세정보").setPreferredWidth(190);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds((800-750)/2, 100, 750, 450);
		
		add(scrollPane);

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					ac.campingCarQueryController().onCarSelected(selectedRow + 1);
				}
			}
		});

	
	}
	
	static class SwingImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            if (value instanceof byte[]) {
                try {
                    ImageIcon icon = new ImageIcon((byte[]) value); // pure Swing
                    Image scaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaled));
                } catch (Exception e) {
                    label.setText("이미지 오류");
                }
            }
            return label;
        }
    }
}