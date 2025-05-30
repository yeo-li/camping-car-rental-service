package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.application.dto.*;

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
		add(new JScrollPane(dataTable), BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel(new BorderLayout());

		deleteRadio = new JRadioButton("Delete");
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
		buildUpdateFields();
	}

	private void buildUpdateFields() {
		updateFieldPanel.removeAll();
		updateFields.clear();

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

			for (String key : rows.get(0).keySet()) {
				String newKey = key.substring(key.indexOf('_') + 1);
				JLabel label = new JLabel(newKey);
				JTextField field = new JTextField(10);
				updateFields.put(newKey, field);
				updateFieldPanel.add(label);
				updateFieldPanel.add(field);
			}

			updateFieldPanel.revalidate();
			updateFieldPanel.repaint();
		} catch (Exception e) {
			showError("필드 생성 실패: " + e.getMessage());
		}
	}

	private void clearUpdateFields() {
		updateFieldPanel.removeAll();
		updateFields.clear();
		updateFieldPanel.revalidate();
		updateFieldPanel.repaint();
	}

	private void refreshTableData() {
		String table = (String) tableSelector.getSelectedItem();
		try {
			List<Map<String, Object>> rows = ac.dataFetchService().fetchData("SELECT * FROM " + table);
			if (rows.isEmpty()) {
				dataTable.setModel(new DefaultTableModel());
				return;
			}

			String[] columns = rows.get(0).keySet().toArray(new String[0]);

			for (int i = 0; i < columns.length; i++) {
				columns[i] = columns[i].substring(columns[i].indexOf('_') + 1);
			}

			DefaultTableModel model = new DefaultTableModel(columns, 0) {
				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			for (Map<String, Object> row : rows) {
				Object[] rowData = row.values().toArray();
				model.addRow(rowData);
			}
			dataTable.setModel(model);
		} catch (Exception ex) {
			showError("데이터 조회 실패: " + ex.getMessage());
		}
	}

	private void executeAction() {
		String table = (String) tableSelector.getSelectedItem();
		String condition = conditionField.getText().trim();

		if (condition.isEmpty()) {
			showError("조건을 입력하세요.");
			return;
		}

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

				if (values.isEmpty()) {
					showError("변경할 속성 값을 입력하세요.");
					return;
				}

				switch (table) {
				case "CampingCarCompany" -> ac.dataUpdateService().updateCampingCarCompanies(
						new UpdateCampingCarCompanyRequest(values.get("name"), values.get("address"),
								values.get("phone"), values.get("manager_name"), values.get("manager_email")),
						condition);
				case "CampingCar" ->
					ac.dataUpdateService().updateCampingCars(new UpdateCampingCarRequest(values.get("name"),
							values.get("plate_number"),
							values.get("capacity") == null ? null : Integer.parseInt(values.get("capacity")), null,
							values.get("description"),
							values.get("rental_price") == null ? null : Integer.parseInt(values.get("rental_price")),
							values.get("company_id") == null ? null : Integer.parseInt(values.get("company_id")),
							values.get("registered_date") == null ? null : values.get("registered_date")),
							condition);
				case "Customer" -> ac.dataUpdateService()
						.updateCustomers(new UpdateCustomerRequest(values.get("username"), values.get("password"),
								values.get("license_number"), values.get("name"), values.get("address"),
								values.get("phone"), values.get("email")), condition);
				case "Employee" -> ac.dataUpdateService()
						.updateEmployees(new UpdateEmployeeRequest(values.get("name"), values.get("phone"),
								values.get("address"),
								values.get("salary") == null ? null : Integer.parseInt(values.get("salary")),
								values.get("dependents") == null ? null : Integer.parseInt(values.get("dependents")),
								values.get("department"), values.get("role")), condition);
				case "ExternalRepairShop" -> ac.dataUpdateService().updateExternalRepairShops(
						new UpdateExternalRepairShopRequest(values.get("name"), values.get("address"),
								values.get("phone"), values.get("manager_name"), values.get("manager_email")),
						condition);
				case "ExternalRepairRecord" ->
					ac.dataUpdateService().updateExternalRepairRecords(new UpdateExternalRepairRecordRequest(
							values.get("car_id") == null ? null : Integer.parseInt(values.get("car_id")),
							values.get("shop_id") == null ? null : Integer.parseInt(values.get("shop_id")),
							values.get("company_id") == null ? null : Integer.parseInt(values.get("company_id")),
							values.get("customer_id") == null ? null : Integer.parseInt(values.get("customer_id")),
							values.get("content"), values.get("repair_date") == null ? null : values.get("repair_date"),
							values.get("cost") == null ? null : Integer.parseInt(values.get("cost")),
							values.get("due_date") == null ? null : values.get("due_date"), values.get("note")),
							condition);
				case "InternalRepairRecord" -> ac.dataUpdateService().updateInternalRepairRecords(
						new UpdateInternalRepairRecordRequest(
								values.get("car_id") == null ? null : Integer.parseInt(values.get("car_id")),
								values.get("part_id") == null ? null : Integer.parseInt(values.get("part_id")),
								values.get("repair_date") == null ? null : values.get("repair_date"),
								values.get("duration_minutes") == null ? null
										: Integer.parseInt(values.get("duration_minutes")),
								values.get("employee_id") == null ? null : Integer.parseInt(values.get("employee_id"))),
						condition);
				case "Part" -> ac.dataUpdateService()
						.updateParts(new UpdatePartRequest(values.get("name"),
								values.get("unit_price") == null ? null : Integer.parseInt(values.get("unit_price")),
								values.get("stock_quantity") == null ? null
										: Integer.parseInt(values.get("stock_quantity")),
								values.get("stock_date") == null ? null : values.get("stock_date"),
								values.get("supplier_name")), condition);
				case "Rental" -> ac.dataUpdateService().updateRentals(new UpdateRentalRequest(
						values.get("car_id") == null ? null : Integer.parseInt(values.get("car_id")),
						values.get("customer_id") == null ? null : Integer.parseInt(values.get("customer_id")),
						values.get("company_id") == null ? null : Integer.parseInt(values.get("company_id")),
						values.get("start_date") == null ? null : "start_date",
						values.get("rental_period") == null ? null : Integer.parseInt(values.get("rental_period")),
						values.get("total_charge") == null ? null : Integer.parseInt(values.get("total_charge")),
						values.get("due_date") == null ? null : values.get("due_date"), values.get("extra_charges"),
						values.get("extra_charge_amount") == null ? null
								: Integer.parseInt(values.get("extra_charge_amount"))),
						condition);
				}
			} else {
				showError("삭제 또는 변경을 선택하세요.");
				return;
			}

			refreshTableData();
			clearUpdateFieldValues();
			JOptionPane.showMessageDialog(this, "실행이 완료되었습니다.");
		} catch (Exception ex) {
			showError("실행 실패: " + ex.getMessage());
		}
	}

	private void clearUpdateFieldValues() {
		for (JTextField field : updateFields.values()) {
			field.setText("");
		}
	}

	private void showError(String message) {
		JOptionPane.showMessageDialog(this, message, "오류", JOptionPane.ERROR_MESSAGE);
	}
}