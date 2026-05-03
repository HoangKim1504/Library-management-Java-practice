package user;

import org.jetbrains.annotations.NotNull;
import util.Gender;
import util.Status;
import util.UserType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // ================= STORE ALL USERS =================
    private final List<User> userList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW USER =================
    public boolean createUser(User user) {
        // Check null
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
                if (user.getStatus() != Status.ACTIVATED) {
                    System.out.println("Tài khoản đã bị khoá. Vui lòng liên hệ admin.");
                    return null; // account islocked
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
                user.setStatus((Status) newInfo);
                break;
            case 8:
                user.setUserType((UserType) newInfo);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }

        return user;
    }

    // ================= CONVERT DATE FRON STRING TO LOCAL DATE =================
    public LocalDate convertToLocalDate(String date, String dateFormat) {
        // Date format
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);

        // Remove spaces on the beginning and the end
        date = date.trim();

        return LocalDate.parse(date, df);
    }

    // ================= GENERATE NEW USERID =================
    public String generateNewUserId() {
        // Check empty list
        if (userList.isEmpty()) {
            return "01";
        }

        // Get last user
        User lastUser = userList.getLast();

        // Get last userId
        String lastUserId = lastUser.getUserId();

        // Create new userId
        try {
            int id = Integer.parseInt(lastUserId);
            return String.format("%02d", id + 1); // keep format: 01, 02, ...
        } catch (NumberFormatException e) {
            System.out.println("UserId không phải số!");
            return null;
        }
    }

    // ================= USER AUTHORIZATION =================
    public boolean userAuth(String userId, String @NotNull ... userTypeList) {
        User user = findCurrentUser(userId);
        String role = user.getUserType().toString();
        for (String userType : userTypeList) {
            if (role.equals(userType)) {
                return true;
            }
        }

        System.out.println("Không có quyền truy cập. Vui lòng liên hệ quản trị viên.");
        return false;
    }
}
