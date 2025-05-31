package dbcar.main.java.com.dbshindong.dbcar.ui.view.admin;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.GlobalExceptionHandler;
import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SqlQueryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private AppConfig ac = AppConfig.getInstance();
	private final JTextArea sqlInputArea;
	private final JButton executeButton;
	private final JTable resultTable;
	private final JLabel messageLabel;

	public SqlQueryPanel() {
		setLayout(new BorderLayout(10, 10));

		// 메시지 라벨
		messageLabel = new JLabel(" ");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setFont(new Font("SansSerif", Font.BOLD, 14));

		// 결과 테이블
		resultTable = new JTable();
		resultTable.setShowGrid(true);
		resultTable.setGridColor(Color.GRAY);
		JScrollPane tableScrollPane = new JScrollPane(resultTable);

		// SQL 입력창
		sqlInputArea = new JTextArea(4, 60);
		JScrollPane inputScroll = new JScrollPane(sqlInputArea);

		executeButton = new JButton("SQL 실행");

		executeButton.addActionListener(e -> {
			String sql = sqlInputArea.getText().trim();
			try {
				List<Map<String, Object>> result = ac.sqlQueryController().handleQuery(sql);
				renderTable(result);
			} catch (Exception ex) {
				GlobalExceptionHandler.handle(ex);
			}
		});

		// 하단 입력 영역
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.add(inputScroll, BorderLayout.CENTER);
		bottomPanel.add(executeButton, BorderLayout.EAST);

		// 상단 영역
		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(messageLabel, BorderLayout.NORTH);
		topPanel.add(tableScrollPane, BorderLayout.CENTER);

		add(topPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public String getSqlInput() {
		return sqlInputArea.getText().trim();
	}

	public void setResult(List<Map<String, Object>> result) {
		if (result.isEmpty()) {
			messageLabel.setText("📭 해당 데이터가 없습니다.");
			resultTable.setModel(new DefaultTableModel());
			return;
		}

		Map<String, Object> firstRow = result.get(0);
		String[] columnNames = firstRow.keySet().toArray(new String[0]);

		Object[][] rowData = new Object[result.size()][columnNames.length];
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> row = result.get(i);
			for (int j = 0; j < columnNames.length; j++) {
				rowData[i][j] = row.get(columnNames[j]);
			}
		}

		resultTable.setModel(new DefaultTableModel(rowData, columnNames) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		messageLabel.setText("✅ 결과: " + result.size() + "건");
	}

	private void renderTable(List<Map<String, Object>> result) {
		if (result.isEmpty()) {
			messageLabel.setText("📭 해당 데이터가 없습니다.");
			resultTable.setModel(new DefaultTableModel());
			return;
		}

		Map<String, Object> firstRow = result.get(0);
		String[] columns = firstRow.keySet().toArray(new String[0]);

		Object[][] data = new Object[result.size()][columns.length];
		for (int i = 0; i < result.size(); i++) {
			Map<String, Object> row = result.get(i);
			for (int j = 0; j < columns.length; j++) {
				Object value = row.get(columns[j]);
				if (value instanceof byte[] && columns[j].toLowerCase().contains("image")) {
					try {
						BufferedImage img = ImageIO.read(new ByteArrayInputStream((byte[]) value));
						if (img != null) {
							value = new ImageIcon(img.getScaledInstance(100, 60, Image.SCALE_SMOOTH));
						} else {
							value = "이미지 오류";
						}
					} catch (IOException e) {
						value = "이미지 오류";
					}
				}
				data[i][j] = value;
			}
		}

		// 컬럼명 다듬기 (접두사 제거)
		for (int i = 0; i < columns.length; i++) {
			columns[i] = columns[i].substring(columns[i].indexOf('_') + 1);
		}

		resultTable.setModel(new DefaultTableModel(data, columns) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});

		resultTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof ImageIcon) {
					JLabel label = new JLabel((ImageIcon) value);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					return label;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		});

		boolean flag = false;
		for (String col : columns) {
			if (col.equals("image"))
				flag = true;
		}
		if (flag) {
			resultTable.setRowHeight(80);
		}
		resultTable.setShowGrid(true);
		resultTable.setGridColor(Color.GRAY);

		messageLabel.setText("✅ 결과: " + result.size() + "건");
	}
}