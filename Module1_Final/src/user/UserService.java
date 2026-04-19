package user;

import util.Status;

import java.util.ArrayList;
import java.util.List;

public class UserService {

    // Store all users
    private final List<User> userList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // Add new user
    public void createUser(User initData) {
        userList.add(initData);
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

    public void logout(String userId) {
        if (userId == null || userId.equals("0")) {
            System.out.println("Hiện chưa có người dùng nào đăng nhập.");
        }
    }
}
