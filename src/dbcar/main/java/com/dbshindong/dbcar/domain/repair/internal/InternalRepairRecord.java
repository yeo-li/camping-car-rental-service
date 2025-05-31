package dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal;

import java.sql.Date;
import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.exception.InvalidInternalRepairRecordException;

public class InternalRepairRecord {
	private final int internal_repair_id;
	private final int car_id;
	private final Integer part_id; // nullable
	private final Date repair_date;
	private final int duration_minutes;
	private final int employee_id;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	private static final String INTERNAL_REPAIR_ID = "자체 정비 등록 ID";
	private static final String CAR_ID = "캠핑카 등록 ID";
	private static final String PART_ID = "부픔 등록 ID";
	private static final String REPAIR_DATE = "정비 일자";
	private static final String DURATION = "정비 소요 시간";
	private static final String EMPLOYEE_ID = "정비 담당자 ID";

	public InternalRepairRecord(Integer internal_repair_id, Integer car_id, Integer part_id, String repair_date,
			Integer duration_minutes, Integer employee_id) {
		validate(internal_repair_id, car_id, part_id, repair_date, duration_minutes, employee_id);
		this.internal_repair_id = internal_repair_id;
		this.car_id = car_id;
		this.part_id = part_id;
		this.repair_date = java.sql.Date.valueOf(repair_date);
		this.duration_minutes = duration_minutes;
		this.employee_id = employee_id;
	}

	public InternalRepairRecord(Integer car_id, Integer part_id, String repair_date, Integer duration_minutes,
			Integer employee_id) {
		validate(-1, car_id, part_id, repair_date, duration_minutes, employee_id);
		this.internal_repair_id = -1;
		this.car_id = car_id;
		this.part_id = part_id;
		this.repair_date = java.sql.Date.valueOf(repair_date);
		this.duration_minutes = duration_minutes;
		this.employee_id = employee_id;
	}

	private void validate(Integer internal_repair_id, Integer car_id, Integer part_id, String repair_date,
			Integer duration_minutes, Integer employee_id) {
		try {
			Objects.requireNonNull(internal_repair_id, String.format(NULL_MESSAGE, INTERNAL_REPAIR_ID));
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, CAR_ID));
			Validator.requireNonBlank(repair_date, String.format(NULL_MESSAGE, REPAIR_DATE));
			Objects.requireNonNull(duration_minutes, String.format(NULL_MESSAGE, DURATION));
			Objects.requireNonNull(employee_id, String.format(NULL_MESSAGE, EMPLOYEE_ID));
		} catch (NullPointerException e) {
			throw new InvalidInternalRepairRecordException(e.getMessage(), e);
		}

		if (car_id <= 0) {
			throw new InvalidInternalRepairRecordException(CAR_ID + "의 입력값이 잘못되었습니다.");
		}

		if (employee_id <= 0) {
			throw new InvalidInternalRepairRecordException(EMPLOYEE_ID + "의 입력값이 잘못되었습니다.");
		}

		if (duration_minutes < 0) {
			throw new InvalidInternalRepairRecordException(DURATION + "의 입력값이 잘못되었습니다.");
		}

		if (!Validator.isValidDate(repair_date)) {
			throw new InvalidInternalRepairRecordException(REPAIR_DATE + "의 입력값이 잘못되었습니다.");
		}

		if (part_id != null && part_id <= 0) {
			throw new InvalidInternalRepairRecordException(PART_ID + "의 입력값이 잘못되었습니다.");
		}
	}

	public Integer getInternal_repair_id() {
		return internal_repair_id;
	}

	public Integer getCar_id() {
		return car_id;
	}

	public Integer getPart_id() {
		return part_id;
	}

	public Date getRepair_date() {
		return repair_date;
	}

	public Integer getDuration_minutes() {
		return duration_minutes;
	}

	public Integer getEmployee_id() {
		return employee_id;
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"internal_repair_id\": %d, \"car_id\": %d, \"part_id\": %s, \"repair_date\": \"%s\", \"duration_minutes\": %d, \"employee_id\": %d }",
				internal_repair_id, car_id, part_id == null ? "null" : part_id.toString(), repair_date.toString(),
				duration_minutes, employee_id);
	}
}