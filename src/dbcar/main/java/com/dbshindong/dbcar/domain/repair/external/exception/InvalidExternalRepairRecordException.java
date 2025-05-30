package dbcar.main.java.com.dbshindong.dbcar.domain.repair.external.exception;

import dbcar.main.java.com.dbshindong.dbcar.common.exception.ConstraintViolationException;

public class InvalidExternalRepairRecordException extends ConstraintViolationException {

	public InvalidExternalRepairRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidExternalRepairRecordException(String message, Exception e) {
		super(message, e);
		// TODO Auto-generated constructor stub
	}

}
