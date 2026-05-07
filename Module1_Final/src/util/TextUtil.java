package util;

import user.User;
import user.UserService;
import validator.InputValidator;

import java.util.Scanner;

public class TextUtil {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserService userService = new UserService();

    // ================= HANDLE TEXT UPDATE =================
    public static boolean handleTextUpdate(int choice, String userId) {
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

}
