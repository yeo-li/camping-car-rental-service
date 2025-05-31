package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.Rental;

public class RentalInsertPanel extends JPanel {

	AppConfig ac = AppConfig.getInstance();

	private final JTextField carIdField = new JTextField(10);
	private final JTextField customerIdField = new JTextField(10);
	private final JTextField companyIdField = new JTextField(10);
	private final JTextField startDateField = new JTextField(10); // yyyy-MM-dd
	private final JTextField rentalPeriodField = new JTextField(10);
	private final JTextField totalChargeField = new JTextField(10);
	private final JTextField dueDateField = new JTextField(10); // yyyy-MM-dd
	private final JTextField extraChargesField = new JTextField(10);
	private final JTextField extraChargeAmountField = new JTextField(10);

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	private static final String RENTAL_ID = "캠핑카 대여 ID";
	private static final String CAR_ID = "캠핑카 등록 ID";
	private static final String CUSTOMER_ID = "고객 ID";
	private static final String COMPANY_ID = "캠핑카 대여 회사 ID";
	private static final String START_DATE = "대여 시작일";
	private static final String RENTAL_PERIOD = "대여 기간";
	private static final String TOTAL_CHARGE = "청구 요금";
	private static final String DUE_DATE = "납입 기한";
	private static final String EXTRA_CHARGE_DETAIL = "기타 청구 내역";
	private static final String EXTRA_CHARGE_AMOUNT = "기타 청구 요금 정보";

	public RentalInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("\uD83D\uDDC2 Rental 정보 입력"));

		formPanel.add(new JLabel(CAR_ID));
		formPanel.add(carIdField);

		formPanel.add(new JLabel(CUSTOMER_ID));
		formPanel.add(customerIdField);

		formPanel.add(new JLabel(COMPANY_ID));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel(START_DATE + "(yyyy-MM-dd)"));
		formPanel.add(startDateField);

		formPanel.add(new JLabel(RENTAL_PERIOD + "(days)"));
		formPanel.add(rentalPeriodField);

		formPanel.add(new JLabel(TOTAL_CHARGE));
		formPanel.add(totalChargeField);

		formPanel.add(new JLabel(DUE_DATE + "(yyyy-MM-dd)"));
		formPanel.add(dueDateField);

		formPanel.add(new JLabel(EXTRA_CHARGE_DETAIL));
		formPanel.add(extraChargesField);

		formPanel.add(new JLabel(EXTRA_CHARGE_AMOUNT));
		formPanel.add(extraChargeAmountField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				Integer carId = safeParseInt(carIdField.getText().trim(), CAR_ID);
				Integer customerId = safeParseInt(customerIdField.getText().trim(), CUSTOMER_ID);
				Integer companyId = safeParseInt(companyIdField.getText().trim(), COMPANY_ID);
				String startDate = startDateField.getText().trim();
				Integer rentalPeriod = safeParseInt(rentalPeriodField.getText().trim(), RENTAL_PERIOD);
				Integer totalCharge = safeParseInt(totalChargeField.getText().trim(), TOTAL_CHARGE);
				String dueDate = dueDateField.getText().trim();
				String extraDetail = extraChargesField.getText().trim();
				Integer extraAmount = safeParseInt(extraChargeAmountField.getText().trim(), EXTRA_CHARGE_AMOUNT);

				Rental rental = ac.dataInsertService().createRental(carId, customerId, companyId, startDate,
						rentalPeriod, totalCharge, dueDate, extraDetail, extraAmount);

				ac.dataInsertService().insertRental(rental);
				JOptionPane.showMessageDialog(this, "저장 되었습니다.");
				clearFields();
			} catch (Exception ex) {
				ex.printStackTrace();
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
		customerIdField.setText("");
		companyIdField.setText("");
		startDateField.setText("");
		rentalPeriodField.setText("");
		totalChargeField.setText("");
		dueDateField.setText("");
		extraChargesField.setText("");
		extraChargeAmountField.setText("");
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public JButton getCancelButton() {
		return cancelButton;
	}

	private Integer safeParseInt(String input, String fieldName) {
		try {
			return input == null || input.isBlank() ? null : Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(fieldName + "의 입력값은 숫자여야 합니다.");
		}
	}
}