package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.sql.Date;

public record UpdateExternalRepairRecordRequest(Integer car_id, Integer shop_id, Integer company_id,
		Integer customer_id, String content, String repair_date, Integer cost, String due_date, String note) {

}
