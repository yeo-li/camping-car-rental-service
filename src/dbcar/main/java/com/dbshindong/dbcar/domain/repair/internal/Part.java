package dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal;

import java.sql.Date;
import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.exception.InvalidPartException;

public class Part {
	private final Integer part_id;
	private final String name;
	private final Integer unit_price;
	private final Integer stock_quantity;
	private final Date stock_date;
	private final String supplier_name;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	private final static String PART_ID = "부품 등록 ID";
	private final static String NAME = "부품";
	private final static String UNIT_PRICE = "단가";
	private final static String STOCK_QUANTITY = "재고 수량";
	private final static String STOCK_DATE = "입고 날짜";
	private final static String SUPPLIER_NAME = "공급업체명";

	public Part(Integer part_id, String name, Integer unit_price, Integer stock_quantity, String stock_date,
			String supplier_name) {
		this.validate(part_id, name, unit_price, stock_quantity, stock_date, supplier_name);
		this.part_id = part_id;
		this.name = name;
		this.unit_price = unit_price;
		this.stock_quantity = stock_quantity;
		this.stock_date = java.sql.Date.valueOf(stock_date);
		this.supplier_name = supplier_name;
	}

	public Part(String name, Integer unit_price, Integer stock_quantity, String stock_date, String supplier_name) {
		this.validate(-1, name, unit_price, stock_quantity, stock_date, supplier_name);
		this.part_id = -1;
		this.name = name;
		this.unit_price = unit_price;
		this.stock_quantity = stock_quantity;
		this.stock_date = java.sql.Date.valueOf(stock_date);
		this.supplier_name = supplier_name;
	}

	private void validate(Integer part_id, String name, Integer unit_price, Integer stock_quantity, String stock_date,
			String supplier_name) {
		try {
			Objects.requireNonNull(part_id, String.format(NULL_MESSAGE, PART_ID));
			Validator.requireNonBlank(name, String.format(NULL_MESSAGE, NAME));
			Objects.requireNonNull(unit_price, String.format(NULL_MESSAGE, UNIT_PRICE));
			Objects.requireNonNull(stock_quantity, String.format(NULL_MESSAGE, STOCK_QUANTITY));
			Validator.requireNonBlank(stock_date, String.format(NULL_MESSAGE, STOCK_DATE));
			Validator.requireNonBlank(supplier_name, String.format(NULL_MESSAGE, SUPPLIER_NAME));
		} catch (NullPointerException e) {
			throw new InvalidPartException(e.getMessage(), e);
		}

		if (unit_price < 0) {
			throw new InvalidPartException(UNIT_PRICE + "의 입력값이 올바르지 습니다.");
		}

		if (stock_quantity < 0) {
			throw new InvalidPartException(STOCK_QUANTITY + "의 입력값이 잘못되었습니다.");
		}

		if (!Validator.isValidDate(stock_date)) {
			throw new InvalidPartException(STOCK_DATE + "의 입력값이 잘못되었습니다.");
		}

	}

	public int getPart_id() {
		return part_id;
	}

	public String getName() {
		return name;
	}

	public int getUnit_price() {
		return unit_price;
	}

	public int getStock_quantity() {
		return stock_quantity;
	}

	public Date getStock_date() {
		return stock_date;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"part_id\": %d, \"name\": \"%s\", \"unit_price\": %d, \"stock_quantity\": %d, "
						+ "\"stock_date\": \"%s\", \"supplier_name\": \"%s\" }",
				part_id, name, unit_price, stock_quantity, stock_date.toString(), supplier_name);
	}
}