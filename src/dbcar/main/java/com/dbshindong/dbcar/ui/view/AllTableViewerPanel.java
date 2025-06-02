package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
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
		Field[] fullFields = first.getClass().getDeclaredFields();
		int limit = (fullFields.length - 1) / 2 - 1;
		Field[] fields = Arrays.copyOfRange(fullFields, 0, limit + 1);
		for (Field field : fields)
			field.setAccessible(true);

		String[] columnNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++)
			columnNames[i] = fields[i].getName();

		Object[][] rowData = new Object[dataList.size()][fields.length];

		for (int i = 0; i < dataList.size(); i++) {
			T obj = dataList.get(i);
			for (int j = 0; j < fields.length; j++) {
				try {
					Object value = fields[j].get(obj);
					if (value instanceof byte[] && "CampingCar".equals(tableName)
							&& fields[j].getName().equals("image")) {
						byte[] imgBytes = (byte[]) value;
						try {
							BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imgBytes));
							if (bufferedImage != null) {
								ImageIcon icon = new ImageIcon(
										bufferedImage.getScaledInstance(150, 100, Image.SCALE_SMOOTH));
								rowData[i][j] = icon;
							} else {
								rowData[i][j] = "이미지 오류";
							}
						} catch (Exception ex) {
							rowData[i][j] = "이미지 오류";
						}
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
		resultTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
		boolean flag = false;
		for (String col : columnNames) {
			if (col.equals("image"))
				flag = true;
		}
		if (flag) {
			resultTable.setRowHeight(80);
		} else {
			resultTable.setRowHeight(20);
		}
		resultTable.setIntercellSpacing(new Dimension(5, 5));
		resultTable.setShowGrid(true);
		resultTable.setGridColor(Color.LIGHT_GRAY);

		// 중앙 정렬 렌더러
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// 컬럼별 렌더러 설정
		for (int i = 0; i < resultTable.getColumnCount(); i++) {
			String columnName = columnNames[i];
			if ("image".equals(columnName) && "CampingCar".equals(tableName)) {
				resultTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
					@Override
					public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
							boolean hasFocus, int row, int column) {
						if (value instanceof ImageIcon) {
							JLabel label = new JLabel((ImageIcon) value);
							label.setHorizontalAlignment(SwingConstants.CENTER);
							return label;
						}
						if (value instanceof byte[]) {
							try {
								BufferedImage img = ImageIO.read(new ByteArrayInputStream((byte[]) value));
								if (img != null) {
									ImageIcon icon = new ImageIcon(img.getScaledInstance(100, 60, Image.SCALE_SMOOTH));
									JLabel label = new JLabel(icon);
									label.setHorizontalAlignment(SwingConstants.CENTER);
									return label;
								}
							} catch (IOException e) {
								return new JLabel("이미지 오류");
							}
						}
						return new JLabel("이미지 없음");
					}
				});
			} else {
				resultTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
			}
		}

		messageLabel.setText("✅ " + dataList.size() + "건 조회됨");
	}

	@FunctionalInterface
	interface SupplierWithException<T> {
		T get() throws Exception;
	}
}
