package library;

import UserManagement.User;
import UserManagement.UserService;
import util.Gender;
import util.Status;
import util.UserType;

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryManagement {
    private final Scanner sc = new Scanner(System.in);
    private final UserService userService = new UserService();

    public static void main(String[] args) {
        LibraryManagement app = new LibraryManagement();
        app.createInitData();
        app.run();
    }

    public void createInitData() {
        User admin = new User(
                "admin", "admin", "Admin system", LocalDate.of(1990, 1, 1),
                "123456789", "TP.HCM", Gender.MALE, Status.ACTIVATED, UserType.ADMIN);
        User manager = new User(
                "manager", "manager", "Manager system", LocalDate.of(1995, 12, 1),
                "987654321", "TP.HCM", Gender.FEMALE, Status.ACTIVATED, UserType.MANAGER);
        User user1 = new User(
                "admin", "admin", "Admin system", LocalDate.of(1994, 8, 17),
                "159753852", "TP.Ha Noi", Gender.MALE, Status.ACTIVATED, UserType.USER);
        User user2 = new User(
                "admin", "admin", "Admin system", LocalDate.of(1999, 5, 20),
                "456789158", "TP.Can Tho", Gender.FEMALE, Status.ACTIVATED, UserType.USER);
        User user3 = new User(
                "admin", "admin", "Admin system", LocalDate.of(2000, 1, 15),
                "789541259", "TP.HCM", Gender.MALE, Status.ACTIVATED, UserType.USER);

        userService.createUser(admin);
        userService.createUser(manager);
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);
    }

    public void run() {
        String userId = null;
        if (userId == null) {
            boolean isLogin = loginScreen(userId);
            if (!isLogin) {
                return;
            }
        }

        printMainMenu();
        int choice = readNum("Chọn: ");
        if (choice == 0) {
            return;
        }
        if (choice != 1) {
            System.out.println("Lựa chọn không hợp lệ!");
        }
        switch (choice) {
            case 1:
                userScreen(userId);
                break;
        }
    }

    public void printMainMenu() {
        System.out.println("\n=======Menu Chính======");
        System.out.println("1. Chức năng người dùng (đăng xuất, đổi MK, cập nhật TT, ...)");
        System.out.println("2. Quản lý độc giả");
        System.out.println("3. Quản lý sách");
        System.out.println("4. Lập phiếu mượn sách");
        System.out.println("5. Lập phiếu trả sách");
        System.out.println("6. Thống kê");
    }

    public boolean loginScreen (String userId) {
        do {
            System.out.println("\n=====Menu=====");
            System.out.println("1. Đăng nhập");
            System.out.println("0. Thoát chương trình");
            int choice = readNum("Chọn: ");
            if (choice == 0) {
                return false;
            }
            if (choice != 1) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            System.out.print("Tên đăng nhập: ");
            String userName = sc.nextLine().trim();
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            User n = userService.login(userName, password, userId);
            if (n == null) {
                System.out.println("Đăng nhập thất bại (sai tài khoản/ mật khẩu hoặc tài khoản bị khoá).");
            } else {
                System.out.println("Xin chào, " + n.getUserName() + " (" + n.getUserType().getDisplayName() + ").");
                n.setUserId(userId);
                return true;
            }
        } while (true);
    }

    public int readNum(String prompt) {
        System.out.print(prompt);
        int choice = sc.nextInt();
        sc.nextLine(); // consume leftover newline
        return choice;
    }

    public void userScreen(String userId) {
        do {
            System.out.println("\n======Menu======");
            System.out.println("1. Đăng xuất");
            System.out.println("2. Thay đổi mật khẩu");
            System.out.println("3. Cập nhật thông tin cá nhân");
            System.out.println("4. Tạo người dùng");
            System.out.println("5. Phân quyền người dùng");
            int choice = readNum("Chọn: ");
            if (choice == 0) {
                return;
            }
            if (choice != 1) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            switch (choice) {
                case 1:
                    userId = userService.logout(userId);
                    if (userId == null) {
                        System.out.println("Đăng xuất thành công!");
                        return;
                    }
            }
        } while (true);
    }
}
