package util;

import enums.AccountStatus;
import enums.BookCategory;
import enums.Gender;
import enums.UserType;

import java.util.Scanner;

public class InputUtil {
    private static final Scanner sc = new Scanner(System.in);

    // ================= INPUT HELPER =================
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

    // ================= MAP GENDER INFO =================
    public static Gender inputGender() {
        while (true) {
            System.out.println("\nChọn giới tính: ");
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");

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
            System.out.println("1. Hoạt động");
            System.out.println("2. Khoá");
            System.out.println("3. Khác");

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
            System.out.println("1. Quản trị viên");
            System.out.println("2. Quản lý");
            System.out.println("3. Người dùng");
            System.out.println("4. Khác");

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

    // ================= MAP CATEGORY INFO =================
    public static BookCategory inputCategory() {
        while (true) {
            System.out.println("\nChọn loại người dùng: ");
            System.out.println("1. Khoa học");
            System.out.println("2. Tiểu thuyết");
            System.out.println("3. Lịch sử");
            System.out.println("4. Lập trình");
            System.out.println("5. Khác");

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
