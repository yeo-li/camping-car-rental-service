package dbcar.main.java.com.dbshindong.dbcar.common;

public class Validator {
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@" + // local-part
			"[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; // domain

	public static boolean isValidEmail(String email) {
		if (email == null || email.isBlank()) {
			return false;
		}
		return email.matches(EMAIL_REGEX);
	}
}
