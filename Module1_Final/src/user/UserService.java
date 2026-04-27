package user;

import util.Gender;
import util.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // Store all users
    private final List<User> userList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // Add new user
    public void createUser(User initData) {
        userList.add(initData);
    }

    // Find current user
    public User findCurrentUser(String userId) {
        for (User user : userList) {
            // Find curent user
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null; // not found
    }

    // Login function
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

    // Logout function
    public void logout(String userId) {
        if (userId == null || userId.equals("0")) {
            System.out.println("Hiện chưa có người dùng nào đăng nhập.");
        }
    }

    // Change password function
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

    // Update current user info
    public User updateUserInfo(int choice, String userId, String newInfo, Gender gender, Status status) {
        User user = findCurrentUser(userId);

        if (user == null) {
            System.out.println("Không tìm thấy người dùng!");
            return null;
        }

        // Update user info
        switch (choice) {
            case 1:
                user.setFullName(newInfo);
                break;
            case 2:
                user.setBirthDate(LocalDate.parse(newInfo));
                break;
            case 3:
                user.setUserId(newInfo);
                break;
            case 4:
                user.setAddress(newInfo);
                break;
            case 5:
                user.setGender(gender);
                break;
            case 6:
                user.setStatus(status);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }

        return user;
    }

}
