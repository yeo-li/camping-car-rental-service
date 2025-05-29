package dbcar.main.java.com.dbshindong.dbcar.common.exception;

public class ExceptionSafeRunner {
	public static void run(Runnable task) {
		try {
			task.run();
		} catch (Exception e) {
			GlobalExceptionHandler.handle(e);
		}
	}
}
