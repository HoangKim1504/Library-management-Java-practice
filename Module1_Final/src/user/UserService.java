package user;

import enums.AccountStatus;
import enums.Gender;
import enums.UserType;
import org.jetbrains.annotations.NotNull;
import util.DateUtil;
import util.InputUtil;
import validator.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // ================= STORE ALL USERS =================
    private final List<User> userList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW USER =================
    public boolean createUser(User user) {
        // Validate input user
        if (user == null) {
            System.out.println("Người dùng không hợp lệ!");
            return false;
        }

        // Check duplicate userId
        if (findCurrentUser(user.getUserId()) != null) {
            System.out.println("Người dùng đã tồn tại!");
            return false;
        }

        userList.add(user);
        return true;
    }

    // ================= FIND CURRENT USER =================
    public User findCurrentUser(String userId) {
        for (User user : userList) {
            // Find current user
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null; // not found
    }

    // ================= LOGIN FUNCTION =================
    public User login(String userName, String password) {
        for (User user: userList) {
            // Check userName & password
            if (user.getUserName().equals(userName)
                    && user.getPassword().equals(password)) {
                // Check account status
                if (user.getStatus() != AccountStatus.ACTIVATED) {
                    System.out.println("Tài khoản đã bị khoá. Vui lòng liên hệ admin.");
                    return null; // account is locked
                }

                return user; // login success
            }
        }

        return null ;// wrong credentials
    }

    // ================= LOGOUT FUNCTION =================
    public void logout(String userId) {
        if (userId == null || userId.equals("0")) {
            System.out.println("Hiện chưa có người dùng nào đăng nhập.");
        }
    }

    // ================= CHANGE PASSWORD FUNCTION =================
    public boolean changePassword (String userId, String oldPass, String newPass) {
        User user = findCurrentUser(userId);

        // Check user exists
        if (user == null) return false;

        // Check old password
        if (!user.getPassword().equals(oldPass)) return false;

        // Update new password
        user.setPassword(newPass);
        return true;
    }

    // ================= INPUT USER INFO =================
    public User inputUserInfo(String defaultUserName, String defaultPassword) {
        // Full name
        String fullName = InputValidator.inputValidString("Họ Tên: ", InputValidator::isValidName);

        // Birthdate
        String inputBirthDate = InputValidator.inputValidString("Ngày sinh: ", InputValidator::isValidDate);
        LocalDate birthDate = DateUtil.parseLocalDate(inputBirthDate, "yyyy-MM-dd");

        // NationalId
        String nationalId = InputValidator.inputValidString("CMND: ", InputValidator::isValidId);

        // Address
        String address = InputValidator.inputValidString("Địa chỉ: ", InputValidator::isValidAddress);

        // Gender
        Gender gender = InputUtil.inputGender();
        if (gender == null) {
            System.out.println("Thông tin giới tính bị lỗi!");
            return null;
        }

        // AccountStatus
        AccountStatus accountStatus = InputUtil.inputStatus();
        if (accountStatus == null) {
            System.out.println("Thông tin tình trạng tài khoản bị lỗi!");
            return null;
        }

        // User type
        UserType userType = InputUtil.inputUserType();
        if (userType == null) {
            System.out.println("Thông tin loại người dùng bị lỗi!");
            return null;
        }

        // Generate userId
        String userId = generateNewUserId();
        if (userId == null) {
            System.out.println("Không thể tạo userId!");
            return null;
        }

        // Create user object
        return new User(
                defaultUserName,
                defaultPassword,
                fullName,
                birthDate,
                nationalId,
                address,
                gender,
                accountStatus,
                userType,
                userId
        );
    }

    // ================= UPDATE CURRENT USER INFO =================
    public User updateUserInfo(int choice, String userId, Object newInfo) {
        User user = findCurrentUser(userId);

        if (user == null) {
            System.out.println("Không tìm thấy người dùng!");
            return null;
        }

        // Update user info
        switch (choice) {
            case 1:
                user.setUserName((String) newInfo);
                break;
            case 2:
                user.setFullName((String) newInfo);
                break;
            case 3:
                user.setBirthDate(LocalDate.parse((String) newInfo));
                break;
            case 4:
                user.setNationalId((String) newInfo);
                break;
            case 5:
                user.setAddress((String) newInfo);
                break;
            case 6:
                user.setGender((Gender) newInfo);
                break;
            case 7:
                user.setStatus((AccountStatus) newInfo);
                break;
            case 8:
                user.setUserType((UserType) newInfo);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }

        return user;
    }

    // ================= GENERATE NEW USERID =================
    public String generateNewUserId() {
        // First user
        if (userList.isEmpty()) {
            return "0001";
        }

        // Get last user
        User lastUser = userList.getLast();

        // Get last userId
        String lastUserId = lastUser.getUserId();

        // Create new userId
        try {
            int id = Integer.parseInt(lastUserId);
            return String.format("%04d", id + 1); // keep format: 0001, 0002, ...
        } catch (NumberFormatException e) {
            System.out.println("UserId không phải số!");
            return null;
        }
    }

    // ================= USER AUTHORIZATION =================
    public boolean hasAccess(String userId, UserType @NotNull ... allowedRoles) {
        // Find current user
        User user = findCurrentUser(userId);

        // Check user existence
        if (user == null) {
            System.out.println("Không tìm thấy người dùng!");
            return false;
        }

        // Get user's role
        UserType currentRole = user.getUserType();

        // Check if user's role is in allowed roles
        for (UserType role : allowedRoles) {
            if (currentRole == role) {
                return true; // authorized
            }
        }

        return false;
    }

    // ================= REQUIRE ROLE HELPER =================
    public boolean requireRole(String userId, UserType... roles) {
        boolean noAccess = !hasAccess(userId, roles);
        if (noAccess) {
            // If no matching role -> deny access
            System.out.println("Không có quyền truy cập. Vui lòng liên hệ quản trị viên.");
            return true;
        }
        return false;
    }
}
