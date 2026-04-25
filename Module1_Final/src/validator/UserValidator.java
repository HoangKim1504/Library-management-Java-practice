package validator;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserValidator {
    // ================= VALID NEW PASSWORD =================
    public static boolean isValidPass(@NotNull String pw) {
        // Check length
        if (pw.length() < 8) {
            System.out.println("Mật khẩu phải có ít nhất 8 kí tự.");
            return false;
        }

        // Check uppercase
        boolean hasUpper = !pw.equals(pw.toLowerCase());

        // Check lowercase
        boolean hasLower = !pw.equals(pw.toUpperCase());

        // Check digit
        boolean hasDigit = pw.matches(".*\\d.*");

        // Check special character
        boolean hasSpecial = pw.matches(".*[!@#$%^&*].*");

        // Final validation
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) {
            System.out.println("Mật khẩu phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW NAME =================
    public static boolean isValidName(String name) {
        // Check null
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Tên không được bỏ trống!");
            return false;
        }

        // Remove spaces on the beginning and the end
        name = name.trim();

        // Check length
        if (name.length() < 2) {
            System.out.println("Tên phải có ít nhất 2 ký tự!");
            return false;
        }

        // Only letters and spaces
        if (!name.matches("[\\p{L} ]+")) {
            System.out.println("Tên chỉ được chứa chữ và khoảng cách!");
            return false;
        }

        // No multiple spaces
        if (name.contains("  ")) {
            System.out.println("Tên không chứa nhiều khoảng cách!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW DATE =================
    public static boolean isValidDate(String date) {
        // Check null
        if (date == null || date.trim().isEmpty()) return false;

        // Date format
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Remove spaces on the beginning and the end
        date = date.trim();

        try {
            LocalDate.parse(date, df);
            return true; // valid date
        } catch (DateTimeParseException e) {
            System.out.println("Thông tin thời gian không hợp lệ!");
            return false; // invalid date
        }
    }

    // ================= VALID NEW USER ID =================
    public static boolean isValidId(String id) {
        // Check null
        if (id == null || id.trim().isEmpty()) return false;

        // Remove spaces on the beginning and the end
        id = id.trim();

        // Check only number and have 12 numbers
        if (!id.matches("\\d{12}")) {
            System.out.println("Số CMND phải nhập đủ 12 số!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW ADDRESS =================
    public static boolean isValidAddress(String address) {
        // Check null
        if (address == null || address.trim().isEmpty()) return false;

        // Remove spaces on the beginning and the end
        address = address.trim();

        // Check length
        if (address.length() < 5) {
            System.out.println("Địa chỉ phải có ít nhất 5 ký tự.");
            return false;
        }

        // Allow letters (Unicode), numbers, space and common symbols
        if (!address.matches("[\\p{L}\\d ,./-]+")) {
            System.out.println("Địa chỉ chứa ký tự cho phép.");
            return false;
        }

        // No multiple spaces
        if (address.contains("  ")) {
            System.out.println("Địa chỉ không chứa nhiều khoảng cách!");
            return false;
        }

        return true;
    }
}
