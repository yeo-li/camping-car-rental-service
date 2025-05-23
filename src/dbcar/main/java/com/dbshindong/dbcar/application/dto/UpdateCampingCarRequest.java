package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.sql.Date;

public record UpdateCampingCarRequest(String name, String plate_number, Integer capacity, byte[] image,
		String description, Integer rental_price, Integer company_id, Date registered_date) {

}
