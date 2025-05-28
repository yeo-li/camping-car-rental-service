package dbcar.main.java.com.dbshindong.dbcar.common;

import java.util.Objects;

public class AssertUtil {
    public static void assertEqual(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual)) {
            System.out.println("❌ 실패: " + message + " (expected=" + expected + ", actual=" + actual + ")");
        } else {
            System.out.println("✅ 성공: " + message);
        }
    }
    
    public static void assertNull(Object expected, String message) {
        if (!Objects.equals(expected, null)) {
            System.out.println("❌ 실패: " + message + " ("+expected+" is not null)");
        } else {
            System.out.println("✅ 성공: " + message);
        }
    }
}
