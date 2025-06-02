package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

public class RepairRecordPanel extends JPanel {

	private final AppConfig ac = AppConfig.getInstance();
	private JTable carTable, recordTable, detailTable;
	private JRadioButton internalRadio, externalRadio;
	private DefaultTableModel carModel, recordModel, detailModel;
	private JLabel noRecordLabel, noDetailLabel;
	private JScrollPane detailScroll;

	public RepairRecordPanel() {
		setLayout(new BorderLayout());

		// 좌측 상단 - 토글 버튼
		JPanel togglePanel = new JPanel(new FlowLayout());
		internalRadio = new JRadioButton("내부 정비");
		externalRadio = new JRadioButton("외부 정비");
		ButtonGroup group = new ButtonGroup();
		group.add(internalRadio);
		group.add(externalRadio);
		togglePanel.add(internalRadio);
		togglePanel.add(externalRadio);
		internalRadio.setSelected(true);
		add(togglePanel, BorderLayout.NORTH);

		// 우측 - 캠핑카 테이블
		carModel = new DefaultTableModel(new String[] { "ID", "이름", "차량번호", "차량 사진" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		carTable = new JTable(carModel);
		carTable.setFillsViewportHeight(true);

		// 이미지 컬럼 렌더러 설정
		carTable.setRowHeight(80); // 이미지 크기에 맞춰 행 높이 조정
		TableColumn imageColumn = carTable.getColumnModel().getColumn(3); // "차량 사진" 컬럼
		imageColumn.setCellRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof ImageIcon icon) {
					JLabel label = new JLabel(icon);
					label.setHorizontalAlignment(SwingConstants.CENTER);
					return label;
				} else {
					return new JLabel("이미지 없음");
				}
			}
		});

		JScrollPane carScroll = new JScrollPane(carTable);
		carScroll.setBorder(new TitledBorder("캠핑카 목록"));

		// 좌측 중앙 - 정비 기록 테이블
		recordModel = new DefaultTableModel() {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		recordTable = new JTable(recordModel);
		recordTable.setFillsViewportHeight(true);
		JScrollPane recordScroll = new JScrollPane(recordTable);
		recordScroll.setBorder(new TitledBorder("정비 기록"));

		// 좌측 하단 - 세부 정보 테이블
		detailModel = new DefaultTableModel() {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		detailTable = new JTable(detailModel);
		detailTable.setFillsViewportHeight(true);
		detailScroll = new JScrollPane(detailTable);
		detailScroll.setBorder(new TitledBorder("부품 세부 정보"));

		// 좌측 전체
		JSplitPane leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, recordScroll, detailScroll);
		leftSplitPane.setResizeWeight(0.5);
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(leftSplitPane, BorderLayout.CENTER);

		// 분할
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, carScroll);
		splitPane.setResizeWeight(0.4);
		add(splitPane, BorderLayout.CENTER);

		// 안내 라벨
		noRecordLabel = new JLabel("조회된 정비 기록이 없습니다.");
		noDetailLabel = new JLabel("조회된 데이터가 없습니다.");
		noRecordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		noDetailLabel.setHorizontalAlignment(SwingConstants.CENTER);

		loadCampingCars();
		setupListeners();
		styleTable(carTable);
		styleTable(recordTable);
		styleTable(detailTable);
	}

	private void styleTable(JTable table) {
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.setShowGrid(true);
		table.setGridColor(Color.GRAY);
		if (table != carTable) {
			table.setRowHeight(25);
		}
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTableHeader().setReorderingAllowed(false);
	}

	private void loadCampingCars() {
		List<CampingCar> cars = ac.repairRecordFetchService().fetchCampingCars();
		carModel.setRowCount(0);
		for (CampingCar car : cars) {
			ImageIcon imageIcon = createImageIcon(car.getImage());
			carModel.addRow(new Object[] { car.getCar_id(), car.getName(), car.getPlate_number(), imageIcon // ← 여기에 이미지
																											// 넣기
			});
		}
	}

	private ImageIcon createImageIcon(byte[] imageData) {
		if (imageData == null || imageData.length == 0) {
			return null;
		}
		try {
			ImageIcon icon = new ImageIcon(imageData);
			Image image = icon.getImage().getScaledInstance(100, 70, Image.SCALE_SMOOTH); // 적절한 크기로 조정
			return new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private void setupListeners() {
		carTable.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				int selected = carTable.getSelectedRow();
				if (selected >= 0) {
					int carId = (Integer) carModel.getValueAt(selected, 0);
					loadRepairRecords(carId);
				}
			}
		});

		recordTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = recordTable.getSelectedRow();
				if (internalRadio.isSelected() && row >= 0) {
					Object partObj = recordModel.getValueAt(row, 1);
					if (partObj instanceof Integer partId)
						showPartDetails(partId);
				} else if (externalRadio.isSelected() && row >= 0) {
					Object shopObj = recordModel.getValueAt(row, 1);
					if (shopObj instanceof Integer shopId)
						showShopDetails(shopId);
				}
			}
		});

		internalRadio.addActionListener(e -> {
			clearTables();
			updateDetailTitle("부품 세부 정보");
		});
		externalRadio.addActionListener(e -> {
			clearTables();
			updateDetailTitle("외부 정비소 세부 정보");
		});
	}

	private void clearTables() {
		recordModel.setRowCount(0);
		detailModel.setRowCount(0);
		recordModel.setColumnIdentifiers(new Object[] {});
		detailModel.setColumnIdentifiers(new Object[] {});
	}

	private void updateDetailTitle(String title) {
		detailScroll.setBorder(new TitledBorder(title));
		detailScroll.repaint();
	}

	private void loadRepairRecords(int carId) {
		if (internalRadio.isSelected()) {
			List<InternalRepairRecord> records = ac.repairRecordFetchService().fetchInternalRepairRecord(carId);
			if (records == null || records.isEmpty()) {
				recordModel.setRowCount(0);
				recordModel.setColumnIdentifiers(new String[] { "정비ID", "부품ID", "날짜", "소요시간", "직원ID" });
				recordModel.addRow(new Object[] { "조회된 정비 기록이 없습니다." });
				return;
			}
			recordModel.setColumnIdentifiers(new String[] { "정비ID", "부품ID", "날짜", "소요시간", "직원ID" });
			recordModel.setRowCount(0);
			for (InternalRepairRecord r : records) {
				recordModel.addRow(new Object[] { r.getInternal_repair_id(), r.getPart_id(), r.getRepair_date(),
						r.getDuration_minutes(), r.getEmployee_id() });
			}
		} else {
			List<ExternalRepairRecord> records = ac.repairRecordFetchService().fetchExternalRepairRecord(carId);
			if (records == null || records.isEmpty()) {
				recordModel.setRowCount(0);
				recordModel.setColumnIdentifiers(new String[] { "정비ID", "정비소ID", "내용", "정비일", "비용" });
				recordModel.addRow(new Object[] { "조회된 정비 기록이 없습니다." });
				return;
			}
			recordModel.setColumnIdentifiers(new String[] { "정비ID", "정비소ID", "내용", "정비일", "비용" });
			recordModel.setRowCount(0);
			for (ExternalRepairRecord r : records) {
				recordModel.addRow(new Object[] { r.getExternal_repair_id(), r.getShop_id(), r.getContent(),
						r.getRepair_date(), r.getCost() });
			}
		}
		detailModel.setRowCount(0);
	}

	private void showPartDetails(int partId) {
		Part part = ac.repairRecordFetchService().fetchPart(partId);
		if (part == null) {
			detailModel.setRowCount(0);
			detailModel.setColumnIdentifiers(new String[] { "조회된 데이터가 없습니다." });
			return;
		}
		detailModel.setColumnIdentifiers(new String[] { "부품ID", "부품명", "단가", "재고", "입고날짜", "공급사명" });
		detailModel.setRowCount(0);
		detailModel.addRow(new Object[] { part.getPart_id(), part.getName(), part.getUnit_price(),
				part.getStock_quantity(), part.getStock_date(), part.getSupplier_name() });
	}

	private void showShopDetails(int shopId) {
		ExternalRepairShop shop = ac.repairRecordFetchService().fetchExternalRepairShop(shopId);
		if (shop == null) {
			detailModel.setRowCount(0);
			detailModel.setColumnIdentifiers(new String[] { "조회된 데이터가 없습니다." });
			return;
		}
		detailModel.setColumnIdentifiers(new String[] { "정비소ID", "정비소명", "주소", "전화번호", "담당자", "이메일" });
		detailModel.setRowCount(0);
		detailModel.addRow(new Object[] { shop.getShop_id(), shop.getName(), shop.getAddress(), shop.getPhone(),
				shop.getManager_name(), shop.getManager_email() });
	}
}
