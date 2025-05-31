package dbcar.main.java.com.dbshindong.dbcar.domain.repair.external;

import java.sql.Date;
import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.exception.InvalidExternalRepairRecordException;

public class ExternalRepairRecord {
	private final Integer external_repair_id;
	private final Integer car_id;
	private final Integer shop_id;
	private final Integer company_id;
	private final Integer customer_id;
	private final String content;
	private final Date repair_date;
	private final Integer cost;
	private final Date due_date;
	private final String note;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public ExternalRepairRecord(Integer external_repair_id, Integer car_id, Integer shop_id, Integer company_id,
			Integer customer_id, String content, String repair_date, Integer cost, String due_date, String note) {
		this.validate(external_repair_id, car_id, shop_id, company_id, customer_id, content, repair_date, cost,
				due_date, note);

		this.external_repair_id = external_repair_id;
		this.car_id = car_id;
		this.shop_id = shop_id;
		this.company_id = company_id;
		this.customer_id = customer_id;
		this.content = content;
		this.repair_date = java.sql.Date.valueOf(repair_date);
		this.cost = cost;
		this.due_date = java.sql.Date.valueOf(due_date);
		this.note = note;
	}

	public ExternalRepairRecord(Integer car_id, Integer shop_id, Integer company_id, Integer customer_id,
			String content, String repair_date, Integer cost, String due_date, String note) {
		this.validate(-1, car_id, shop_id, company_id, customer_id, content, repair_date, cost, due_date, note);

		this.external_repair_id = -1;
		this.car_id = car_id;
		this.shop_id = shop_id;
		this.company_id = company_id;
		this.customer_id = customer_id;
		this.content = content;
		this.repair_date = java.sql.Date.valueOf(repair_date);
		this.cost = cost;
		this.due_date = java.sql.Date.valueOf(due_date);
		this.note = note;
	}

	private void validate(Integer external_repair_id, Integer car_id, Integer shop_id, Integer company_id,
			Integer customer_id, String content, String repair_date, Integer cost, String due_date, String note) {
		try {
			Objects.requireNonNull(external_repair_id, String.format(NULL_MESSAGE, "external_repair_id"));
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, "car_id"));
			Objects.requireNonNull(shop_id, String.format(NULL_MESSAGE, "shop_id"));
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, "company_id"));
			Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, "customer_id"));
			Validator.requireNonBlank(content, String.format(NULL_MESSAGE, "content"));
			Validator.requireNonBlank(repair_date, String.format(NULL_MESSAGE, "repair_date"));
			Objects.requireNonNull(cost, String.format(NULL_MESSAGE, "cost"));
			Validator.requireNonBlank(due_date, String.format(NULL_MESSAGE, "due_date"));
		} catch (NullPointerException e) {
			throw new InvalidExternalRepairRecordException(e.getMessage(), e);
		}

		if (car_id <= 0) {
			throw new InvalidExternalRepairRecordException("캠핑카 아이디의 입력값이 잘못되었습니다.");
		}

		if (shop_id <= 0) {
			throw new InvalidExternalRepairRecordException("외부정비소 아이디의 입력값이 잘못되었습니다.");
		}

		if (company_id <= 0) {
			throw new InvalidExternalRepairRecordException("캠핑카 대여 회사 아이디의 입력값이 올바르지 않습니다.");
		}

		if (customer_id <= 0) {
			throw new InvalidExternalRepairRecordException("고객 아이디의 입력값이 올바르지 않습니다.");
		}

		if (!Validator.isValidDate(repair_date)) {
			throw new InvalidExternalRepairRecordException("정비 날짜의 입력값이 올바르지 않습니다.");
		}

		if (!Validator.isValidDate(due_date)) {
			throw new InvalidExternalRepairRecordException("정비 날의 입력값이 올바르지 않습니다.");
		}
	}

	public Integer getExternal_repair_id() {
		return external_repair_id;
	}

	public Integer getCar_id() {
		return car_id;
	}

	public Integer getShop_id() {
		return shop_id;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public String getContent() {
		return content;
	}

	public Date getRepair_date() {
		return repair_date;
	}

	public Integer getCost() {
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