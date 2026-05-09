package util;

import reader.Reader;
import reader.ReaderService;
import user.User;
import user.UserService;
import validator.InputValidator;

import java.util.Scanner;

public class TextUtil {
    private static final Scanner sc = new Scanner(System.in);

    // ================= HANDLE TEXT UPDATE USER DATA =================
    public static boolean handleTextUpdateUser(int choice, String userId, UserService userService) {
        while (true) {
            System.out.print("Thông tin cập nhật mới: ");
            String newInfo = sc.nextLine().trim();

            // Valid input
            boolean isValid = InputValidator.isValidateInput(choice, newInfo);
            if (!isValid) {
                System.out.println("Vui lòng nhập lại thông tin.");
                continue;
            }

            // Update user info
            User updatedUser = userService.updateUserInfo(choice, userId, newInfo);

            return updatedUser != null;
        }
    }

    // ================= HANDLE TEXT UPDATE READER DATA =================
    public static boolean handleTextUpdateReader(int choice, String readerId, ReaderService readerService) {
        while (true) {
            System.out.print("Thông tin cập nhật mới: ");
            String newInfo = sc.nextLine().trim();

            // Valid input
            boolean isValid = InputValidator.isValidateInput(choice, newInfo);
            if (!isValid) {
                System.out.println("Vui lòng nhập lại thông tin.");
                continue;
            }

            // Update reader info
            Reader updatedReader = readerService.updateReaderInfo(choice, readerId, newInfo);

            return updatedReader != null;
        }
    }

    // ================= CHECK TEXT CONTAINS IGNORE CASE =================
    public static boolean containsIgnoreCase(String text, String keyword) {
        return text.toLowerCase().contains(keyword.trim().toLowerCase());
    }

}
