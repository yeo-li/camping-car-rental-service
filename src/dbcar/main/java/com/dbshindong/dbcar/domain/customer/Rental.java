package dbcar.main.java.com.dbshindong.dbcar.domain.customer;

import java.sql.Date;
import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.customer.exception.InvalidRentalException;

public class Rental {
	private final Integer rental_id;
	private final Integer car_id;
	private final Integer customer_id;
	private final Integer company_id;
	private final Date start_date;
	private final Integer rental_period;
	private final Integer total_charge;
	private final Date due_date;
	private final String extra_charge_detail;
	private final Integer extra_charge;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	private static final String RENTAL_ID = "캠핑카 대여 ID";
	private static final String CAR_ID = "캠핑카 등록 ID";
	private static final String CUSTOMER_ID = "고객 ID";
	private static final String COMPANY_ID = "캠핑카 대여 회사 ID";
	private static final String START_DATE = "대여 시작일";
	private static final String RENTAL_PERIOD = "대여 기간";
	private static final String TOTAL_CHARGE = "청구 요금";
	private static final String DUE_DATE = "납입 기한";
	private static final String EXTRA_CHARGE_DETAIL = "기타 청구 내역";
	private static final String EXTRA_CHARGE_AMOUNT = "기타 청구 요금 정보";

	public Rental(Integer rental_id, Integer car_id, Integer customer_id, Integer company_id, String start_date,
			Integer rental_period, Integer total_charge, String due_date, String extra_charge_detail,
			Integer extra_charge) {
		this.validate(rental_id, car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date,
				extra_charge_detail, extra_charge);

		this.rental_id = rental_id;
		this.car_id = car_id;
		this.customer_id = customer_id;
		this.company_id = company_id;
		this.start_date = java.sql.Date.valueOf(start_date);
		this.rental_period = rental_period;
		this.total_charge = total_charge;
		this.due_date = java.sql.Date.valueOf(due_date);
		this.extra_charge_detail = extra_charge_detail;
		this.extra_charge = extra_charge;
	}

	public Rental(Integer car_id, Integer customer_id, Integer company_id, String start_date, Integer rental_period,
			Integer total_charge, String due_date, String extra_charge_detail, Integer extra_charge) {

		this.validate(-1, car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date,

				extra_charge_detail, extra_charge);
		this.rental_id = -1;
		this.car_id = car_id;
		this.customer_id = customer_id;
		this.company_id = company_id;
		this.start_date = java.sql.Date.valueOf(start_date);
		this.rental_period = rental_period;
		this.total_charge = total_charge;
		this.due_date = java.sql.Date.valueOf(due_date);
		this.extra_charge_detail = extra_charge_detail;
		this.extra_charge = extra_charge;
	}

	private void validate(Integer rental_id, Integer car_id, Integer customer_id, Integer company_id, String start_date,
			Integer rental_period, Integer total_charge, String due_date, String extra_charge_detail,
			Integer extra_charge) {
		try {
			Objects.requireNonNull(rental_id, String.format(NULL_MESSAGE, RENTAL_ID));
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, CAR_ID));
			Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, CUSTOMER_ID));
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, COMPANY_ID));
			Validator.requireNonBlank(start_date, String.format(NULL_MESSAGE, START_DATE));
			Objects.requireNonNull(rental_period, String.format(NULL_MESSAGE, RENTAL_PERIOD));
			Objects.requireNonNull(total_charge, String.format(NULL_MESSAGE, TOTAL_CHARGE));
			Validator.requireNonBlank(due_date, String.format(NULL_MESSAGE, DUE_DATE));
		} catch (NullPointerException e) {
			throw new InvalidRentalException(e.getMessage(), e);
		}

		if (!Validator.isValidDate(start_date)) {
			throw new InvalidRentalException(START_DATE + "의 입력값이 올바르지 않습니다.");
		}

		if (!Validator.isValidDate(due_date)) {
			throw new InvalidRentalException(DUE_DATE + "의 입력값이 올바르지 않습니다.");
		}

		if (rental_id != -1 && rental_id <= 0) {
			throw new InvalidRentalException(RENTAL_ID + "의 입력값이 올바르지 않습니다.");
		}

		if (car_id <= 0) {
			throw new InvalidRentalException(CAR_ID + "의 입력값이 올바르지 않습니다.");
		}

		if (customer_id <= 0) {
			throw new InvalidRentalException(CUSTOMER_ID + "의 입력값이 올바르지 않습니다.");
		}

		if (company_id <= 0) {
			throw new InvalidRentalException(COMPANY_ID + "의 입력값이 올바르지 않습니다.");
		}

		if (rental_period <= 0) {
			throw new InvalidRentalException(RENTAL_PERIOD + "의 입력값이 올바르지 않습니다.");
		}

		if (total_charge <= 0) {
			throw new InvalidRentalException(TOTAL_CHARGE + "의 입력값이 올바르지 않습니다.");
		}


		if (extra_charge != null && extra_charge < 0) {


			throw new InvalidRentalException(EXTRA_CHARGE_AMOUNT + "의 입력값이 올바르지 않습니다.");

		}

	}

	public int getRental_id() {
		return rental_id;
	}

	public int getCar_id() {
		return car_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public int getCompany_id() {
		return company_id;
	}

	public Date getStart_date() {
		return start_date;
	}

	public int getRental_period() {
		return rental_period;
	}

	public int getTotal_charge() {
		return total_charge;
	}

	public Date getDue_date() {
		return due_date;
	}

	public String getExtra_charge_detail() {
		return extra_charge_detail;
	}

	public Integer getExtra_charge() {
		return extra_charge;
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"rental_id\": %d, \"car_id\": %d, \"customer_id\": %d, \"company_id\": %d, "
						+ "\"start_date\": \"%s\", \"rental_period\": %d, \"total_charge\": %d, "
						+ "\"due_date\": \"%s\", \"extra_charge_detail\": \"%s\", \"extra_charge\": %d }",
				rental_id, car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date,
				extra_charge_detail, extra_charge);
	}
}