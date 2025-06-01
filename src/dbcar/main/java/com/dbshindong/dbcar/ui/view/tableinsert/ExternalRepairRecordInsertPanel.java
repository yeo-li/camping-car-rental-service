package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
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

	private static final String CAR_ID = "캠핑카 등록 ID";
	private static final String SHOP_ID = "캠핑카 정비소 ID";
	private static final String COMPANY_ID = "캠핑카 대여 회사 ID";
	private static final String CUSTOMER_ID = "고객 ID";
	private static final String CONTENT = "정비 내역";
	private static final String REPAIR_DATE = "수리 날짜";
	private static final String COST = "수리 비용";
	private static final String DUE_DATE = "납입 기한";
	private static final String NOTE = "기타 정비 내역 정보";

	public ExternalRepairRecordInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("External Repair Record 입력"));

		formPanel.add(new JLabel(CAR_ID));
		formPanel.add(carIdField);

		formPanel.add(new JLabel(SHOP_ID));
		formPanel.add(shopIdField);

		formPanel.add(new JLabel(COMPANY_ID));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel(CUSTOMER_ID));
		formPanel.add(customerIdField);

		formPanel.add(new JLabel(CONTENT));
		formPanel.add(contentField);

		formPanel.add(new JLabel(REPAIR_DATE + "(yyyy-MM-dd)"));
		formPanel.add(repairDateField);

		formPanel.add(new JLabel(COST));
		formPanel.add(costField);

		formPanel.add(new JLabel(DUE_DATE + "(yyyy-MM-dd)"));
		formPanel.add(dueDateField);

		formPanel.add(new JLabel(NOTE));
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
						safeParseInt(carIdField.getText().trim(), CAR_ID),
						safeParseInt(shopIdField.getText().trim(), SHOP_ID),
						safeParseInt(companyIdField.getText().trim(), COMPANY_ID),
						safeParseInt(customerIdField.getText().trim(), CUSTOMER_ID), contentField.getText().trim(),
						repairDateField.getText().trim(), safeParseInt(costField.getText().trim(), COST),
						dueDateField.getText().trim(), noteField.getText().trim());

				ac.dataInsertService().insertExternalRepairRecord(record);
				JOptionPane.showMessageDialog(this, "저장 되었습니다.");
				clearFields();
			} catch (Exception ex) {
				GlobalExceptionHandler.handle(ex);
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

	private Integer safeParseInt(String input, String fieldName) {
		try {
			return input == null || input.isBlank() ? null : Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(fieldName + "의 입력값은 숫자여야 합니다.");
		}
	}
}