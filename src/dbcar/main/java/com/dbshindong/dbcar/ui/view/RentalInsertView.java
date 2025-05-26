package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RentalInsertView extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField carIdField = new JTextField(10);
	private final JTextField customerIdField = new JTextField(10);
	private final JTextField companyIdField = new JTextField(10);
	private final JTextField startDateField = new JTextField(10);
	private final JTextField rentalPeriodField = new JTextField(10);
	private final JTextField totalChargeField = new JTextField(10);
	private final JTextField dueDateField = new JTextField(10);
	private final JTextField extraChargesField = new JTextField(10);
	private final JTextField extraChargeAmountField = new JTextField(10);
	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");

	public RentalInsertView() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("📄 Rental 정보 입력"));

		formPanel.add(new JLabel("car_id"));
		formPanel.add(carIdField);

		formPanel.add(new JLabel("customer_id"));
		formPanel.add(customerIdField);

		formPanel.add(new JLabel("company_id"));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel("start_date (YYYY-MM-DD)"));
		formPanel.add(startDateField);

		formPanel.add(new JLabel("rental_period (일 수)"));
		formPanel.add(rentalPeriodField);

		formPanel.add(new JLabel("total_charge (총 요금)"));
		formPanel.add(totalChargeField);

		formPanel.add(new JLabel("due_date (YYYY-MM-DD)"));
		formPanel.add(dueDateField);

		formPanel.add(new JLabel("extra_charges"));
		formPanel.add(extraChargesField);

		formPanel.add(new JLabel("extra_charge_amount"));
		formPanel.add(extraChargeAmountField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		// 예시 저장 버튼 로직
		saveButton.addActionListener(e -> {
			try {
				Map<String, Object> input = collectInputs();
				JOptionPane.showMessageDialog(this, "입력값 확인:\n" + input);
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "❗ 오류", JOptionPane.ERROR_MESSAGE);
			}
		});

		cancelButton.addActionListener(e -> clearFields());
	}

	private Map<String, Object> collectInputs() {
		Map<String, Object> data = new HashMap<>();
		try {
			data.put("car_id", Integer.parseInt(carIdField.getText().trim()));
			data.put("customer_id", Integer.parseInt(customerIdField.getText().trim()));
			data.put("company_id", Integer.parseInt(companyIdField.getText().trim()));
			data.put("start_date", startDateField.getText().trim());
			data.put("rental_period", Integer.parseInt(rentalPeriodField.getText().trim()));
			data.put("total_charge", Integer.parseInt(totalChargeField.getText().trim()));
			data.put("due_date", dueDateField.getText().trim());

			String extra = extraChargesField.getText().trim();
			if (!extra.isEmpty()) {
				data.put("extra_charges", extra);
			}

			String extraAmount = extraChargeAmountField.getText().trim();
			if (!extraAmount.isEmpty()) {
				data.put("extra_charge_amount", Integer.parseInt(extraAmount));
			}
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("숫자 형식 오류가 있습니다.");
		}
		return data;
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
}