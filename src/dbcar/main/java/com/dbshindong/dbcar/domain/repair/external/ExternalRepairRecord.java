package dbcar.main.java.com.dbshindong.dbcar.domain.repair.external;

import java.sql.Date;
import java.util.Objects;

public class ExternalRepairRecord {
	private final int external_repair_id;
	private final int car_id;
	private final int shop_id;
	private final int company_id;
	private final int customer_id;
	private final String content;
	private final Date repair_date;
	private final int cost;
	private final Date due_date;
	private final String note;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public ExternalRepairRecord(int external_repair_id, int car_id, int shop_id, int company_id, int customer_id,
			String content, Date repair_date, int cost, Date due_date, String note) {
		validate(external_repair_id, car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date);

		this.external_repair_id = external_repair_id;
		this.car_id = car_id;
		this.shop_id = shop_id;
		this.company_id = company_id;
		this.customer_id = customer_id;
		this.content = content;
		this.repair_date = repair_date;
		this.cost = cost;
		this.due_date = due_date;
		this.note = note;
	}

	public ExternalRepairRecord(int car_id, int shop_id, int company_id, int customer_id, String content,
			Date repair_date, int cost, Date due_date, String note) {
		validate(-1, car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date);

		this.external_repair_id = -1;
		this.car_id = car_id;
		this.shop_id = shop_id;
		this.company_id = company_id;
		this.customer_id = customer_id;
		this.content = content;
		this.repair_date = repair_date;
		this.cost = cost;
		this.due_date = due_date;
		this.note = note;
	}

	private void validate(int external_repair_id, int car_id, int shop_id, int company_id, int customer_id,
			String content, Date repair_date, int cost, Date due_date) {
		Objects.requireNonNull(external_repair_id, String.format(NULL_MESSAGE, "external_repair_id"));
		Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, "car_id"));
		Objects.requireNonNull(shop_id, String.format(NULL_MESSAGE, "shop_id"));
		Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, "company_id"));
		Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, "customer_id"));
		Objects.requireNonNull(content, String.format(NULL_MESSAGE, "content"));
		Objects.requireNonNull(repair_date, String.format(NULL_MESSAGE, "repair_date"));
		Objects.requireNonNull(cost, String.format(NULL_MESSAGE, "cost"));
		Objects.requireNonNull(due_date, String.format(NULL_MESSAGE, "due_date"));
	}

	public int getExternal_repair_id() {
		return external_repair_id;
	}

	public int getCar_id() {
		return car_id;
	}

	public int getShop_id() {
		return shop_id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public String getContent() {
		return content;
	}

	public Date getRepair_date() {
		return repair_date;
	}

	public int getCost() {
		return cost;
	}

	public Date getDue_date() {
		return due_date;
	}

	public String getNote() {
		return note;
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"external_repair_id\": %d, \"car_id\": %d, \"shop_id\": %d, \"company_id\": %d, "
						+ "\"customer_id\": %d, \"content\": \"%s\", \"repair_date\": \"%s\", "
						+ "\"cost\": %d, \"due_date\": \"%s\", \"note\": \"%s\" }",
				external_repair_id, car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date,
				note);
	}
}