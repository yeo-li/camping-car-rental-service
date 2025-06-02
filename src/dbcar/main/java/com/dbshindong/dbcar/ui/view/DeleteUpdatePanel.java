package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.*;
import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;

public class DeleteUpdatePanel extends JPanel {

	private final AppConfig ac = AppConfig.getInstance();

	private JComboBox<String> tableSelector;
	private JTable dataTable;
	private JRadioButton deleteRadio;
	private JRadioButton updateRadio;
	private JTextField conditionField;
	private JButton executeButton;
	private JPanel updateFieldPanel;
	private Map<String, JTextField> updateFields = new LinkedHashMap<>();
	private JButton imageUploadButton;
	private JLabel imageFileNameLabel;
	private byte[] imageData = null;

	public DeleteUpdatePanel() {
		setLayout(new BorderLayout());
		initComponents();
	}

	private void initComponents() {
		String[] tableNames = { "CampingCar", "CampingCarCompany", "Customer", "Employee", "ExternalRepairRecord",
				"ExternalRepairShop", "InternalRepairRecord", "Part", "Rental" };
		tableSelector = new JComboBox<>(tableNames);
		tableSelector.addActionListener(e -> {
			refreshTableData();
			if (updateRadio.isSelected())
				buildUpdateFields();
			else
				clearUpdateFields();
		});
		add(tableSelector, BorderLayout.NORTH);

		dataTable = new JTable();
		dataTable.setShowGrid(true);
		dataTable.setGridColor(Color.GRAY);
		add(new JScrollPane(dataTable), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		deleteRadio = new JRadioButton("Delete", true); // 초기 선택 상태
		updateRadio = new JRadioButton("Update");
		ButtonGroup group = new ButtonGroup();
		group.add(deleteRadio);
		group.add(updateRadio);

		JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		radioPanel.add(deleteRadio);
		radioPanel.add(updateRadio);
		bottomPanel.add(radioPanel, BorderLayout.WEST);

		conditionField = new JTextField(30);
		JPanel conditionPanel = new JPanel();
		conditionPanel.add(new JLabel("Condition:"));
		conditionPanel.add(conditionField);
		bottomPanel.add(conditionPanel, BorderLayout.CENTER);

		executeButton = new JButton("Execute");
		executeButton.addActionListener(e -> executeAction());
		bottomPanel.add(executeButton, BorderLayout.EAST);

		add(bottomPanel, BorderLayout.SOUTH);

		updateFieldPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		updateFieldPanel.setBorder(BorderFactory.createTitledBorder("Update Fields"));
		add(updateFieldPanel, BorderLayout.EAST);

		updateRadio.addActionListener(e -> buildUpdateFields());
		deleteRadio.addActionListener(e -> clearUpdateFields());

		refreshTableData();
		clearUpdateFields();
	}

	private void buildUpdateFields() {
		updateFieldPanel.removeAll();
		updateFields.clear();
		imageData = null;

		String table = (String) tableSelector.getSelectedItem();
		if (!updateRadio.isSelected() || table == null) {
			updateFieldPanel.revalidate();
			updateFieldPanel.repaint();
			return;
		}

		try {
			List<Map<String, Object>> rows = ac.dataFetchService().fetchData("SELECT * FROM " + table);
			if (rows.isEmpty())
				return;

			JLabel guideLabel = new JLabel("* 변경할 속성만 입력하세요.");
			guideLabel.setForeground(Color.GRAY);
			updateFieldPanel.add(guideLabel);
			updateFieldPanel.add(new JLabel());

			boolean flag = true;
			for (String key : rows.get(0).keySet()) {
				if (flag) {
					flag = false;
					continue;
				}
				String newKey = key.substring(key.indexOf('_') + 1);
				if (newKey.equals("image"))
					continue;
				JLabel label = new JLabel(newKey);
				JTextField field = new JTextField(10);
				updateFields.put(newKey, field);
				updateFieldPanel.add(label);
				updateFieldPanel.add(field);
			}

			if ("CampingCar".equals(table)) {
				JLabel imageLabel = new JLabel("Image");
				imageUploadButton = new JButton("이미지 선택");
				imageFileNameLabel = new JLabel("선택된 이미지 없음");
				imageUploadButton.addActionListener(e -> handleImageUpload());
				updateFieldPanel.add(imageLabel);
				JPanel imgPanel = new JPanel(new BorderLayout());
				imgPanel.add(imageUploadButton, BorderLayout.WEST);
				imgPanel.add(imageFileNameLabel, BorderLayout.CENTER);
				updateFieldPanel.add(imgPanel);
			}

			updateFieldPanel.revalidate();
			updateFieldPanel.repaint();
		} catch (Exception e) {
			GlobalExceptionHandler.handle(e);
		}
	}

	private void handleImageUpload() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				File selectedFile = fileChooser.getSelectedFile();
				BufferedImage bufferedImage = ImageIO.read(selectedFile);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ImageIO.write(bufferedImage, "png", baos);
				baos.flush();
				imageData = baos.toByteArray();
				baos.close();
				imageFileNameLabel.setText(selectedFile.getName());
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "이미지 업로드에 실패했습니다.");
			}
		}
	}

	private void clearUpdateFields() {
		updateFieldPanel.removeAll();
		updateFields.clear();
		imageData = null;
		updateFieldPanel.revalidate();
		updateFieldPanel.repaint();
	}

