package dbcar.main.java.com.dbshindong.dbcar.ui.view.tableinsert;

import javax.swing.*;
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

	public CampingCarInsertPanel() {
		setLayout(new BorderLayout(10, 10));

		JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
		formPanel.setBorder(BorderFactory.createTitledBorder("\uD83D\uDE98 CampingCar 정보 입력"));

		formPanel.add(new JLabel("Name"));
		formPanel.add(nameField);

		formPanel.add(new JLabel("Plate Number"));
		formPanel.add(plateField);

		formPanel.add(new JLabel("Capacity"));
		formPanel.add(capacityField);

		formPanel.add(new JLabel("Description"));
		formPanel.add(descriptionField);

		formPanel.add(new JLabel("Rental Price"));
		formPanel.add(rentalPriceField);

		formPanel.add(new JLabel("Company ID"));
		formPanel.add(companyIdField);

		formPanel.add(new JLabel("Registered Date (YYYY-MM-DD)"));
		formPanel.add(registeredDateField);

		formPanel.add(new JLabel("Image Upload"));
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
				int capacity = Integer.parseInt(capacityField.getText().trim());
				String description = descriptionField.getText().trim();
				int rentalPrice = Integer.parseInt(rentalPriceField.getText().trim());
				int companyId = Integer.parseInt(companyIdField.getText().trim());
				Date regDate = Date.valueOf(registeredDateField.getText().trim());

				if (imageData == null) {
					JOptionPane.showMessageDialog(this, "이미지를 업로드해주세요.", "\u2757 오류", JOptionPane.ERROR_MESSAGE);
					return;
				}

				CampingCar car = ac.dataInsertService().createCampingCar(name, plate, capacity, imageData, description,
						rentalPrice, companyId, regDate);
				ac.dataInsertService().insertCampingCar(car);
				JOptionPane.showMessageDialog(this, "저장 되었습니다.");
				clearFields();
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(this, ex.getMessage(), "\u2757 오류", JOptionPane.ERROR_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "입력 중 오류가 발생했습니다: " + ex.getMessage(), "\u2757 오류",
						JOptionPane.ERROR_MESSAGE);
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
}
