package library;

import enums.AccountStatus;
import enums.Gender;
import enums.UserType;
import reader.Reader;
import reader.ReaderService;
import user.User;
import user.UserService;
import util.DateUtil;
import validator.UserValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class LibraryManagement {

    private final Scanner sc = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final ReaderService readerService = new ReaderService();

    private static final String NOT_LOGIN = "0";
    private String userId = NOT_LOGIN;

    private static final String DEFAULT_USERNAME = "default";
    private static final String DEFAULT_PASSWORD = "default";
    private static final UserType ADMIN = UserType.ADMIN;
    private static final UserType MANAGER = UserType.MANAGER;
    private static final UserType USER = UserType.USER;

    public static void main(String[] args) {
        LibraryManagement app = new LibraryManagement();
        app.createInitData();
        app.run();
    }

    // ================= INIT USER DATA =================
    public void createInitData() {
        // User data
        User admin = new User(
                "admin", "admin", "Admin system", LocalDate.of(1990, 1, 1),
                "123456789875", "TP.HCM", Gender.MALE, AccountStatus.ACTIVATED, UserType.ADMIN, "0001");
        User manager = new User(
                "manager", "manager", "Manager", LocalDate.of(1995, 12, 1),
                "987654321954", "TP.HCM", Gender.FEMALE, AccountStatus.ACTIVATED, UserType.MANAGER, "0002");
        User user1 = new User(
                "user1", "user1", "User1", LocalDate.of(1994, 8, 17),
                "159753852851", "TP.Ha Noi", Gender.MALE, AccountStatus.ACTIVATED, UserType.USER, "0003");
        User user2 = new User(
                "user2", "user2", "User2", LocalDate.of(1999, 5, 20),
                "456789158487", "TP.Can Tho", Gender.FEMALE, AccountStatus.ACTIVATED, UserType.USER, "0004");
        User user3 = new User(
                "user3", "user3", "User3", LocalDate.of(2000, 1, 15),
                "789541259851", "TP.HCM", Gender.MALE, AccountStatus.BLOCK, UserType.USER, "0005");

        // Reader data
        Reader reader1 = new Reader(
                "0001", "Nguyen Van An", "123654789852", LocalDate.of(2006, 8, 17),
                Gender.MALE, "an@gmail.com", "Ho Chi Minh City", LocalDate.of(2026, 5, 6)
        );
        Reader reader2 = new Reader(
                "0002", "Tran Thi Bich", "456987123654", LocalDate.of(2004, 3, 12),
                Gender.FEMALE, "bich@gmail.com", "Da Nang", LocalDate.of(2026, 5, 6)
        );
        Reader reader3 = new Reader(
                "0003", "Le Minh Khang", "789456123852", LocalDate.of(2002, 11, 25),
                Gender.MALE, "khang@gmail.com", "Can Tho", LocalDate.of(2026, 5, 6)
        );

        Reader reader4 = new Reader(
                "0004", "Pham Ngoc Ha", "321654987456", LocalDate.of(2005, 1, 5),
                Gender.FEMALE, "ha@gmail.com", "Binh Duong", LocalDate.of(2026, 5, 6)
        );

        Reader reader5 = new Reader(
                "0005", "Vo Thanh Tung", "852741963258", LocalDate.of(2001, 9, 30),
                Gender.MALE, "tung@gmail.com", "Ha Noi", LocalDate.of(2026, 5, 6)
        );

        // Create users
        userService.createUser(admin);
        userService.createUser(manager);
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        // Create readers
        readerService.createReader(reader1);
        readerService.createReader(reader2);
        readerService.createReader(reader3);
        readerService.createReader(reader4);
        readerService.createReader(reader5);
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
                case 2:
                    readerScreen();
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

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    // Allow all roles
                    if (requireRole(ADMIN, MANAGER, USER)) continue;

                    // Logout current user
                    userService.logout(userId);

                    // Reset login state
                    userId = NOT_LOGIN;

                    System.out.println("Đăng xuất thành công!");
                    return;
                case 2:
                    // Allow all roles
                    if (requireRole(ADMIN, MANAGER, USER)) continue;

                    // Navigate to change password screen
                    changePassScreen();

                    // If password changed successfully, userId will be reset
                    // → exit this screen to trigger re-login
                    if (userId.equals(NOT_LOGIN)) {
                        return;
                    }
                    break;
                case 3:
                    // Allow all roles
                    if (requireRole(ADMIN, MANAGER, USER)) continue;

                    // Navigate to update user info screen
                    updateInfoScreen();
                    break;
                case 4:
                    // Only ADMIN can create user
                    if (requireRole(ADMIN)) continue;

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
            case 7: // AccountStatus (use Menu)
                AccountStatus accountStatus = inputStatus();
                if (accountStatus == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, accountStatus) != null;
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
            String fullName = inputValidString("Họ Tên: ", UserValidator::isValidName);

            // Birthdate
            String inputBirthDate = inputValidString("Ngày sinh: ", UserValidator::isValidDate);
            LocalDate birthDate = DateUtil.parseLocalDate(inputBirthDate, "yyyy-MM-dd");

            // NationalId
            String nationalId = inputValidString("CMND: ", UserValidator::isValidId);

            // Address
            String address = inputValidString("Địa chỉ: ", UserValidator::isValidAddress);

            // Gender
            Gender gender = inputGender();
            if (gender == null) {
                System.out.println("Thông tin giới tính bị lỗi!");
                continue;
            }

            // AccountStatus
            AccountStatus accountStatus = inputStatus();
            if (accountStatus == null) {
                System.out.println("Thông tin tình trạng tài khoản bị lỗi!");
                continue;
            }

            // User type
            UserType userType = inputUserType();
            if (userType == null) {
                System.out.println("Thông tin loại người dùng bị lỗi!");
                continue;
            }

            // Generate userId
            String userId = userService.generateNewUserId();
            if (userId == null) {
                System.out.println("Không thể tạo userId!");
                continue;
            }

            // Create user object
            User user = new User(
                    DEFAULT_USERNAME,
                    DEFAULT_PASSWORD,
                    fullName,
                    birthDate,
                    nationalId,
                    address,
                    gender,
                    accountStatus,
                    userType,
                    userId
            );

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

    // ================= READER MENU =================
    public void readerScreen() {
        while (true) {
            System.out.println("\n====== MENU ĐỘC GIẢ ======");
            System.out.println("1. Xem danh sách độc giả trong thư viện");
            System.out.println("2. Thêm độc giả");
            System.out.println("3. Chỉnh sửa thông tin một độc giả");
            System.out.println("4. Xóa thông tin một độc giả");
            System.out.println("5. Tìm kiếm độc giả theo CMND");
            System.out.println("6. Tìm kiếm sách theo họ tên");

            int choice = readNum("Chọn: ");

            switch (choice) {
                case 1:
                    // Allow all roles
                    if (requireRole(ADMIN, MANAGER, USER)) continue;

                    // Get the reader list
                    List<Reader> readerList = readerService.getAllReaders();

                    // Show reader list
                    readerService.showReaderList(readerList);

                    break;
                case 2:
                    // Allow all roles
                    if (requireRole(ADMIN, MANAGER, USER)) continue;

                    // Navigate to create reader screen
                    createReaderScreen();

                    // If password changed successfully, userId will be reset
                    // → exit this screen to trigger re-login
                    if (userId.equals(NOT_LOGIN)) {
                        return;
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= CREATE READER FUNCTION =================
    public void createReaderScreen() {
        while (true) {
            System.out.println("\n====== TẠO ĐỘC GIẢ MỚI ======");

            // Generate readerId
            String readerId = readerService.generateNewReaderId();
            if (readerId == null) {
                System.out.println("Không thể tạo readerId!");
                continue;
            }

            // Full name
            String fullName = inputValidString("Họ Tên: ", UserValidator::isValidName);

            // NationalId
            String nationalId = inputValidString("CMND: ", UserValidator::isValidId);

            // Birthdate
            String inputBirthDate = inputValidString("Ngày tháng năm sinh: ", UserValidator::isValidDate);
            LocalDate birthDate = DateUtil.parseLocalDate(inputBirthDate, "yyyy-MM-dd");

            // Gender
            Gender gender = inputGender();
            if (gender == null) {
                System.out.println("Thông tin giới tính bị lỗi!");
                continue;
            }

            // Email
            String email = inputValidString("Email: ", UserValidator::isValidEmail);

            // Address
            String address = inputValidString("Địa chỉ: ", UserValidator::isValidAddress);

            // CreatedDate
            String inputCreatedDate = inputValidString("Ngày lập thẻ: ", UserValidator::isValidDate);
            LocalDate createdDate = DateUtil.parseLocalDate(inputCreatedDate, "yyyy-MM-dd");

            // Create reader object
            Reader reader = new Reader(
                    readerId,
                    fullName,
                    nationalId,
                    birthDate,
                    gender,
                    email,
                    address,
                    createdDate
            );

            // Create new reader
            boolean isSuccess = readerService.createReader(reader);

            // Create reader fail
            if (!isSuccess) {
                System.out.println("Tạo độc giả thất bại!");
                continue;
            }

            // Create reader successfully
            System.out.println("Tạo độc giả thành công!");
            printReaderInfo(reader, false);
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

    // ================= PRINT USER INFO =================
    public void printReaderInfo(Reader reader, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN ĐỘC GIẢ ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN ĐỘC GIẢ ======");
        }
        System.out.println("1. Họ Tên: " + reader.getFullName());
        System.out.println("2. CMND: " + reader.getNationalId());
        System.out.println("3. Ngày sinh: " + reader.getBirthDate());
        System.out.println("4. Giới tính: " + reader.getGender().getDisplayName());
        System.out.println("5. Email: " + reader.getEmail());
        System.out.println("6. Ngày lập thẻ: " + reader.getCreatedDate());
        System.out.println("7. Ngày hết hạn của thẻ (48 tháng kể từ ngày lập thẻ): " + reader.getExpiredDate());
    }

    // ================= MAP GENDER INFO =================
    public Gender inputGender() {
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
    public AccountStatus inputStatus() {
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
    public UserType inputUserType() {
        while (true) {
            System.out.println("\nChọn loại người dùng: ");
            System.out.println("1. Quản trị viên");
            System.out.println("2. Quản lý");
            System.out.println("3. Người dùng");

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

    // ================= INPUT + VALIDATE STRING =================
    private String inputValidString(String prompt, Function<String, Boolean> validator) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();

            if (validator.apply(input)) {
                return input;
            }

            System.out.println("Vui lòng nhập lại!");
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

    // ================= REQUIRE ROLE HELPER =================
    private boolean requireRole(UserType... roles) {
        return !userService.hasAccess(userId, roles);
    }
}
