package library;

import user.User;
import user.UserService;
import util.Gender;
import util.Status;
import util.UserType;
import validator.UserValidator;

import java.time.LocalDate;
import java.util.Scanner;

public class LibraryManagement {

    private final Scanner sc = new Scanner(System.in);
    private final UserService userService = new UserService();

    private static final String NOT_LOGIN = "0";
    private String userId = NOT_LOGIN;

    public static void main(String[] args) {
        LibraryManagement app = new LibraryManagement();
        app.createInitData();
        app.run();
    }

    // ================= INIT DATA =================
    public void createInitData() {
        User admin = new User(
                "admin", "admin", "Admin system", LocalDate.of(1990, 1, 1),
                "123456789", "TP.HCM", Gender.MALE, Status.ACTIVATED, UserType.ADMIN);
        User manager = new User(
                "manager", "manager", "Manager", LocalDate.of(1995, 12, 1),
                "987654321", "TP.HCM", Gender.FEMALE, Status.ACTIVATED, UserType.MANAGER);
        User user1 = new User(
                "user1", "user1", "User1", LocalDate.of(1994, 8, 17),
                "159753852", "TP.Ha Noi", Gender.MALE, Status.ACTIVATED, UserType.USER);
        User user2 = new User(
                "user2", "user2", "User2", LocalDate.of(1999, 5, 20),
                "456789158", "TP.Can Tho", Gender.FEMALE, Status.ACTIVATED, UserType.USER);
        User user3 = new User(
                "user3", "user3", "User3", LocalDate.of(2000, 1, 15),
                "789541259", "TP.HCM", Gender.MALE, Status.BLOCK, UserType.USER);

        userService.createUser(admin);
        userService.createUser(manager);
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);
    }

    // ================= MAIN PROGRAM FLOW =================
    public void run() {
        while (true) {
            // Force user to login before using system
            requireLogin();

            // Main menu loop
            printMainMenu();
            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    userScreen();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= REQUIRE LOGIN =================
    private void requireLogin() {
        while (userId.equals(NOT_LOGIN)) {
            boolean isLogin = loginScreen();
            if (!isLogin) {
                System.out.println("Thoát chương trình...");
                System.exit(0); // exit program
            }
        }
    }

    // ================= MAIN MENU =================
    public void printMainMenu() {
        System.out.println("\n======= MENU CHÍNH ======");
        System.out.println("1. Chức năng người dùng (đăng xuất, đổi MK, cập nhật TT, ...)");
        System.out.println("2. Quản lý độc giả");
        System.out.println("3. Quản lý sách");
        System.out.println("4. Lập phiếu mượn sách");
        System.out.println("5. Lập phiếu trả sách");
        System.out.println("6. Thống kê");
    }

    // ================= LOGIN FUNCTION =================
    public boolean loginScreen () {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng nhập");
            System.out.println("0. Thoát chương trình");

            int choice = readNum("Chọn: ");

            if (choice == 0) return false;

            if (choice != 1) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            // Input username & password
            System.out.print("Tên đăng nhập: ");
            String userName = sc.nextLine().trim();

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            // Call service to authenticate
            User user = userService.login(userName, password);

            if (user == null) {
                System.out.println("Đăng nhập thất bại (sai tài khoản/ mật khẩu hoặc tài khoản bị khoá).");
            } else {
                // Save logged-in userId
                this.userId = user.getUserId();

                System.out.println("Xin chào, " + user.getUserName() + " (" + user.getUserType().getDisplayName() + ").");
                return true;
            }
        }
    }

    // ================= USER MENU =================
    public void userScreen() {
        while (true) {
            System.out.println("\n====== MENU NGƯỜI DÙNG ======");
            System.out.println("1. Đăng xuất");
            System.out.println("2. Thay đổi mật khẩu");
            System.out.println("3. Cập nhật thông tin cá nhân");
            System.out.println("4. Tạo người dùng");
            System.out.println("5. Phân quyền người dùng");

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    // Logout current user
                    userService.logout(userId);

                    // Reset login state
                    userId = NOT_LOGIN;

                    System.out.println("Đăng xuất thành công!");
                    return;
                case 2:
                    // Navigate to change password screen
                    changePassScreen();

                    // If password changed successfully, userId will be reset
                    // → exit this screen to trigger re-login
                    if (userId.equals(NOT_LOGIN)) {
                        return;
                    }
                    break;
                case 3:
                    // Navigate to update user info
                    updateInfoScreen();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= CHANGE PASSWORD FUNCTION =================
    public void changePassScreen () {
        while (true) {
            System.out.println("\n====== ĐỔI MẬT KHẨU ======");

            // Input old password
            System.out.print("Nhập mật khẩu cũ: ");
            String oldPass = sc.nextLine();

            // Password rules
            System.out.println("Mật khẩu mạnh là mật khẩu:");
            System.out.println("\uF0A7 Có ít nhất 8 ký tự");
            System.out.println("\uF0A7 Có ít nhất 1 chữ in hoa, 1 chữ thường, 1 số, 1 ký tự đặc biệt (!@#$%^&*)");

            // Input new password
            System.out.print("Nhập mật khẩu mới: ");
            String newPass = sc.nextLine();
            System.out.print("Nhập lại mật khẩu mới: ");
            String confirmPass = sc.nextLine();

            // Validate confirm password
            if (!newPass.equals(confirmPass)) {
                System.out.println("Mật khẩu xác nhận không khớp với mật khẩu mới!");
                continue;
            }

            // Prevent same password
            if (oldPass.equals(newPass)) {
                System.out.println("Mật khẩu mới phải khác với mật khẩu cũ");
                continue;
            }

            // Check validate new password
            if (!UserValidator.isValidPass(newPass)) {
                continue;
            }

            // Call service to update password
            boolean isChanged = userService.changePassword(userId, oldPass, newPass);

            if (isChanged) {
                System.out.println("Thay đổi mật khẩu thành công!");
                userId = NOT_LOGIN; // force to log in again
                break;
            } else {
                System.out.println("Mật khẩu cũ không đúng!");
            }
        }
    }

    // ================= UPDATE USER INFO FUNCTION =================
    public void updateInfoScreen() {
        while (true) {
            String newInfo = "";
            Gender gender = null;
            Status status = null;
            boolean isValid = false;
            boolean isUpdate = false;

            User user = userService.findCurrentUser(userId);

            if (user == null) {
                System.out.println("Không tìm thấy người dùng!");
                return;
            }

            // Print user info
            printUserInfo(user, isUpdate);
            int choice = readNum("Chọn thông tin muốn cập nhật (1-6) hoặc chọn 0 để quay lại menu người dùng: ");

            if (choice == 0) return;

            while (true) {
                if (choice == 5) {
                    gender = inputGender();
                    break;
                } else if (choice == 6) {
                    status = inputStatus();
                    break;
                } else {
                    System.out.print("Thông tin cập nhật mới: ");
                    newInfo = sc.nextLine();
                }

                // Validate input
                switch (choice) {
                    case 1:
                        if (UserValidator.isValidName(newInfo)) isValid = true;
                        break;
                    case 2:
                        if (UserValidator.isValidDate(newInfo)) isValid = true;
                        break;
                    case 3:
                        if (UserValidator.isValidId(newInfo)) isValid = true;
                        break;
                    case 4:
                        if (UserValidator.isValidAddress(newInfo)) isValid = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ!");
                        break;
                }

                if (isValid) {
                    break;
                }

                System.out.println("Vui lòng nhập lại thông tin.");
            }

            // Update use info
            User newUserInfo = userService.updateUserInfo(choice, userId, newInfo, gender, status);

            // Update fail
            if (newUserInfo == null) {
                System.out.println("Cập nhật thông tin thất bại. Vui lòng thử lại.");
                return;
            }

            // Update userId
            if (choice == 3) {
                userId = newInfo;
            }

            // Update successfully
            System.out.println("Cập nhật thông tin thành công!");
            isUpdate = true;
            printUserInfo(newUserInfo, isUpdate);
        }
    }

    // ================= PRINT USER INFO =================
    public void printUserInfo(User user, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ======");
        }
        System.out.println("1. Họ Tên: " + user.getFullName());
        System.out.println("2. Ngày sinh: " + user.getBirthDate());
        System.out.println("3. CMND: " + user.getUserId());
        System.out.println("4. Địa chỉ: " + user.getAddress());
        System.out.println("5. Giới tính: " + user.getGender().getDisplayName());
        System.out.println("6. Tình trạng: " + user.getStatus().getDisplayName());
    }

    // ================= MAP GENDER INFO =================
    public Gender inputGender() {
        while (true) {
            System.out.println("Hãy chọn giới tính của bạn: ");
            System.out.println("1. Nam");
            System.out.println("2. Nữ");
            System.out.println("3. Khác");
            System.out.println("0. Quay lại");

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    return Gender.MALE;
                case 2:
                    return Gender.FEMALE;
                case 3:
                    return Gender.OTHER;
                case 0:
                    return null; // cancel
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= MAP STATUS INFO =================
    public Status inputStatus() {
        while (true) {
            System.out.println("Hãy chọn tình trạng tài khoản của bạn: ");
            System.out.println("1. Hoạt động");
            System.out.println("2. Khoá");
            System.out.println("3. Khác");
            System.out.println("0. Quay lại");

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    return Status.ACTIVATED;
                case 2:
                    return Status.BLOCK;
                case 3:
                    return Status.OTHER;
                case 0:
                    return null; // cancel
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= INPUT HELPER =================
    public int readNum(String prompt) {
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
}
