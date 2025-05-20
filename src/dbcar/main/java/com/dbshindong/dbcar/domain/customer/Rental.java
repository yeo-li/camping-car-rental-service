package dbcar.main.java.com.dbshindong.dbcar.domain.customer;

import java.sql.Date;
import java.util.Objects;

public class Rental {
	private final int rental_id;
	private final int car_id;
	private final int customer_id;
	private final int company_id;
	private final Date start_date;
	private final int rental_period;
	private final int total_charge;
	private final Date due_date;
	private final String extra_charge_detail;
	private final Integer extra_charge;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public Rental(int rental_id, int car_id, int customer_id, int company_id, Date start_date, Integer rental_period,
			int total_charge, Date due_date, String extra_charge_detail, int extra_charge) {
		validate(rental_id, car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date);

		this.rental_id = rental_id;
		this.car_id = car_id;
		this.customer_id = customer_id;
		this.company_id = company_id;
		this.start_date = start_date;
		this.rental_period = rental_period;
		this.total_charge = total_charge;
		this.due_date = due_date;
		this.extra_charge_detail = extra_charge_detail;
		this.extra_charge = extra_charge;
	}

	public Rental(int car_id, int customer_id, int company_id, Date start_date, int rental_period, int total_charge,
			Date due_date, String extra_charge_detail, Integer extra_charge) {
		this.validate(-1, car_id, customer_id, company_id, start_date, rental_period, total_charge, due_date);
		this.rental_id = -1;
		this.car_id = car_id;
		this.customer_id = customer_id;
		this.company_id = company_id;
		this.start_date = start_date;
		this.rental_period = rental_period;
		this.total_charge = total_charge;
		this.due_date = due_date;
		this.extra_charge_detail = extra_charge_detail;
		this.extra_charge = extra_charge;
	}

	private void validate(int rental_id, int car_id, int customer_id, int company_id, Date start_date,
			int rental_period, int total_charge, Date due_date) {
		Objects.requireNonNull(rental_id, String.format(NULL_MESSAGE, "rental_id"));
		Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, "car_id"));
		Objects.requireNonNull(customer_id, String.format(NULL_MESSAGE, "customer_id"));
		Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, "company_id"));
		Objects.requireNonNull(start_date, String.format(NULL_MESSAGE, "start_date"));
		Objects.requireNonNull(rental_period, String.format(NULL_MESSAGE, "rental_period"));
		Objects.requireNonNull(total_charge, String.format(NULL_MESSAGE, "total_charge"));
		Objects.requireNonNull(due_date, String.format(NULL_MESSAGE, "due_date"));
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