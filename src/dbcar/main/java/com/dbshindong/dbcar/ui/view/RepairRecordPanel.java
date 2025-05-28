package dbcar.main.java.com.dbshindong.dbcar.ui.view;

import dbcar.main.java.com.dbshindong.dbcar.config.AppConfig;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.CampingCar;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.ExternalRepairShop;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.InternalRepairRecord;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.Part;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RepairRecordPanel extends JPanel {

	private final AppConfig ac = AppConfig.getInstance();
	private JTable carTable, recordTable, detailTable;
	private JRadioButton internalRadio, externalRadio;
	private DefaultTableModel carModel, recordModel, detailModel;
	private JLabel noRecordLabel, noDetailLabel;

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
		carModel = new DefaultTableModel(new String[] { "ID", "이름", "차량번호" }, 0) {
			public boolean isCellEditable(int r, int c) {
				return false;
			}
		};
		carTable = new JTable(carModel);
		carTable.setFillsViewportHeight(true);
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
		JScrollPane detailScroll = new JScrollPane(detailTable);
		detailScroll.setBorder(new TitledBorder("세부 정보"));

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
	}

	private void loadCampingCars() {
		List<CampingCar> cars = ac.repairRecordFetchService().fetchCampingCars();
		carModel.setRowCount(0);
		for (CampingCar car : cars) {
			carModel.addRow(new Object[] { car.getCar_id(), car.getName(), car.getPlate_number() });
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

		internalRadio.addActionListener(e -> clearTables());
		externalRadio.addActionListener(e -> clearTables());
	}

	private void clearTables() {
		recordModel.setRowCount(0);
		detailModel.setRowCount(0);
		recordModel.setColumnIdentifiers(new Object[] {});
		detailModel.setColumnIdentifiers(new Object[] {});
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