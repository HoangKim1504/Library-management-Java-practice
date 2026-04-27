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

    private static final String DEFAULT_USERNAME = "default";
    private static final String DEFAULT_PASSWORD = "default";

    public static void main(String[] args) {
        LibraryManagement app = new LibraryManagement();
        app.createInitData();
        app.run();
    }

    // ================= INIT DATA =================
    public void createInitData() {
        User admin = new User(
                "admin", "admin", "Admin system", LocalDate.of(1990, 1, 1),
                "123456789875", "TP.HCM", Gender.MALE, Status.ACTIVATED, UserType.ADMIN, "01");
        User manager = new User(
                "manager", "manager", "Manager", LocalDate.of(1995, 12, 1),
                "987654321954", "TP.HCM", Gender.FEMALE, Status.ACTIVATED, UserType.MANAGER, "02");
        User user1 = new User(
                "user1", "user1", "User1", LocalDate.of(1994, 8, 17),
                "159753852851", "TP.Ha Noi", Gender.MALE, Status.ACTIVATED, UserType.USER, "03");
        User user2 = new User(
                "user2", "user2", "User2", LocalDate.of(1999, 5, 20),
                "456789158487", "TP.Can Tho", Gender.FEMALE, Status.ACTIVATED, UserType.USER, "04");
        User user3 = new User(
                "user3", "user3", "User3", LocalDate.of(2000, 1, 15),
                "789541259851", "TP.HCM", Gender.MALE, Status.BLOCK, UserType.USER, "05");

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
                    // Navigate to update user info screen
                    updateInfoScreen();
                    break;
                case 4:
                    // Navigate to create user screen
                    createUserScreen();
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

            // Validate passwords
            if (!UserValidator.isValidPasswords(oldPass, newPass, confirmPass)) continue;

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
        boolean isSuccess = false;

        // Find current user
        User user = userService.findCurrentUser(userId);

        if (user == null) {
            System.out.println("Không tìm thấy người dùng!");
            return;
        }

        // Print user info
        printUserInfo(user, isSuccess);
        int choice = readNum("Chọn thông tin muốn cập nhật (1-7) hoặc chọn 0 để quay lại menu người dùng: ");

        if (choice == 0) return;

        switch (choice) {
            case 6: // Gender (use Menu)
                Gender gender = inputGender();
                if (gender == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, gender) != null;
                break;
            case 7: // Status (use Menu)
                Status status = inputStatus();
                if (status == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, status) != null;
                break;
            case 8: // UserType (use Menu)
                UserType userType = inputUserType();
                if (userType == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, userType) != null;
                break;
            default:
                // Handle normal string input
                isSuccess = handleTextUpdate(choice);
                break;
        }

        // Update fail
        if (!isSuccess) {
            System.out.println("Cập nhật thông tin thất bại. Vui lòng thử lại.");
            return;
        }

        // Update successfully
        System.out.println("Cập nhật thông tin thành công!");
        printUserInfo(user, isSuccess);
    }

    // ================= CREATE USER FUNCTION =================
    public void createUserScreen() {
        while (true) {
            System.out.println("\n====== TẠO NGƯỜI DÙNG MỚI ======");

            // Full name
            System.out.print("Họ Tên: ");
            String fullName = sc.nextLine();
            if (!UserValidator.isValidName(fullName)) continue;

            // Birthdate
            System.out.print("Ngày sinh: ");
            String inputBirthDate = sc.nextLine();
            if (!UserValidator.isValidDate(inputBirthDate)) continue;
            LocalDate birthDate = userService.convertToLocalDate(inputBirthDate, "yyyy-MM-dd");

            // NationalId
            System.out.print("CMND: ");
            String nationalId = sc.nextLine();
            if (!UserValidator.isValidId(nationalId)) continue;

            // Address
            System.out.print("Địa chỉ: ");
            String address = sc.nextLine();
            if (!UserValidator.isValidAddress(address)) continue;

            // Gender
            Gender gender = inputGender();
            if (gender == null) {
                System.out.println("Thông tin giới tính bị lỗi!");
                continue;
            }

            // Status
            Status status = inputStatus();
            if (status == null) {
                System.out.println("Thông tin tình trạng tài khoản bị lỗi!");
                continue;
            }

            // User type
            UserType userType = inputUserType();
            if (userType == null) {
                System.out.println("Thông tin loại người dùng bị lỗi!");
                continue;
            }

            // UserId
            String userId = userService.createNewUserId();
            if (userId == null) {
                System.out.println("Thông tin userId bị lỗi!");
                continue;
            }

            // User info
            User user = new User(DEFAULT_USERNAME, DEFAULT_PASSWORD, fullName, birthDate,
                    nationalId, address, gender, status, userType, userId);

            // Create new user
            boolean isSuccess = userService.createUser(user);

            // Create user fail
            if (!isSuccess) {
                System.out.println("Tạo người dùng thất bại!");
                continue;
            }

            // Create user successfully
            System.out.println("Tạo người dùng thành công!");
            printUserInfo(user, false);
            return;
        }
    }

    // ================= PRINT USER INFO =================
    public void printUserInfo(User user, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ======");
        }
        System.out.println("1. Tên đăng nhập: " + user.getUserName());
        System.out.println("2. Họ Tên: " + user.getFullName());
        System.out.println("3. Ngày sinh: " + user.getBirthDate());
        System.out.println("4. CMND: " + user.getNationalId());
        System.out.println("5. Địa chỉ: " + user.getAddress());
        System.out.println("6. Giới tính: " + user.getGender().getDisplayName());
        System.out.println("7. Tình trạng: " + user.getStatus().getDisplayName());
        System.out.println("8. Loại người dùng: " + user.getUserType().getDisplayName());
    }

    // ================= MAP GENDER INFO =================
    public Gender inputGender() {
        while (true) {
            System.out.println("\nChọn giới tính: ");
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
            System.out.println("\nChọn tình trạng tài khoản: ");
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

    // ================= MAP USER TYPE INFO =================
    public UserType inputUserType() {
        while (true) {
            System.out.println("\nChọn loại người dùng: ");
            System.out.println("1. Quản trị viên");
            System.out.println("2. Quản lý");
            System.out.println("3. Người dùng");
            System.out.println("0. Quay lại");

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
                case 0:
                    return null; // cancel
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= HANDLE TEXT UPDATE =================
    private boolean handleTextUpdate(int choice) {
        while (true) {
            System.out.print("Thông tin cập nhật mới: ");
            String newInfo = sc.nextLine().trim();

            // Valid input
            boolean isValid = UserValidator.isValidateInput(choice, newInfo);
            if (!isValid) {
                System.out.println("Vui lòng nhập lại thông tin.");
                continue;
            }

            // Update user info
            User updatedUser = userService.updateUserInfo(choice, userId, newInfo);

            return updatedUser != null;
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
