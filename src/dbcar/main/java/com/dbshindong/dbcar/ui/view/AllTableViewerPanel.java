package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.util.List;

import javax.imageio.ImageIO;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

public class AllTableViewerPanel extends JPanel {

	private final AppConfig ac = AppConfig.getInstance();
	private final JTable resultTable = new JTable();
	private final JLabel messageLabel = new JLabel(" ");
	private final JPanel buttonPanel = new JPanel();

	public AllTableViewerPanel() {
		setLayout(new BorderLayout(10, 10));

		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		buttonPanel.setLayout(new GridLayout(0, 1, 5, 5));

		buttonPanel
				.add(createTableButton("CampingCarCompany", () -> ac.dataFetchService().fetchAllCampingCarCompanies()));
		buttonPanel.add(createTableButton("CampingCar", () -> ac.dataFetchService().fetchAllCampingCars()));
		buttonPanel.add(createTableButton("Customer", () -> ac.dataFetchService().fetchAllCustomers()));
		buttonPanel.add(createTableButton("Employee", () -> ac.dataFetchService().fetchAllEmployees()));
		buttonPanel.add(
				createTableButton("ExternalRepairShop", () -> ac.dataFetchService().fetchAllExternalRepairShops()));
		buttonPanel.add(
				createTableButton("ExternalRepairRecord", () -> ac.dataFetchService().fetchAllExternalRepairRecords()));
		buttonPanel.add(createTableButton("Part", () -> ac.dataFetchService().fetchAllParts()));
		buttonPanel.add(
				createTableButton("InternalRepairRecord", () -> ac.dataFetchService().fetchAllInternalRepairRecords()));
		buttonPanel.add(createTableButton("Rental", () -> ac.dataFetchService().fetchAllRentals()));

		JScrollPane tableScroll = new JScrollPane(resultTable);

		add(messageLabel, BorderLayout.NORTH);
		add(new JScrollPane(buttonPanel), BorderLayout.WEST);
		add(tableScroll, BorderLayout.CENTER);
	}

	private <T> JButton createTableButton(String tableName, SupplierWithException<List<T>> fetchFunction) {
		JButton button = new JButton(tableName);
		button.setPreferredSize(new Dimension(180, 40));
		button.setFont(new Font("SansSerif", Font.PLAIN, 14));
		button.addActionListener(e -> {
			try {
				List<T> list = fetchFunction.get();
				renderObjectTable(list, tableName);
			} catch (Exception ex) {
				GlobalExceptionHandler.handle(ex);
			}
		});
		return button;
	}

	private <T> void renderObjectTable(List<T> dataList, String tableName) {
		if (dataList.isEmpty()) {
			messageLabel.setText("\uD83D\uDCC4 결과 없음");
			resultTable.setModel(new DefaultTableModel());
			return;
		}

		T first = dataList.get(0);
		Field[] fields = first.getClass().getDeclaredFields();
		for (Field f : fields)
			f.setAccessible(true);

		String[] columnNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			columnNames[i] = fields[i].getName();
		}

		Object[][] rowData = new Object[dataList.size()][fields.length];
		for (int i = 0; i < dataList.size(); i++) {
			T obj = dataList.get(i);
			for (int j = 0; j < fields.length; j++) {
				try {
					Object value = fields[j].get(obj);
					if (value instanceof byte[] && "CampingCar".equals(tableName)) {
						// 이미지를 아이콘으로 변환
						byte[] imageBytes = (byte[]) value;
						BufferedImage img = ImageIO.read(new ByteArrayInputStream(imageBytes));
						Image scaled = img.getScaledInstance(100, 70, Image.SCALE_SMOOTH);
						rowData[i][j] = new ImageIcon(scaled);
					} else {
						rowData[i][j] = value;
					}
				} catch (Exception e) {
					rowData[i][j] = "ERROR";
				}
			}
		}

		DefaultTableModel model = new DefaultTableModel(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				for (int row = 0; row < getRowCount(); row++) {
					Object value = getValueAt(row, columnIndex);
					if (value != null)
						return value.getClass();
				}
				return Object.class;
			}
		};

		resultTable.setModel(model);
		resultTable.setRowHeight(80);
		messageLabel.setText("✅ " + dataList.size() + "건 조회됨");
	}

	@FunctionalInterface
	interface SupplierWithException<T> {
		T get() throws Exception;
	}
}
