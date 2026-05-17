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
    private static final String READER_ID_REGEX = "^R\\d{4}$";
    private static final String BOOK_ID_REGEX = "^BK\\d{5}$";

    // ================= INPUT VALID STRING =================
    public static String inputValidString(String prompt, @NotNull Predicate<String> validator) {
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
    public static int inputValidInt(String prompt, @NotNull Predicate<Integer> validator) {
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
    public static double inputValidDouble(String prompt, @NotNull Predicate<Double> validator) {
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

    // ================= VALID STRING BY REGEX =================
    public static boolean isValidStringByRegex(String input, String regex, String errorMessage) {
        // Validate input
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Thông tin không được bỏ trống!");
            return false;
        }

        // Remove spaces on the beginning and the end
        input = input.trim();

        // Validate regex format
        if (!input.matches(regex)) {
            System.out.println(errorMessage);
            return false;
        }

        return true;
    }

    // ================= VALID PASSWORDS =================
    public static boolean isValidPasswords(String oldPw, @NotNull String newPw, String confirmPw) {
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
        return isValidPassword(newPw);
    }

    // ================= VALID FORMAT PASSWORD =================
    public static boolean isValidPassword(@NotNull String pw) {
        // Check length
        if (pw.length() < 8) {
            System.out.println("Mật khẩu phải có ít nhất 8 kí tự.");
            return false;
        }

        // Check uppercase
        boolean hasUpperCase = !pw.equals(pw.toLowerCase());

        // Check lowercase
        boolean hasLowerCase = !pw.equals(pw.toUpperCase());

        // Check digit
        boolean hasDigit = pw.matches(HAS_DIGIT_REGEX);

        // Check special character
        boolean hasSpecial = pw.matches(HAS_SPECIAL_CHARACTER_REGEX);

        // Final validation
        if (!hasUpperCase || !hasLowerCase || !hasDigit || !hasSpecial) {
            System.out.println("Mật khẩu phải chứa chữ hoa, chữ thường, số và ký tự đặc biệt!");
            return false;
        }

        return true;
    }

    // ================= VALID USER NAME =================
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

    // ================= VALID FULL NAME =================
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

    // ================= VALID DATE =================
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

    // ================= VALID ADDRESS =================
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

    // ================= VALID USER ID =================
    public static boolean isValidId(String id) {
        return isValidStringByRegex(id, NATIONAL_ID_REGEX, "Số CMND phải nhập đủ 12 số!");
    }

    // ================= VALID EMAIL =================
    public static boolean isValidEmail(String email) {
        return isValidStringByRegex(email, EMAIL_REGEX, "Email không hợp lệ!");
    }

    // ================= VALID BOOK TITLE =================
    public static boolean isValidBookTitle(String title) {
        return isValidStringByRegex(title, BOOK_TITLE_REGEX, "Tên sách không hợp lệ!");
    }

    // ================= VALID PUBLISHER =================
    public static boolean isValidPublisher(String publisher) {
        return isValidStringByRegex(publisher, PUBLISHER_REGEX, "Nhà xuất bản không hợp lệ!");
    }

    // ================= VALID READER ID =================
    public static boolean isValidReaderId(String id) {
        return isValidStringByRegex(id, READER_ID_REGEX, "Mã độc giả không hợp lệ!");
    }

    // ================= VALID BOOK ID =================
    public static boolean isValidBookId(String id) {
        return isValidStringByRegex(id, BOOK_ID_REGEX, "Mã sách không hợp lệ!");
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

    // ================= VALID RETURN DATE =================
    public static boolean isValidReturnDate(@NotNull LocalDate borrowDate, @NotNull LocalDate returnDate) {
        try {
            // Return date must be after or equal borrow date
            return returnDate.isAfter(borrowDate);
        } catch (Exception e) {
            System.out.println("Ngày trả không hợp lệ!");
            return false;
        }
    }
}
