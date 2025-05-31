package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;

import javax.imageio.ImageIO;

public class CampingCarInsertPanel extends JPanel {
	AppConfig ac = AppConfig.getInstance();

	private final JTextField nameField = new JTextField(10);
	private final JTextField plateField = new JTextField(10);
	private final JTextField capacityField = new JTextField(10);
	private final JTextField descriptionField = new JTextField(10);
	private final JTextField rentalPriceField = new JTextField(10);
	private final JTextField companyIdField = new JTextField(10);
	private final JTextField registeredDateField = new JTextField(10);

	private final JButton uploadImageButton = new JButton("이미지 업로드");
	private final JLabel imageFileNameLabel = new JLabel("선택된 이미지 없음");
	private final JButton clearImageButton = new JButton("이미지 초기화");

	private final JButton saveButton = new JButton("저장");
	private final JButton cancelButton = new JButton("취소");
	private final JButton clearButton = new JButton("초기화");

	private byte[] imageData = null;
	private String imageFileName = null;

	private static final String NAME = "캠핑카 이름";
	private static final String PLATE_NUMBER = "캠핑카 차량 번호";
	private static final String CAPACITY = "캠핑카 승차 인원수";
	private static final String DESCRIPTION = "캠핑카상세정보";
	private static final String IMAGE = "캠핑카 이미지";
	private static final String RENTAL_PRICE = "캠핑카 대여 비용";
	private static final String COMPANY_ID = "캠핑카 대여 회사 ID";
	private static final String REGISTERED_DATE = "캠핑카 등록 일자";

	public CampingCarInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("CampingCar 정보 입력"));

		formPanel.add(new JLabel(NAME));
		formPanel.add(nameField);

		formPanel.add(new JLabel(PLATE_NUMBER));
		formPanel.add(plateField);

		formPanel.add(new JLabel(CAPACITY));
		formPanel.add(capacityField);

		formPanel.add(new JLabel(DESCRIPTION));
		formPanel.add(descriptionField);

		formPanel.add(new JLabel(RENTAL_PRICE));
		formPanel.add(rentalPriceField);

		formPanel.add(new JLabel(COMPANY_ID));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel(REGISTERED_DATE + "(YYYY-MM-DD)"));
		formPanel.add(registeredDateField);

		formPanel.add(new JLabel(IMAGE));
		JPanel imageControlPanel = new JPanel(new BorderLayout(5, 5));
		imageControlPanel.add(uploadImageButton, BorderLayout.WEST);
		imageControlPanel.add(imageFileNameLabel, BorderLayout.CENTER);
		imageControlPanel.add(clearImageButton, BorderLayout.EAST);
		formPanel.add(imageControlPanel);

		JPanel buttonPanel = new JPanel();
		buttonPanel.add(saveButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(clearButton);

		add(formPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		uploadImageButton.addActionListener(e -> {
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
					imageFileName = selectedFile.getName();
					imageFileNameLabel.setText(imageFileName);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "이미지 업로드 실패", "\u2757 오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		clearImageButton.addActionListener(e -> {
			imageData = null;
			imageFileName = null;
			imageFileNameLabel.setText("선택된 이미지 없음");
		});

		saveButton.addActionListener(e -> {
			try {
				String name = nameField.getText().trim();
				String plate = plateField.getText().trim();
				Integer capacity = safeParseInt(capacityField.getText().trim(), CAPACITY);
				String description = descriptionField.getText().trim();
				Integer rentalPrice = safeParseInt(rentalPriceField.getText().trim(), RENTAL_PRICE);
				Integer companyId = safeParseInt(companyIdField.getText().trim(), COMPANY_ID);
				String regDate = registeredDateField.getText().trim();

				CampingCar car = ac.dataInsertService().createCampingCar(name, plate, capacity, imageData, description,
						rentalPrice, companyId, regDate);
				ac.dataInsertService().insertCampingCar(car);
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
		nameField.setText("");
		plateField.setText("");
		capacityField.setText("");
		descriptionField.setText("");
		rentalPriceField.setText("");
		companyIdField.setText("");
		registeredDateField.setText("");
		imageData = null;
		imageFileName = null;
		imageFileNameLabel.setText("선택된 이미지 없음");
	}

	private Integer safeParseInt(String input, String fieldName) {
		try {
			return input == null || input.isBlank() ? null : Integer.parseInt(input);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(fieldName + "의 입력값은 숫자여야 합니다.");
		}
	}
}
