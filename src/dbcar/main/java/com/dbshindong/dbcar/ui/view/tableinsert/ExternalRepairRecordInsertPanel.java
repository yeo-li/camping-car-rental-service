package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;

public class ExternalRepairRecordInsertPanel extends JPanel {

	AppConfig ac = AppConfig.getInstance();

	private final JTextField carIdField = new JTextField(10);
	private final JTextField shopIdField = new JTextField(10);
	private final JTextField companyIdField = new JTextField(10);
	private final JTextField customerIdField = new JTextField(10);
	private final JTextField contentField = new JTextField(10);
	private final JTextField repairDateField = new JTextField(10); // yyyy-MM-dd
	private final JTextField costField = new JTextField(10);
	private final JTextField dueDateField = new JTextField(10); // yyyy-MM-dd
	private final JTextField noteField = new JTextField(10);

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	public ExternalRepairRecordInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("🆒 External Repair Record 입력"));

		formPanel.add(new JLabel("Car ID"));
		formPanel.add(carIdField);

		formPanel.add(new JLabel("Shop ID"));
		formPanel.add(shopIdField);

		formPanel.add(new JLabel("Company ID"));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel("Customer ID"));
		formPanel.add(customerIdField);

		formPanel.add(new JLabel("Content"));
		formPanel.add(contentField);

		formPanel.add(new JLabel("Repair Date (yyyy-MM-dd)"));
		formPanel.add(repairDateField);

		formPanel.add(new JLabel("Cost"));
		formPanel.add(costField);

		formPanel.add(new JLabel("Due Date (yyyy-MM-dd)"));
		formPanel.add(dueDateField);

		formPanel.add(new JLabel("Note"));
		formPanel.add(noteField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				ExternalRepairRecord record = ac.dataInsertService().createExternalRepairRecord(
						Integer.parseInt(carIdField.getText().trim()), Integer.parseInt(shopIdField.getText().trim()),
						Integer.parseInt(companyIdField.getText().trim()),
						Integer.parseInt(customerIdField.getText().trim()), contentField.getText().trim(),
						Date.valueOf(repairDateField.getText().trim()).toString(),
						Integer.parseInt(costField.getText().trim()),
						Date.valueOf(dueDateField.getText().trim()).toString(), noteField.getText().trim());

				ac.dataInsertService().insertExternalRepairRecord(record);
				JOptionPane.showMessageDialog(this, "저장 되었습니다.");
				clearFields();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "❗ 오류", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "입력 오류: " + ex.getMessage(), "❗ 오류", JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelButton.addActionListener(e -> {
			clearFields();
			ac.appCoordinator().clearContentPanel();
		});

		clearButton.addActionListener(e -> clearFields());
	}

	private void clearFields() {
		carIdField.setText("");
		shopIdField.setText("");
		companyIdField.setText("");
		customerIdField.setText("");
		contentField.setText("");
		repairDateField.setText("");
		costField.setText("");
		dueDateField.setText("");
		noteField.setText("");
	}
}