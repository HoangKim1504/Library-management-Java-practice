package util;

import enums.AccountStatus;
import enums.BookCategory;
import enums.Gender;
import enums.UserType;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    // ================= INPUT NUM HELPER =================
    public static int readNum(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    // ================= INPUT CHAR HELPER =================
    public static char readChar(String prompt) {
        while (true) {
            System.out.println(prompt);
            System.out.println("Nhập 'y' để tiếp tục nhập, nhập 'n' để dừng lại.");
            System.out.print("Chọn: ");
            char ans = Character.toLowerCase(sc.next().charAt(0));

            sc.nextLine(); // Clear scanner buffer

            // Validate answer
            if (ans == 'y' || ans == 'n') {
                return ans;
            }

            System.out.println("Vui lòng chỉ nhập 'y' hoặc 'n'!");
        }
    }

    // ================= MAP GENDER INFO =================
    public static Gender inputGender() {
        while (true) {
            System.out.println("\nChọn giới tính: ");
            System.out.println("1. " + Gender.MALE.getDisplayName());
            System.out.println("2. " + Gender.FEMALE.getDisplayName());
            System.out.println("3. " + Gender.OTHER.getDisplayName());

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    return Gender.MALE;
                case 2:
                    return Gender.FEMALE;
                case 3:
                    return Gender.OTHER;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= MAP STATUS INFO =================
    public static AccountStatus inputStatus() {
        while (true) {
            System.out.println("\nChọn tình trạng tài khoản: ");
            System.out.println("1. " + AccountStatus.ACTIVATED.getDisplayName());
            System.out.println("2. " + AccountStatus.BLOCK.getDisplayName());
            System.out.println("3. " + AccountStatus.OTHER.getDisplayName());

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    return AccountStatus.ACTIVATED;
                case 2:
                    return AccountStatus.BLOCK;
                case 3:
                    return AccountStatus.OTHER;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= MAP USER TYPE INFO =================
    public static UserType inputUserType() {
        while (true) {
            System.out.println("\nChọn loại người dùng: ");
            System.out.println("1. " + UserType.ADMIN.getDisplayName());
            System.out.println("2. " + UserType.MANAGER.getDisplayName());
            System.out.println("3. " + UserType.USER.getDisplayName());
            System.out.println("4. " + UserType.OTHER.getDisplayName());

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    return UserType.ADMIN;
                case 2:
                    return UserType.MANAGER;
                case 3:
                    return UserType.USER;
                case 4:
                    return UserType.OTHER;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= MAP BOOK CATEGORY INFO =================
    public static BookCategory inputBookCategory() {
        while (true) {
            System.out.println("\nChọn loại người dùng: ");
            System.out.println("1. " + BookCategory.SCIENCE.getDisplayName());
            System.out.println("2. " + BookCategory.NOVEL.getDisplayName());
            System.out.println("3. " + BookCategory.HISTORY.getDisplayName());
            System.out.println("4. " + BookCategory.PROGRAMMING.getDisplayName());
            System.out.println("5. " + BookCategory.OTHER.getDisplayName());

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    return BookCategory.SCIENCE;
                case 2:
                    return BookCategory.NOVEL;
                case 3:
                    return BookCategory.HISTORY;
                case 4:
                    return BookCategory.PROGRAMMING;
                case 5:
                    return BookCategory.OTHER;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}
