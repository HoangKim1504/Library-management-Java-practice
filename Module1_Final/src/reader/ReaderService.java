package reader;

import enums.Gender;
import util.DateUtil;
import util.InputUtil;
import util.TextUtil;
import validator.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReaderService {
    // ================= STORE ALL READERS =================
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
        // Validate input
        if (readerId == null || readerId.isEmpty()) {
            return null;
        }

        // Remove spaces at beginning and end
        readerId = readerId.trim();

        for (Reader reader : readerList) {
            //  Match reader ID
            if (reader.getReaderId().equals(readerId)) {
                return reader;
            }
        }

        // Reader not found
        return null;
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
            System.out.println(index + ". " + reader);
            index++;
        }
    }

    // ================= GENERATE NEW READER ID =================
    public String generateNewReaderId() {
        // Default first reader
        if (readerList.isEmpty()) {
            return "R0001";
        }

        // Get last reader
        Reader lastReader = readerList.getLast();

        // Get last reader ID (Remove R prefix)
        String lastReaderId = lastReader.getReaderId().replace("R", "");

        // Create new reader ID
        try {
            // Convert reader ID to integer
            int id = Integer.parseInt(lastReaderId);
            // Generate next reader ID
            return String.format("R%04d", id + 1); // keep format: R0001, R0002, ...
        } catch (NumberFormatException e) {
            System.out.println("Mã độc giả không hợp lệ!");
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

        // Input full name
        String fullName = InputValidator.inputValidString("Họ Tên: ", InputValidator::isValidName);

        // Input national ID
        String nationalId = InputValidator.inputValidString("CMND: ", InputValidator::isValidId);

        // Input birthDate
        String inputBirthDate = InputValidator.inputValidString("Ngày tháng năm sinh: ", InputValidator::isValidDate);
        LocalDate birthDate = DateUtil.parseLocalDate(inputBirthDate, "yyyy-MM-dd");

        // Input gender
        Gender gender = InputUtil.inputGender();
        if (gender == null) {
            System.out.println("Thông tin giới tính bị lỗi!");
            return null;
        }

        // Input email
        String email = InputValidator.inputValidString("Email: ", InputValidator::isValidEmail);

        // Input address
        String address = InputValidator.inputValidString("Địa chỉ: ", InputValidator::isValidAddress);

        // Input created date
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

    // ================= FIND READER ID BY INDEX =================
    public String findReaderIdByIndex(int userChoice) {
        int currentIndex = 1;

        for (Reader reader : readerList) {
            // Match selected reader index
            if (currentIndex == userChoice) {
                return reader.getReaderId();
            }
            currentIndex++;
        }

        return null;
    }

    // ================= UPDATE CURRENT READER INFO =================
    public Reader updateReaderInfo(int choice, String readerId, Object newInfo) {
        Reader reader = findCurrentReader(readerId);

        if (reader == null) {
            System.out.println("Không tìm thấy độc giả!");
            return null;
        }

        // Update reader info
        switch (choice) {
            case 1:
                reader.setFullName((String) newInfo);
                break;
            case 2:
                reader.setNationalId((String) newInfo);
                break;
            case 3:
                reader.setBirthDate(LocalDate.parse((String) newInfo));
                break;
            case 4:
                reader.setGender((Gender) newInfo);
                break;
            case 5:
                reader.setEmail((String) newInfo);
                break;
            case 6:
                reader.setAddress((String) newInfo);
                break;
            case 7:
                reader.setCreatedDate(LocalDate.parse((String) newInfo));
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }

        return reader;
    }

    // ================= DELETE READER =================
    public boolean deleteReader(String readerId) {
        // Find reader by readerId
        Reader reader = findCurrentReader(readerId);

        // Remove reader from list
        boolean isDeleted = readerList.remove(reader);

        // Delete successfully
        if (isDeleted) {
            System.out.println("Đã xoá thông tin độc giả thành công!");
            return true;
        }

        // Delete failed
        System.out.println("Xoá thông tin độc giả thất bại!");

        return false;
    }

    // ================= FIND READER BY NATIONAL ID =================
    public Reader findReaderByNationalId(String nationalId) {
        // Validate input
        if (nationalId == null || nationalId.isEmpty()) {
            return null;
        }

        // Remove spaces at beginning and end
        nationalId = nationalId.trim();

        for (Reader reader : readerList) {
            // Match reader national ID
            if (reader.getNationalId().equals(nationalId)) {
                return reader;
            }
        }

        // Reader not found
        return null;
    }

    // ================= FIND READER BY FULL NAME =================
    public List<Reader> findReaderByFullName(String keyword) {
        List<Reader> searchReaderList = new ArrayList<>();

        // Validate input
        if (keyword == null || keyword.trim().isEmpty()) {
            return null;
        }

        // Remove spaces at beginning and end
        keyword = keyword.trim();

        for (Reader reader : readerList) {
            // Partial match and ignore case
            if (TextUtil.containsIgnoreCase(reader.getFullName(), keyword)) {
                searchReaderList.add(reader);
            }
        }

        return searchReaderList;
    }

    // ================= COUNT TOTAL READERS =================
    public int countTotalReaders() {
        return readerList.size();
    }


    public Map<Gender, Integer> countReaderQuantityByGender() {
        // Store total quantity by gender
        Map<Gender, Integer> genderQuantityMap = new LinkedHashMap<>();

        // Initialize all genders with 0
        for (Gender gender : Gender.values()) {

            // Skip NONE gender
            if (gender == Gender.NONE) {
                continue;
            }

            genderQuantityMap.put(gender, 0);
        }

        // Count quantity for each gender
        for (Reader reader : readerList) {
            Gender gender = reader.getGender();

            // Get current gender quantity
            int currentQuantity = genderQuantityMap.get(gender);

            if (genderQuantityMap.containsKey(gender)) {
                // Update total quantity
                genderQuantityMap.put(gender, currentQuantity + 1);
            }
        }

        return genderQuantityMap;
    }
}
