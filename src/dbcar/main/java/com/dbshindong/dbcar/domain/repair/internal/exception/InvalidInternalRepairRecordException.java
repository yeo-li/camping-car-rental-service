package dbcar.main.java.com.dbshindong.dbcar.domain.repair.internal.exception;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.ConstraintViolationException;

public class InvalidInternalRepairRecordException extends ConstraintViolationException {

	public InvalidInternalRepairRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidInternalRepairRecordException(String message, Exception e) {
		super(message, e);
		// TODO Auto-generated constructor stub
	}

}
