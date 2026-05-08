package reader;

import enums.Gender;
import util.DateUtil;
import util.InputUtil;
import validator.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReaderService {
    // ================= STORE ALL USERS =================
    private final List<Reader> readerList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW READER =================
    public boolean createReader(Reader reader) {
        // Check null
        if (reader == null) {
            System.out.println("Độc giả không hợp lệ!");
            return false;
        }

        // Check duplicate readerId
        if (findCurrentReader(reader.getReaderId()) != null) {
            System.out.println("Độc giả đã tồn tại!");
            return false;
        }

        readerList.add(reader);
        return true;
    }

    // ================= FIND CURRENT READER =================
    public Reader findCurrentReader(String readerId) {
        for (Reader reader : readerList) {
            // Find current reader
            if (reader.getReaderId().equals(readerId)) {
                return reader;
            }
        }
        return null; // not found
    }

    // ================= DISPLAY READER LIST =================
    public void showReaderList() {
        // Check empty reader list
        if (readerList.isEmpty()) {
            System.out.println("Danh sách độc giả trống!");
            return;
        }

        System.out.println("Danh sách độc giả trong thư viện: ");

        int index = 1;

        for (Reader reader : readerList) {
            System.out.println(index + ". " + reader.toString());
            index++;
        }
    }

    // ================= GENERATE NEW READER ID =================
    public String generateNewReaderId() {
        // First reader
        if (readerList.isEmpty()) {
            return "0001";
        }

        // Get last reader
        Reader lastReader = readerList.getLast();

        // Get last readerId
        String lastReaderId = lastReader.getReaderId();

        // Create new readerId
        try {
            int id = Integer.parseInt(lastReaderId);
            return String.format("%04d", id + 1); // keep format: 0001, 0002, ...
        } catch (NumberFormatException e) {
            System.out.println("ReaderId không phải số!");
            return null;
        }
    }

    // ================= INPUT READER INFO =================
    public Reader inputReaderInfo() {
        // Generate readerId
        String readerId = generateNewReaderId();
        if (readerId == null) {
            System.out.println("Không thể tạo readerId!");
            return null;
        }

        // Full name
        String fullName = InputValidator.inputValidString("Họ Tên: ", InputValidator::isValidName);

        // NationalId
        String nationalId = InputValidator.inputValidString("CMND: ", InputValidator::isValidId);

        // Birthdate
        String inputBirthDate = InputValidator.inputValidString("Ngày tháng năm sinh: ", InputValidator::isValidDate);
        LocalDate birthDate = DateUtil.parseLocalDate(inputBirthDate, "yyyy-MM-dd");

        // Gender
        Gender gender = InputUtil.inputGender();
        if (gender == null) {
            System.out.println("Thông tin giới tính bị lỗi!");
            return null;
        }

        // Email
        String email = InputValidator.inputValidString("Email: ", InputValidator::isValidEmail);

        // Address
        String address = InputValidator.inputValidString("Địa chỉ: ", InputValidator::isValidAddress);

        // CreatedDate
        String inputCreatedDate = InputValidator.inputValidString("Ngày lập thẻ: ", InputValidator::isValidDate);
        LocalDate createdDate = DateUtil.parseLocalDate(inputCreatedDate, "yyyy-MM-dd");

        // Create reader object
        return new Reader(
                readerId,
                fullName,
                nationalId,
                birthDate,
                gender,
                email,
                address,
                createdDate
        );
    }

    // ================= FIND READER ID=================
    public String findReaderId(int choice) {
        int index = 1;
        for (Reader reader : readerList) {
            if (index != choice) {
                index++;
                continue;
            }
            return reader.getReaderId();
        }

        return null;
    }

    // ================= DELETE READER =================
    public void deleteReader(String readerId) {
        // Check reader exist
        if (findCurrentReader(readerId) == null) {
            System.out.println("Độc giả không tồn tại!");
            return;
        }

        // Delete reader successfully
        for (Reader reader : readerList) {
            if (reader.getReaderId().equals(readerId)) {
                readerList.remove(reader);
                System.out.println("Đã xoá thông tin độc giả thành công!");
                return;
            }
        }

        // Delete reader fail
        System.out.println("Xoá thông tin độc giả thất bại!");
    }
}
