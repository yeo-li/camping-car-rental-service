package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.sql.Date;

public record UpdateInternalRepairRecordRequest(Integer car_id, Integer part_id, String repair_date,
		Integer duration_minutes, Integer employee_id) {

}
