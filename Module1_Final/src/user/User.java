package user;

import util.Gender;
import util.Status;
import util.UserType;

import java.time.LocalDate;

public class User {

    private String userName;
    private String password;
    private String fullName;
    LocalDate birthDate;
    private String nationalId;
    private String address;
    private Gender gender;
    private Status status;
    private UserType userType;
    private String userId;

    public User() {
    }

    public User(String userName, String password, String fullName, LocalDate birthDate, String nationalId, String address, Gender gender, Status status, UserType userType, String userId) {
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.nationalId = nationalId;
        this.address = address;
        this.gender = gender;
        this.status = status;
        this.userType = userType;
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
