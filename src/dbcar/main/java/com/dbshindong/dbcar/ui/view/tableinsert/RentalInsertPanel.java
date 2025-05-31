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

	public RentalInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("\uD83D\uDDC2 Rental 정보 입력"));

		formPanel.add(new JLabel("Car ID"));
		formPanel.add(carIdField);

		formPanel.add(new JLabel("Customer ID"));
		formPanel.add(customerIdField);

		formPanel.add(new JLabel("Company ID"));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel("Start Date (yyyy-MM-dd)"));
		formPanel.add(startDateField);

		formPanel.add(new JLabel("Rental Period (days)"));
		formPanel.add(rentalPeriodField);

		formPanel.add(new JLabel("Total Charge"));
		formPanel.add(totalChargeField);

		formPanel.add(new JLabel("Due Date (yyyy-MM-dd)"));
		formPanel.add(dueDateField);

		formPanel.add(new JLabel("Extra Charges"));
		formPanel.add(extraChargesField);

		formPanel.add(new JLabel("Extra Charge Amount"));
		formPanel.add(extraChargeAmountField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				Integer carId = safeParseInt(carIdField.getText().trim(), "car_id");
				Integer customerId = safeParseInt(customerIdField.getText().trim(), "customer_id");
				Integer companyId = safeParseInt(companyIdField.getText().trim(), "company_id");
				String startDate = startDateField.getText().trim();
				Integer rentalPeriod = safeParseInt(rentalPeriodField.getText().trim(), "rental_period");
				Integer totalCharge = safeParseInt(totalChargeField.getText().trim(), "total_charge");
				String dueDate = dueDateField.getText().trim();
				String extraDetail = extraChargesField.getText().trim();
				Integer extraAmount = safeParseInt(extraChargeAmountField.getText().trim(), "extra_charge_amount");

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
			throw new IllegalArgumentException("['" + fieldName + "'] 필드에 숫자 형식이 올바르지 않습니다: '" + input + "'");
		}
	}
}