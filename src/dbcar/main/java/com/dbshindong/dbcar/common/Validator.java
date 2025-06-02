package dbcar.main.java.com.dbshindong.dbcar.common;

public class Validator {

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9가-힣._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

	public static boolean isValidEmail(String email) {
		if (email == null || email.isBlank()) {
			return false;
		}
		return email.matches(EMAIL_REGEX);
	}

	public static boolean isValidDate(String date) {
		try {
			java.sql.Date.valueOf(date);
		} catch (IllegalArgumentException e) {
			return false;
		}

		return true;
	}

	public static String requireNonBlank(String value, String message) {
		if (value == null || value.isBlank()) {
			throw new NullPointerException(message);
		}
		return value;
	}

	public static <T> T requireNonNull(T value, String message) {
		if (value == null) {
			throw new NullPointerException(message);
		}
		return value;
	}
}
