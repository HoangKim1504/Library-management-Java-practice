package validator;

import org.jetbrains.annotations.NotNull;
import util.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.function.Predicate;

public class InputValidator {
    private static final Scanner sc = new Scanner(System.in);

    private static final String HAS_DIGIT_REGEX = ".*\\d.*";
    private static final String HAS_SPECIAL_CHARACTER_REGEX = ".*[!@#$%^&*].*";
    private static final String ONLY_LETTERS_SPACES_REGEX = "[\\p{L} ]+";
    private static final String NATIONAL_ID_REGEX = "\\d{12}";
    private static final String ADDRESS_REGEX = "[\\p{L}\\d ,./-]+";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
    private static final String BOOK_TITLE_REGEX = "^[A-Za-zÀ-ỹ0-9+\\-#().,:\\s]{2,100}$";
    private static final String PUBLISHER_REGEX = "^[\\p{L}0-9&'.,\\-\\s]{2,100}$";

    // ================= INPUT VALID STRING =================
    public static String inputValidString(String prompt, Predicate<String> validator) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (validator.test(input)) {
                return input;
            }

            System.out.println("Vui lòng nhập lại!");
        }
    }

    // ================= INPUT VALID INTEGER NUMBER =================
    public static int inputValidInt(String prompt, Predicate<Integer> validator) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            try {
                // Convert to integer
                int numberInt = Integer.parseInt(input);

                // Validate number
                if (validator.test(numberInt)) {
                    return numberInt;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            System.out.println("Vui lòng nhập lại!");
        }
    }

    // ================= INPUT VALID DOUBLE NUMBER =================
    public static double inputValidDouble(String prompt, Predicate<Double> validator) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            try {
                // Convert to double
                double numberDouble = Double.parseDouble(input);

                // Validate price
                if (validator.test(numberDouble)) {
                    return numberDouble;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
                continue;
            }

            System.out.println("Vui lòng nhập lại!");
        }
    }

    // ================= VALID PASSWORDS =================
    public static boolean isValidPasswords(String oldPw, String newPw, String confirmPw) {
        // Validate confirm password
        if (!newPw.equals(confirmPw)) {
            System.out.println("Mật khẩu xác nhận không khớp với mật khẩu mới!");
            return false;
        }

        // Prevent same password
        if (oldPw.equals(newPw)) {
            System.out.println("Mật khẩu mới phải khác với mật khẩu cũ!");
            return false;
        }

        // Check validate new password
        return isValidPass(newPw);
    }

    // ================= VALID FORMAT PASSWORD =================
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
        boolean hasDigit = pw.matches(HAS_DIGIT_REGEX);

        // Check special character
        boolean hasSpecial = pw.matches(HAS_SPECIAL_CHARACTER_REGEX);

        // Final validation
        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial) {
            System.out.println("Mật khẩu phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW USER NAME =================
    public static boolean isValidUserName(String name) {
        // Validate input
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

        // No multiple spaces
        if (name.contains("  ")) {
            System.out.println("Tên không chứa nhiều khoảng cách!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW FULL NAME =================
    public static boolean isValidName(String name) {
        // Validate input
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
        if (!name.matches(ONLY_LETTERS_SPACES_REGEX)) {
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
        // Validate input
        if (date == null || date.trim().isEmpty()) return false;

        try {
            DateUtil.parseLocalDate(date, "yyyy-MM-dd");
            return true; // valid date
        } catch (DateTimeParseException e) {
            System.out.println("Thông tin thời gian không hợp lệ! (yyyy-MM-dd)");
            return false; // invalid date
        }
    }

    // ================= VALID NEW USER ID =================
    public static boolean isValidId(String id) {
        // Validate input
        if (id == null || id.trim().isEmpty()) return false;

        // Remove spaces on the beginning and the end
        id = id.trim();

        // Check only number and have 12 numbers
        if (!id.matches(NATIONAL_ID_REGEX)) {
            System.out.println("Số CMND phải nhập đủ 12 số!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW ADDRESS =================
    public static boolean isValidAddress(String address) {
        // Validate input
        if (address == null || address.trim().isEmpty()) return false;

        // Remove spaces on the beginning and the end
        address = address.trim();

        // Check length
        if (address.length() < 5) {
            System.out.println("Địa chỉ phải có ít nhất 5 ký tự.");
            return false;
        }

        // Allow letters (Unicode), numbers, space and common symbols
        if (!address.matches(ADDRESS_REGEX)) {
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

    // ================= VALID NEW EMAIL =================
    public static boolean isValidEmail(String email) {
        // Validate input
        if (email == null || email.trim().isEmpty()) return false;

        // Remove spaces on the beginning and the end
        email = email.trim();

        // Check validate
        boolean validate = email.matches(EMAIL_REGEX);

        if (!validate) {
            System.out.println("Email không hợp lệ!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW BOOK TITLE =================
    public static boolean isValidBookTitle(String title) {
        // Validate input
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Tên sách không được bỏ trống!");
            return false;
        }

        // Remove spaces on the beginning and the end
        title = title.trim();

        // Check validate
        boolean validate = title.matches(BOOK_TITLE_REGEX);

        if (!validate) {
            System.out.println("Tên sách không hợp lệ!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW PUBLISHER =================
    public static boolean isValidPublisher(String publisher) {
        // Validate input
        if (publisher == null || publisher.trim().isEmpty()) {
            System.out.println("Nhà xuất bản không được bỏ trống!");
            return false;
        }

        // Remove spaces on the beginning and the end
        publisher = publisher.trim();

        // Check validate
        boolean validate = publisher.matches(PUBLISHER_REGEX);

        if (!validate) {
            System.out.println("Nhà xuất bản không hợp lệ!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW YEAR =================
    public static boolean isValidYear(int year) {
        int currentYear = LocalDate.now().getYear();

        // Publisher year must be between 1000 and current year
        if (year < 1000 || year > currentYear) {
            System.out.println("Năm xuất bản không hợp lệ!");
            return false;
        }

        return true;
    }

    // ================= VALID NEW PRICE =================
    public static boolean isValidPrice(double price) {
        return price > 0;
    }

    // ================= VALID NEW QUANTITY =================
    public static boolean isValidQuantity(int quantity) {
        return quantity > 0;
    }

}
