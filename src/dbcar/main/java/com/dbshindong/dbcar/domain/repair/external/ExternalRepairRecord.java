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

	private static final String EXTERNAL_REPAIR_ID = "캠핑카 정비 기록 ID";
	private static final String CAR_ID = "캠핑카 등록 ID";
	private static final String SHOP_ID = "캠핑카 정비소 ID";
	private static final String COMPANY_ID = "캠핑카 대여 회사 ID";
	private static final String CUSTOMER_ID = "고객 ID";
	private static final String CONTENT = "정비 내역";
	private static final String REPAIR_DATE = "수리 날짜";
	private static final String COST = "수리 비용";
	private static final String DUE_DATE = "납입 기한";
	private static final String NOTE = "기타 정비 내역 정보";

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
			Objects.requireNonNull(external_repair_id, String.format(NULL_MESSAGE, EXTERNAL_REPAIR_ID));
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, CAR_ID));
			Objects.requireNonNull(shop_id, String.format(NULL_MESSAGE, SHOP_ID));
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, COMPANY_ID));
			Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, CUSTOMER_ID));
			Validator.requireNonBlank(content, String.format(NULL_MESSAGE, CONTENT));
			Validator.requireNonBlank(repair_date, String.format(NULL_MESSAGE, REPAIR_DATE));
			Objects.requireNonNull(cost, String.format(NULL_MESSAGE, COST));
			Validator.requireNonBlank(due_date, String.format(NULL_MESSAGE, DUE_DATE));
		} catch (NullPointerException e) {
			throw new InvalidExternalRepairRecordException(e.getMessage(), e);
		}

		if (car_id <= 0) {
			throw new InvalidExternalRepairRecordException(CAR_ID + "의 입력값이 잘못되었습니다.");
		}

		if (shop_id <= 0) {
			throw new InvalidExternalRepairRecordException(SHOP_ID + "의 입력값이 잘못되었습니다.");
		}

		if (company_id <= 0) {
			throw new InvalidExternalRepairRecordException(COMPANY_ID + "의 입력값이 올바르지 않습니다.");
		}

		if (customer_id <= 0) {
			throw new InvalidExternalRepairRecordException(CUSTOMER_ID + "의 입력값이 올바르지 않습니다.");
		}

		if (!Validator.isValidDate(repair_date)) {
			throw new InvalidExternalRepairRecordException(REPAIR_DATE + "의 입력값이 올바르지 않습니다.");
		}

		if (!Validator.isValidDate(due_date)) {
			throw new InvalidExternalRepairRecordException(DUE_DATE + "의 입력값이 올바르지 않습니다.");
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