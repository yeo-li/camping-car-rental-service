package dbcar.main.java.com.dbshindong.dbcar.domain.company;

import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.company.exception.InvalidCampingCarException;

import java.sql.*;

public class CampingCar {
	private final Integer car_id;
	private final String name;
	private final String plate_number;
	private final Integer capacity;
	private final byte[] image;
	private final String description;
	private final Integer rental_price;
	private final Integer company_id;
	private final Date registered_date;

	private static final String CAR_ID = "캠핑카 고유 ID";
	private static final String NAME = "캠핑카 이름";
	private static final String PLATE_NUMBER = "캠핑카 차량 번호";
	private static final String CAPACITY = "캠핑카 승차 인원수";
	private static final String DESCRIPTION = "캠핑카상세정보";
	private static final String IMAGE = "캠핑카 이미지";
	private static final String RENTAL_PRICE = "캠핑카 대여 비용";
	private static final String COMPANY_ID = "캠핑카 대여 회사 ID";
	private static final String REGISTERED_DATE = "캠핑카 등록 일자";

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public CampingCar(Integer car_id, String name, String plate_number, Integer capacity, byte[] image,
			String description, int rental_price, Integer company_id, String registered_date) {
		this.validate(car_id, name, plate_number, capacity, image, description, rental_price, company_id,
				registered_date);

		this.car_id = car_id;
		this.name = name;
		this.plate_number = plate_number;
		this.capacity = capacity;
		this.image = image;
		this.description = description;
		this.rental_price = rental_price;
		this.company_id = company_id;
		this.registered_date = java.sql.Date.valueOf(registered_date);
	}

	public CampingCar(String name, String plate_number, Integer capacity, byte[] image, String description,
			Integer rental_price, Integer company_id, String registered_date) {
		this.validate(-1, name, plate_number, capacity, image, description, rental_price, company_id, registered_date);
		this.car_id = -1;
		this.name = name;
		this.plate_number = plate_number;
		this.capacity = capacity;
		this.image = image;
		this.description = description;
		this.rental_price = rental_price;
		this.company_id = company_id;
		this.registered_date = java.sql.Date.valueOf(registered_date);
	}

	private void validate(Integer car_id, String name, String plate_number, Integer capacity, byte[] image,
			String description, Integer rental_price, Integer company_id, String registered_date) {

		// null 유효성 검증
		try {
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, CAR_ID));
			Validator.requireNonBlank(name, String.format(NULL_MESSAGE, NAME));
			Validator.requireNonBlank(plate_number, String.format(NULL_MESSAGE, PLATE_NUMBER));
			Objects.requireNonNull(capacity, String.format(NULL_MESSAGE, CAPACITY));
			Objects.requireNonNull(image, String.format(NULL_MESSAGE, IMAGE));
			Validator.requireNonBlank(description, String.format(NULL_MESSAGE, DESCRIPTION));
			Objects.requireNonNull(rental_price, String.format(NULL_MESSAGE, RENTAL_PRICE));
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, COMPANY_ID));
			Validator.requireNonBlank(registered_date, String.format(NULL_MESSAGE, REGISTERED_DATE));
		} catch (Exception e) {
			throw new InvalidCampingCarException(e.getMessage(), e);
		}

		// 수용인원 음수 유효성 검증
		if (capacity < 0) {
			throw new InvalidCampingCarException(CAPACITY + "의 입력값이 올바르지 않습니다.");
		}

		if (rental_price < 0) {
			throw new InvalidCampingCarException(RENTAL_PRICE + "의 입력값이 올바르지 않습니다.");
		}

		if (company_id < 0) {
			throw new InvalidCampingCarException(COMPANY_ID + "의 입력값이 올바르지 않습니다.");
		}

		// Date type 유효성 검증
		if (!Validator.isValidDate(registered_date)) {
			throw new InvalidCampingCarException(REGISTERED_DATE + "의 입력값이 올바르지 않습니다.");
		}

	}

	@Override
	public String toString() {
		return "{\n" + "  \"car_id\": " + car_id + ",\n" + "  \"name\": \"" + name + "\",\n" + "  \"plate_number\": \""
				+ plate_number + "\",\n" + "  \"capacity\": " + capacity + ",\n" + "  \"image\": \"byte["
				+ (image != null ? image.length : 0) + "]\",\n" + "  \"description\": \"" + description + "\",\n"
				+ "  \"rental_price\": " + rental_price + ",\n" + "  \"company_id\": " + company_id + ",\n"
				+ "  \"registered_date\": \"" + registered_date + "\"\n" + "}";
	}

	public Integer getCar_id() {
		return car_id;
	}

	public String getName() {
		return name;
	}

	public String getPlate_number() {
		return plate_number;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public byte[] getImage() {
		return image;
	}

	public String getDescription() {
		return description;
	}

	public Integer getRental_price() {
		return rental_price;
	}

	public Integer getCompany_id() {
		return company_id;
	}

	public Date getRegistered_date() {
		return registered_date;
	}

}
