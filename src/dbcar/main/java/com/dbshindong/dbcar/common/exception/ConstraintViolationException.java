package dbcar.main.java.com.dbshindong.dbcar.common.exception;

public class ConstraintViolationException extends RuntimeException {
	public ConstraintViolationException(String message) {
		super(message);
	}

	public ConstraintViolationException(String message, Exception e) {
		super(message, e);
	}
}
