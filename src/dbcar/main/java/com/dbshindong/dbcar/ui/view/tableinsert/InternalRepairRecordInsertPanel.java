package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;

import java.awt.*;
import java.sql.Date;

public class InternalRepairRecordInsertPanel extends JPanel {

	AppConfig ac = AppConfig.getInstance();

	private final JTextField carIdField = new JTextField(10);
	private final JTextField partIdField = new JTextField(10);
	private final JTextField repairDateField = new JTextField(10); // yyyy-mm-dd
	private final JTextField durationField = new JTextField(10);
	private final JTextField employeeIdField = new JTextField(10);

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	public InternalRepairRecordInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("🔧 내부 정비 기록 입력"));

		formPanel.add(new JLabel("차량 ID"));
		formPanel.add(carIdField);

		formPanel.add(new JLabel("부품 ID (없으면 비워두세요)"));
		formPanel.add(partIdField);

		formPanel.add(new JLabel("정비일 (yyyy-mm-dd)"));
		formPanel.add(repairDateField);

		formPanel.add(new JLabel("정비 소요 시간 (분)"));
		formPanel.add(durationField);

		formPanel.add(new JLabel("직원 ID"));
		formPanel.add(employeeIdField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				int carId = Integer.parseInt(carIdField.getText().trim());
				String partText = partIdField.getText().trim();
				Integer partId = partText.isEmpty() ? null : Integer.parseInt(partText);
				String repairDate = Date.valueOf(repairDateField.getText().trim()).toString();
				int duration = Integer.parseInt(durationField.getText().trim());
				int employeeId = Integer.parseInt(employeeIdField.getText().trim());

				InternalRepairRecord record = ac.dataInsertService().creatInternalRepairRecord(carId, partId,
						repairDate, duration, employeeId);

				if (record == null) {
					throw new IllegalArgumentException("입력값을 확인해주세요.");
				}

				ac.dataInsertService().insertInternalRepairRecord(record);
				JOptionPane.showMessageDialog(this, "저장 되었습니다.");
				clearFields();

			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "❗ 오류", JOptionPane.ERROR_MESSAGE);
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
		partIdField.setText("");
		repairDateField.setText("");
		durationField.setText("");
		employeeIdField.setText("");
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}
}