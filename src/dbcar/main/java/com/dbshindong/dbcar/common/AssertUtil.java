package dbcar.main.java.com.dbshindong.dbcar.common;

public class AssertUtil {
    public static void assertEqual(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            System.out.println("❌ 실패: " + message + " (expected=" + expected + ", actual=" + actual + ")");
        } else {
            System.out.println("✅ 성공: " + message);
        }
    }
}
