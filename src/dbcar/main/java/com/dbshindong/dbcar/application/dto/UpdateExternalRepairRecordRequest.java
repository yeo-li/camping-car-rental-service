package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.sql.Date;

public record UpdateExternalRepairRecordRequest(Integer car_id, Integer shop_id, Integer company_id,
		Integer customer_id, String content, Date repair_date, Integer cost, Date due_date, String note) {

}
