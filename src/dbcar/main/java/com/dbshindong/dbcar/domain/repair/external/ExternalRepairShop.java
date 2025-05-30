package dbcar.main.java.com.dbshindong.dbcar.domain.repair.external;

import java.util.Objects;

import dbcar.main.java.com.dbshindong.dbcar.common.Validator;
import dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.exception.InvalidExternalRepairShopException;

public class ExternalRepairShop {
	private final Integer shop_id;
	private final String name;
	private final String address;
	private final String phone;
	private final String manager_name;
	private final String manager_email;

	private static final String NULL_MESSAGE = "%s은(는) null이 들어갈 수 없습니다.";

	public ExternalRepairShop(Integer shop_id, String name, String address, String phone, String manager_name,
			String manager_email) {
		this.validate(shop_id, name, address, phone, manager_name, manager_email);
		this.shop_id = shop_id;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.manager_name = manager_name;
		this.manager_email = manager_email;
	}

	public ExternalRepairShop(String name, String address, String phone, String manager_name, String manager_email) {
		this.validate(-1, name, address, phone, manager_name, manager_email);
		this.shop_id = -1;
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.manager_name = manager_name;
		this.manager_email = manager_email;
	}

	private void validate(Integer shop_id, String name, String address, String phone, String manager_name,
			String manager_email) {
		try {
			Objects.requireNonNull(shop_id, String.format(NULL_MESSAGE, "shop_id"));
			Objects.requireNonNull(name, String.format(NULL_MESSAGE, "name"));
			Objects.requireNonNull(address, String.format(NULL_MESSAGE, "address"));
			Objects.requireNonNull(phone, String.format(NULL_MESSAGE, "phone"));
			Objects.requireNonNull(manager_name, String.format(NULL_MESSAGE, "manager_name"));
			Objects.requireNonNull(manager_email, String.format(NULL_MESSAGE, "manager_email"));
		} catch (NullPointerException e) {
			throw new InvalidExternalRepairShopException(e.getMessage(), e);
		}

		if (!Validator.isValidEmail(manager_email)) {
			throw new InvalidExternalRepairShopException("매니저 이메일의 입력값이 올바르지 않습니다.");
		}
	}

	@Override
	public String toString() {
		return String.format(
				"{ \"shop_id\": %d, \"name\": \"%s\", \"address\": \"%s\", \"phone\": \"%s\", "
						+ "\"manager_name\": \"%s\", \"manager_email\": \"%s\" }",
				shop_id, name, address, phone, manager_name, manager_email);
	}

	public int getShop_id() {
		return shop_id;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getManager_name() {
		return manager_name;
	}

	public String getManager_email() {
		return manager_email;
	}
}