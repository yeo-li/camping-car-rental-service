package dbcar.main.java.com.dbshindong.dbcar.application.dto;

import java.time.LocalDate;

public record DateAvailabilityRequest(LocalDate date, boolean available) {

}
