package dbcar.main.java.com.dbshindong.dbcar.domain.company;

import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.domain.company.exception.InvalidCampingCarException;

import java.sql.*;

public class CampingCar {
	private final int car_id;
	private final String name;
	private final String plate_number;
	private final int capacity;
	private final byte[] image;
	private final String description;
	private final int rental_price;
	private final int company_id;
	private final Date registered_date;

	private static final String NULL_MESSAGE = "%s은() null이 들어갈 수 없습니다.";

	public CampingCar(int car_id, String name, String plate_number, int capacity, byte[] image, String description,
			int rental_price, int company_id, String registered_date) {
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

	public CampingCar(String name, String plate_number, int capacity, byte[] image, String description,
			int rental_price, int company_id, String registered_date) {
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

	private void validate(int car_id, String name, String plate_number, int capacity, byte[] image, String description,
			int rental_price, int company_id, String registered_date) {

		// null 유효성 검증
		try {
			Objects.requireNonNull(car_id, String.format(NULL_MESSAGE, "car_id"));
			Objects.requireNonNull(name, String.format(NULL_MESSAGE, "name"));
			Objects.requireNonNull(plate_number, String.format(NULL_MESSAGE, "plate_number"));
			Objects.requireNonNull(capacity, String.format(NULL_MESSAGE, "capacity"));
			Objects.requireNonNull(image, String.format(NULL_MESSAGE, "image"));
			Objects.requireNonNull(description, String.format(NULL_MESSAGE, "description"));
			Objects.requireNonNull(rental_price, String.format(NULL_MESSAGE, "rental_price"));
			Objects.requireNonNull(company_id, String.format(NULL_MESSAGE, "company_id"));
			Objects.requireNonNull(registered_date, String.format(NULL_MESSAGE, "registered_date"));
		} catch (Exception e) {
			throw new InvalidCampingCarException(e.getMessage(), e);
		}

		// 수용인원 음수 유효성 검증
		if (capacity < 0) {
			throw new InvalidCampingCarException("capacity의 값은 양수여야합니다.");
		}

		// Date type 유효성 검증
		try {
			java.sql.Date.valueOf(registered_date);
		} catch (IllegalArgumentException e) {
			throw new InvalidCampingCarException("registered_date의 형식이 올바르지 않습니다.");
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

	public int getCar_id() {
		return car_id;
	}

	public String getName() {
		return name;
	}

	public String getPlate_number() {
		return plate_number;
	}

	public int getCapacity() {
		return capacity;
	}

	public byte[] getImage() {
		return image;
	}

	public String getDescription() {
		return description;
	}

	public int getRental_price() {
		return rental_price;
	}

	public int getCompany_id() {
		return company_id;
	}

	public Date getRegistered_date() {
		return registered_date;
	}

}
