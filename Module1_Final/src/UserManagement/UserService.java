package UserManagement;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> userList = new ArrayList<>(); // Prevents accidental reassignment to the list

    public void createUser(User initData) {
        userList.add(initData);
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(userList); // return copy so that outside cannot modify the internal list
    }

    public User login(String userName, String password, String userId) {
        for (User user: userList) {
            if (user.getUserName().equals(userName)
                    && user.getPassword().equals(password)
                    && userId == null) {
                return user;
            }
        }
        return null;
    }

    public String logout(String userId) {
        return null;
    }
}
