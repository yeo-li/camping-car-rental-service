package dbcar.main.java.com.dbshindong.dbcar.common.exception;

public class GlobalExceptionHandler {
	public static void handle(Exception e) {
		// 2. 사용자에게 안내 (GUI 기반이면 팝업)
		javax.swing.JOptionPane.showMessageDialog(null, "예상치 못한 오류가 발생했습니다:\n" + e.getMessage(), "에러",
				javax.swing.JOptionPane.ERROR_MESSAGE);

	}
}
