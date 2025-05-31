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
			Objects.requireNonNull(rental_id, String.format(NULL_MESSAGE, "rental_id"));
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, "car_id"));
			Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, "customer_id"));
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, "company_id"));
			Validator.requireNonBlank(start_date, String.format(NULL_MESSAGE, "start_date"));
			Objects.requireNonNull(rental_period, String.format(NULL_MESSAGE, "rental_period"));
			Objects.requireNonNull(total_charge, String.format(NULL_MESSAGE, "total_charge"));
			Validator.requireNonBlank(due_date, String.format(NULL_MESSAGE, "due_date"));
		} catch (NullPointerException e) {
			throw new InvalidRentalException(e.getMessage(), e);
		}

		if (!Validator.isValidDate(start_date)) {
			throw new InvalidRentalException("납인기한의 입력값이 올바르지 않습니다.");
		}

		if (!Validator.isValidDate(due_date)) {
			throw new InvalidRentalException("납인기한의 입력값이 올바르지 않습니다.");
		}

		if (rental_id <= 0) {
			throw new InvalidRentalException("캠핑카 아이디의 입력값이 올바르지 않습니다.");
		}

		if (car_id <= 0) {
			throw new InvalidRentalException("캠핑카 아이디의 입력값이 올바르지 않습니다.");
		}

		if (customer_id <= 0) {
			throw new InvalidRentalException("고객 아이디의 입력값이 올바르지 않습니다.");
		}

		if (company_id <= 0) {
			throw new InvalidRentalException("캠핑카 대여 회사 아이디의 입력값이 올바르지 않습니다.");
		}

		if (rental_period <= 0) {
			throw new InvalidRentalException("대여기간의 입력값이 올바르지 않습니다.");
		}

		if (total_charge <= 0) {
			throw new InvalidRentalException("청구요금의 입력값이 올바르지 않습니다.");
		}

		if (extra_charge <= 0) {
			throw new InvalidRentalException("기타청구요금의 입력값이 올바르지 않습니다.");
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