//	private void refreshTableData() {
//		String table = (String) tableSelector.getSelectedItem();
//		try {
//			List<Map<String, Object>> rows = ac.dataFetchService().fetchData("SELECT * FROM " + table);
//			if (rows.isEmpty()) {
//				dataTable.setModel(new DefaultTableModel());
//				return;
//			}
//
//			String[] columns = rows.get(0).keySet().toArray(new String[0]);
//			for (int i = 0; i < columns.length; i++) {
//				columns[i] = columns[i].substring(columns[i].indexOf('_') + 1);
//			}
//
//			DefaultTableModel model = new DefaultTableModel(columns, 0) {
//				@Override
//				public boolean isCellEditable(int row, int column) {
//					return false;
//				}
//			};
//			for (Map<String, Object> row : rows) {
//				Object[] rowData = row.values().toArray();
//				model.addRow(rowData);
//			}
//			dataTable.setModel(model);
//		} catch (Exception ex) {
//			throw new IllegalArgumentException("데이터를 조회할 수 없습니다. 선택한 테이블: " + table);
//		}
//	}

	private void refreshTableData() {
		String table = (String) tableSelector.getSelectedItem();
		try {
			List<Map<String, Object>> rows = ac.dataFetchService().fetchData("SELECT * FROM " + table);
			if (rows.isEmpty()) {
				dataTable.setModel(new DefaultTableModel());
				return;
			}

			Map<String, Object> firstRow = rows.get(0);
			String[] columns = firstRow.keySet().toArray(new String[0]);
			Object[][] data = new Object[rows.size()][columns.length];

			for (int i = 0; i < rows.size(); i++) {
				Map<String, Object> row = rows.get(i);
				for (int j = 0; j < columns.length; j++) {
					Object value = row.get(columns[j]);
					if (value instanceof byte[] && columns[j].toLowerCase().contains("image")) {
						try {
							BufferedImage img = ImageIO.read(new ByteArrayInputStream((byte[]) value));
							if (img != null) {
								value = new ImageIcon(img.getScaledInstance(100, 60, Image.SCALE_SMOOTH));
							} else {
								value = "이미지 오류";
							}
						} catch (IOException e) {
							value = "이미지 오류";
						}
					}
					data[i][j] = value;
				}
			}

			for (int i = 0; i < columns.length; i++) {
				columns[i] = columns[i].substring(columns[i].indexOf('_') + 1);
			}

			DefaultTableModel model = new DefaultTableModel(data, columns) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			dataTable.setModel(model);

			// 이미지 렌더링 셀 설정
			dataTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
						boolean hasFocus, int row, int column) {
					if (value instanceof ImageIcon) {
						JLabel label = new JLabel((ImageIcon) value);
						label.setHorizontalAlignment(SwingConstants.CENTER);
						return label;
					}
					return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				}
			});

			dataTable.setRowHeight(80); // 이미지 높이에 맞게 설정
			boolean flag = false;
			for (String col : columns) {
				if (col.equals("image"))
					flag = true;
			}
			if (flag) {
				dataTable.setRowHeight(80);
			} else {
				dataTable.setRowHeight(20);
			}

			dataTable.setShowGrid(true);
			dataTable.setGridColor(Color.GRAY); 

		} catch (Exception ex) {
			GlobalExceptionHandler.handle(new IllegalArgumentException("데이터를 조회할 수 없습니다. 선택한 테이블: " + table)); 
		}
	}

	private void executeAction() {
		String table = (String) tableSelector.getSelectedItem();
		String condition = conditionField.getText().trim();

		try {
			if (deleteRadio.isSelected()) {
				switch (table) {
				case "CampingCar" -> ac.dataDeleteService().deleteCampingCars(condition);
				case "CampingCarCompany" -> ac.dataDeleteService().deleteCampingCarCompanys(condition);
				case "Customer" -> ac.dataDeleteService().deleteCustomers(condition);
				case "Employee" -> ac.dataDeleteService().deleteEmployees(condition);
				case "ExternalRepairRecord" -> ac.dataDeleteService().deleteExternalRepairRecords(condition);
				case "ExternalRepairShop" -> ac.dataDeleteService().deleteExternalRepairShops(condition);
				case "InternalRepairRecord" -> ac.dataDeleteService().deleteInternalRepairRecords(condition);
				case "Part" -> ac.dataDeleteService().deleteParts(condition);
				case "Rental" -> ac.dataDeleteService().deleteRentals(condition);
				}
			} else if (updateRadio.isSelected()) {
				Map<String, String> values = new LinkedHashMap<>();
				for (Map.Entry<String, JTextField> entry : updateFields.entrySet()) {
					String text = entry.getValue().getText().trim();
					if (!text.isEmpty())
						values.put(entry.getKey(), text);
				}
				if (values.isEmpty() && imageData == null) {
					throw new IllegalArgumentException("변경할 속성 값을 입력하세요.");
				}

				switch (table) {
				case "CampingCar" -> ac.dataUpdateService()
						.updateCampingCars(new UpdateCampingCarRequest(values.get("name"), values.get("plate_number"),
								safeParseInt(values.get("capacity"), "capacity"), imageData, values.get("description"),
								safeParseInt(values.get("rental_price"), "rental_price"),
								safeParseInt(values.get("company_id"), "company_id1"), values.get("registered_date")),
								condition);
				case "CampingCarCompany" -> ac.dataUpdateService().updateCampingCarCompanies(
						new UpdateCampingCarCompanyRequest(values.get("name"), values.get("address"),
								values.get("phone"), values.get("manager_name"), values.get("manager_email")),
						condition);
				case "Customer" -> ac.dataUpdateService()
						.updateCustomers(new UpdateCustomerRequest(values.get("username"), values.get("password"),
								values.get("license_number"), values.get("name"), values.get("address"),
								values.get("phone"), values.get("email")), condition);
				case "Employee" -> ac.dataUpdateService()
						.updateEmployees(new UpdateEmployeeRequest(values.get("name"), values.get("phone"),
								values.get("address"), safeParseInt(values.get("salary"), "salary"),
								safeParseInt(values.get("dependents"), "dependents"), values.get("department"),
								values.get("role")), condition);
				case "ExternalRepairShop" -> ac.dataUpdateService().updateExternalRepairShops(
						new UpdateExternalRepairShopRequest(values.get("name"), values.get("address"),
								values.get("phone"), values.get("manager_name"), values.get("manager_email")),
						condition);
				case "ExternalRepairRecord" -> ac.dataUpdateService().updateExternalRepairRecords(
						new UpdateExternalRepairRecordRequest(safeParseInt(values.get("car_id"), "car_id"),
								safeParseInt(values.get("shop_id"), "shop_id"),
								safeParseInt(values.get("company_id"), "company_id"),
								safeParseInt(values.get("customer_id"), "customer_id"), values.get("content"),
								values.get("repair_date"), safeParseInt(values.get("cost"), "cost"),
								values.get("due_date"), values.get("note")),
						condition);
				case "InternalRepairRecord" -> ac.dataUpdateService().updateInternalRepairRecords(
						new UpdateInternalRepairRecordRequest(safeParseInt(values.get("car_id"), "car_id"),
								safeParseInt(values.get("part_id"), "part_id"), values.get("repair_date"),
								safeParseInt(values.get("duration_minutes"), "duration_minutes"),
								safeParseInt(values.get("employee_id"), "employee_id")),
						condition);
				case "Part" -> ac.dataUpdateService()
						.updateParts(new UpdatePartRequest(values.get("name"),
								safeParseInt(values.get("unit_price"), "unit_price"),
								safeParseInt(values.get("stock_quantity"), "stock_quantity"), values.get("stock_date"),
								values.get("supplier_name")), condition);
				case "Rental" ->
					ac.dataUpdateService()
							.updateRentals(new UpdateRentalRequest(safeParseInt(values.get("car_id"), "car_id"),
									safeParseInt(values.get("customer_id"), "customer_id"),
									safeParseInt(values.get("company_id"), "company_id"), values.get("start_date"),
									safeParseInt(values.get("rental_period"), "rental_period"),
									safeParseInt(values.get("total_charge"), "total_charge"), values.get("due_date"),
									values.get("extra_charges"),
									safeParseInt(values.get("extra_charge_amount"), "extra_charge_amount")), condition);
				}
			}
			refreshTableData();
			clearUpdateFieldValues();
			JOptionPane.showMessageDialog(this, "실행이 완료되었습니다.");

		} catch (IllegalArgumentException e) {
			GlobalExceptionHandler.handle(e);
			e.printStackTrace();
		} catch (Exception e) {
			GlobalExceptionHandler.handle(e);
		}
	}

	private Integer safeParseInt(String input, String fieldName) {
		try {
			return input == null || input.isBlank() ? null : Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("['" + fieldName + "'] 필드에 숫자 형식이 올바르지 않습니다: '" + input + "'");
		}
	}

	private void clearUpdateFieldValues() {
		for (JTextField field : updateFields.values()) {
			field.setText("");
		}
		imageData = null;
		if (imageFileNameLabel != null)
			imageFileNameLabel.setText("선택된 이미지 없음");
	}
}
