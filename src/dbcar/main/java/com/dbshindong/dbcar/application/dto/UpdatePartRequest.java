package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.sql.Date;

public record UpdatePartRequest(String name, Integer unit_price, Integer stock_quantity, Date stock_date,
		String supplier_name) {

}
