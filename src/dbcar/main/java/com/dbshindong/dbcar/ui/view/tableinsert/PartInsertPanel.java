package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;

import java.awt.*;
import java.sql.Date;

public class PartInsertPanel extends JPanel {

	AppConfig ac = AppConfig.getInstance();

	private final JTextField nameField = new JTextField(10);
	private final JTextField unitPriceField = new JTextField(10);
	private final JTextField stockQuantityField = new JTextField(10);
	private final JTextField stockDateField = new JTextField(10); // yyyy-mm-dd
	private final JTextField supplierNameField = new JTextField(10);

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	public PartInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("🔩 부품 정보 입력"));

		formPanel.add(new JLabel("부품 이름"));
		formPanel.add(nameField);

		formPanel.add(new JLabel("단가"));
		formPanel.add(unitPriceField);

		formPanel.add(new JLabel("재고 수량"));
		formPanel.add(stockQuantityField);

		formPanel.add(new JLabel("입고 일자 (yyyy-mm-dd)"));
		formPanel.add(stockDateField);

		formPanel.add(new JLabel("공급 업체명"));
		formPanel.add(supplierNameField);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		saveButton.addActionListener(e -> {
			try {
				String name = nameField.getText().trim();
				Integer unitPrice = safeParseInt(unitPriceField.getText().trim(), "unit_price");
				Integer stockQuantity = safeParseInt(stockQuantityField.getText().trim(), "stock_quantity");
				String stockDate = stockDateField.getText().trim();
				String supplier = supplierNameField.getText().trim();

				Part part = ac.dataInsertService().createPart(name, unitPrice, stockQuantity, stockDate, supplier);

				ac.dataInsertService().insertPart(part);
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

		clearButton.addActionListener(e -> {
			clearFields();
		});
	}

	private void clearFields() {
		nameField.setText("");
		unitPriceField.setText("");
		stockQuantityField.setText("");
		stockDateField.setText("");
		supplierNameField.setText("");
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