package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.sql.Date;

public record UpdateRentalRequest(Integer car_id, Integer customer_id, Integer company_id, Date start_date,
		Integer rental_period, Integer total_charge, Date due_date, String extra_charge_detail, Integer extra_charge) {

}
