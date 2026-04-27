package user;

import util.Gender;
import util.Status;
import util.UserType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    // Store all users
    private final List<User> userList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // Add new user
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
    public User updateUserInfo(int choice, String userId, Object newInfo) {
        User user = findCurrentUser(userId);

        if (user == null) {
            System.out.println("Không tìm thấy người dùng!");
            return null;
        }

        // Update user info
        switch (choice) {
            case 1:
                user.setFullName((String) newInfo);
                break;
            case 2:
                user.setBirthDate(LocalDate.parse((String) newInfo));
                break;
            case 3:
                user.setNationalId((String) newInfo);
                break;
            case 4:
                user.setAddress((String) newInfo);
                break;
            case 5:
                user.setGender((Gender) newInfo);
                break;
            case 6:
                user.setStatus((Status) newInfo);
                break;
            case 7:
                user.setUserType((UserType) newInfo);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }

        return user;
    }

    // Convert date from String to LocalDate
    public LocalDate convertToLocalDate(String date, String dateFormat) {
        // Date format
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);

        // Remove spaces on the beginning and the end
        date = date.trim();

        return LocalDate.parse(date, df);
    }

    // Create new userId
    public String createNewUserId() {
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
}
