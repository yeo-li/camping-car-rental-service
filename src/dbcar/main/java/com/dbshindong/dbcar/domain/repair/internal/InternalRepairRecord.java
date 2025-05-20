package dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal;

import java.sql.Date;
import java.util.Objects;

public class InternalRepairRecord {
	private final int internal_repair_id;
	private final int car_id;
	private final Integer part_id; // nullable
	private final Date repair_date;
	private final int duration_minutes;
	private final int employee_id;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public InternalRepairRecord(int internal_repair_id, int car_id, Integer part_id, Date repair_date, int duration_minutes, int employee_id) {
		validate(internal_repair_id, car_id, repair_date, duration_minutes, employee_id);
		this.internal_repair_id = internal_repair_id;
		this.car_id = car_id;
		this.part_id = part_id;
		this.repair_date = repair_date;
		this.duration_minutes = duration_minutes;
		this.employee_id = employee_id;
	}

	public InternalRepairRecord(int car_id, Integer part_id, Date repair_date, int duration_minutes, int employee_id) {
		validate(-1, car_id, repair_date, duration_minutes, employee_id);
		this.internal_repair_id = -1;
		this.car_id = car_id;
		this.part_id = part_id;
		this.repair_date = repair_date;
		this.duration_minutes = duration_minutes;
		this.employee_id = employee_id;
	}

	private void validate(int internal_repair_id, int car_id, Date repair_date, int duration_minutes, int employee_id) {
		Objects.requireNonNull(internal_repair_id, String.format(NULL_MESSAGE, "internal_repair_id"));
		Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, "car_id"));
		Objects.requireNonNull(repair_date, String.format(NULL_MESSAGE, "repair_date"));
		Objects.requireNonNull(duration_minutes, String.format(NULL_MESSAGE, "duration_minutes"));
		Objects.requireNonNull(employee_id, String.format(NULL_MESSAGE, "employee_id"));
	}

	public int getInternal_repair_id() {
		return internal_repair_id;
	}

	public int getCar_id() {
		return car_id;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public Date getRepair_date() {
		return repair_date;
	}

	public int getDuration_minutes() {
		return duration_minutes;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	@Override
	public String toString() {
		return String.format(
			"{ \"internal_repair_id\": %d, \"car_id\": %d, \"part_id\": %s, \"repair_date\": \"%s\", \"duration_minutes\": %d, \"employee_id\": %d }",
			internal_repair_id, car_id, part_id == null ? "null" : part_id.toString(), repair_date.toString(), duration_minutes, employee_id);
	}
}