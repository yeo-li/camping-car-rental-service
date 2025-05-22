package dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal;

import java.sql.Date;
import java.util.Objects;

public class Part {
	private final int part_id;
	private final String name;
	private final int unit_price;
	private final int stock_quantity;
	private final Date stock_date;
	private final String supplier_name;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public Part(int part_id, String name, int unit_price, int stock_quantity, Date stock_date, String supplier_name) {
		this.validate(part_id, name, unit_price, stock_quantity, stock_date, supplier_name);
		this.part_id = part_id;
		this.name = name;
		this.unit_price = unit_price;
		this.stock_quantity = stock_quantity;
		this.stock_date = stock_date;
		this.supplier_name = supplier_name;
	}

	public Part(String name, int unit_price, int stock_quantity, Date stock_date, String supplier_name) {
		this.validate(-1, name, unit_price, stock_quantity, stock_date, supplier_name);
		this.part_id = -1;
		this.name = name;
		this.unit_price = unit_price;
		this.stock_quantity = stock_quantity;
		this.stock_date = stock_date;
		this.supplier_name = supplier_name;
	}

	private void validate(int part_id, String name, int unit_price, int stock_quantity, Date stock_date,
			String supplier_name) {
		Objects.requireNonNull(part_id, String.format(NULL_MESSAGE, "part_id"));
		Objects.requireNonNull(name, String.format(NULL_MESSAGE, "name"));
		Objects.requireNonNull(unit_price, String.format(NULL_MESSAGE, "unit_price"));
		Objects.requireNonNull(stock_quantity, String.format(NULL_MESSAGE, "stock_quantity"));
		Objects.requireNonNull(stock_date, String.format(NULL_MESSAGE, "stock_date"));
		Objects.requireNonNull(supplier_name, String.format(NULL_MESSAGE, "supplier_name"));
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