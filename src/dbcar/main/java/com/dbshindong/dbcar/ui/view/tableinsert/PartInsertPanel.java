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

	private final static String PART_ID = "부품 등록 ID";
	private final static String NAME = "부품";
	private final static String UNIT_PRICE = "단가";
	private final static String STOCK_QUANTITY = "재고 수량";
	private final static String STOCK_DATE = "입고 날짜";
	private final static String SUPPLIER_NAME = "공급업체명";

	public PartInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("🔩 부품 정보 입력"));

		formPanel.add(new JLabel(NAME));
		formPanel.add(nameField);

		formPanel.add(new JLabel(UNIT_PRICE));
		formPanel.add(unitPriceField);

		formPanel.add(new JLabel(STOCK_QUANTITY));
		formPanel.add(stockQuantityField);

		formPanel.add(new JLabel(STOCK_DATE + "(yyyy-mm-dd)"));
		formPanel.add(stockDateField);

		formPanel.add(new JLabel(SUPPLIER_NAME));
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
				Integer unitPrice = safeParseInt(unitPriceField.getText().trim(), UNIT_PRICE);
				Integer stockQuantity = safeParseInt(stockQuantityField.getText().trim(), STOCK_QUANTITY);
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
			throw new IllegalArgumentException(fieldName + "의 입력값은 숫자여야 합니다.");
		}
	}
}