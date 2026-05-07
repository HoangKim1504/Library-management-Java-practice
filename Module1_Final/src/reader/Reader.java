package reader;

import enums.Gender;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class Reader {
    private String readerId;
    private String fullName;
    private String nationalId;
    private LocalDate birthDate;
    private Gender gender;
    private String email;
    private String address;
    private LocalDate createdDate;
    private LocalDate expiredDate;

    public Reader() {
    }

    public Reader(String readerId, String fullName, String nationalId, LocalDate birthDate, Gender gender, String email, String address, @NotNull LocalDate createdDate) {
        this.readerId = readerId;
        this.fullName = fullName;
        this.nationalId = nationalId;
        this.birthDate = birthDate;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.createdDate = createdDate;
        this.expiredDate = createdDate.plusMonths(48); // 48 months from createdDate
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public String toString() {
        return "Thông tin độc giả:" +
                " Mã độc giả: '" + readerId + '\'' +
                ", Họ tên: '" + fullName + '\'' +
                ", CMND: '" + nationalId + '\'' +
                ", Ngày tháng năm sinh: '" + birthDate + '\'' +
                ", Giới tính: '" + gender.getDisplayName() + '\'' +
                ", Email: '" + email + '\'' +
                ", Địa chỉ: '" + address + '\'' +
                ", Ngày lập thẻ: '" + createdDate + '\'' +
                ", Ngày hết hạn của thẻ: '" + expiredDate + '\'';
    }
}
