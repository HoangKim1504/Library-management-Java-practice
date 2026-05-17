package slip;

import reader.Reader;
import reader.ReaderService;
import util.DateUtil;
import util.InputUtil;
import validator.InputValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BorrowReturnSlipService {
    private static final Scanner sc = new Scanner(System.in);

    // ================= STORE ALL BORROW RETURN SLIPS =================
    private final List<BorrowReturnSlip> borrowReturnList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW BORROW SLIP =================
    public boolean createBorrowSlip(BorrowReturnSlip borrowSlip) {
        // Validate borrow slip object
        if (borrowSlip == null) {
            System.out.println("Phiếu mượn sách không hợp lệ!");
            return false;
        }

        // Check duplicate borrow ID
        if (findCurrentBorrowReturnSlip(borrowSlip.getBorrowId()) != null) {
            System.out.println("Phiếu mượn sách đã tồn tại!");
            return false;
        }

        // Add new borrow slip
        borrowReturnList.add(borrowSlip);

        return true;
    }

    // ================= CREATE NEW RETURN SLIP =================
    public BorrowReturnSlip updateBorrowSlip(BorrowReturnSlip returnSlip) {
        // Validate return slip object
        if (returnSlip == null) {
            System.out.println("Phiếu trả sách không hợp lệ!");
            return null;
        }

        BorrowReturnSlip currentSlip = findCurrentBorrowReturnSlip(returnSlip.getBorrowId());

        // Check exist of borrow slip
        if (currentSlip == null) {
            System.out.println("Phiếu mượn sách không tồn tại!");
            return null;
        }

        // Update borrow slip
        currentSlip.setActualReturnDate(returnSlip.getActualReturnDate());
        currentSlip.setLostBookIsbns(returnSlip.getLostBookIsbns());

        return currentSlip;
    }

    // ================= FIND CURRENT BORROW RETURN SLIP =================
    public BorrowReturnSlip findCurrentBorrowReturnSlip(String borrowId) {
        // Validate input
        if (borrowId == null || borrowId.isEmpty()) {
            return null;
        }

        // Remove spaces at beginning and end
        borrowId = borrowId.trim();

        for (BorrowReturnSlip slip : borrowReturnList) {
            //  Match borrow ID
            if (slip.getBorrowId().equals(borrowId)) {
                return slip;
            }
        }

        // Borrow slip not found
        return null;
    }

    // ================= GENERATE NEW BORROW ID =================
    public String generateNewBorrowId() {
        // Default first borrow ID
        if (borrowReturnList.isEmpty()) {
            return "BR0001";
        }

        // Get last borrow slip
        BorrowReturnSlip lastSlip = borrowReturnList.getLast();

        // Get last borrow ID (Remove BR prefix)
        String lastBorrowId = lastSlip.getBorrowId().replace("BR", "");

        // Create new borrow ID
        try {
            // Convert borrow ID to integer
            int id = Integer.parseInt(lastBorrowId);
            // Generate next borrow ID
            return String.format("BR%04d", id + 1); // keep format: BR0001, BR0002, ...
        } catch (NumberFormatException e) {
            System.out.println("Mã phiếu mượn không hợp lệ!");
            return null;
        }
    }

    // ================= ADD BORROW BOOK ISBN =================
    public boolean addBorrowBookIsbn(String prompt, List<String> bookIsbnList) {
        // Input ISBN
        String bookIsbn = InputValidator.inputValidString(prompt, InputValidator::isValidBookId);

        // Check duplicate ISBN
        for (String isbn : bookIsbnList) {
            if (bookIsbn.equals(isbn)) {
                System.out.println("Mã ISBN (mã sách) đã tồn tại!");
                return false;
            }
        }

        // Add new ISBN
        bookIsbnList.add(bookIsbn);

        return true;
    }

    // ================= INPUT BORROW SLIP INFO =================
    public BorrowReturnSlip inputBorrowSlipInfo(ReaderService readerService) {
        // Generate borrow ID
        String borrowId = generateNewBorrowId();

        if (borrowId == null) {
            System.out.println("Không thể tạo mã phiếu mượn sách!");
            return null;
        }

        // Input reader ID
        String readerId;
        while (true) {
            readerId = InputValidator.inputValidString("Mã độc giả: ", InputValidator::isValidReaderId);
            Reader findReader = readerService.findCurrentReader(readerId);
            if (findReader == null) {
                System.out.println("Không tìm thấy mã độc giả!");
                continue;
            }
            break;
        }

        // Input borrow date
        String inputBorrowDate = InputValidator.inputValidString("Ngày mượn: ", InputValidator::isValidDate);
        LocalDate borrowDate = DateUtil.parseLocalDate(inputBorrowDate, "yyyy-MM-dd");

        // Input expected return date
        LocalDate expectedReturnDate;
        while (true) {
            String inputExpectedReturnDate = InputValidator.inputValidString("Ngày trả dự kiến: ", InputValidator::isValidDate);
            expectedReturnDate = DateUtil.parseLocalDate(inputExpectedReturnDate, "yyyy-MM-dd");
            boolean isValidReturnDate = InputValidator.isValidReturnDate(borrowDate, expectedReturnDate);
            if (!isValidReturnDate) {
                System.out.println("Ngày dự kiến trả phải sau ngày hoặc bằng ngày mượn sách!");
                continue;
            }
            break;
        }

        // Input borrowed ISBN list
        List<String> borrowBookIsbnList = new ArrayList<>();
        while (true) {
            boolean isSuccess = addBorrowBookIsbn("Nhập 1 mã sách mượn: ", borrowBookIsbnList);
            if (!isSuccess) continue;

            char ans = InputUtil.readYesNo("Có tiếp tục nhập mã sách không?");
            if (ans == 'y') continue;
            if (ans == 'n') break;
        }

        // Create borrow slip object
        return new BorrowReturnSlip(
                borrowId,
                readerId,
                borrowDate,
                expectedReturnDate,
                borrowBookIsbnList
        );
    }

    // ================= ADD LOST BOOK ISBN =================
    public boolean addLostBookIsbn(String prompt, List<String> lostIsbnList, List<String> borrowIsbnList) {
        // Input lost book ISBN
        String lostIsbn = InputValidator.inputValidString(prompt, InputValidator::isValidBookId);

        // Check duplicate ISBN
        for (String borrowIsbn : borrowIsbnList) {
            if (!lostIsbn.equals(borrowIsbn)) {
                System.out.println("Không tìm thấy mã sách trong danh sách mã sách đã mượn!");
                return false;
            }
        }

        // Add new ISBN
        lostIsbnList.add(lostIsbn);

        return true;
    }

    // ================= INPUT RETURN SLIP INFO =================
    public BorrowReturnSlip inputReturnSlipInfo(ReaderService readerService) {
        // Input borrow ID
        String borrowId;
        BorrowReturnSlip findBorrowSlip;
        while (true) {
            borrowId = InputValidator.inputValidString("Mã phiếu mượn sách: ", InputValidator::isValidBorrowId);
            findBorrowSlip = findCurrentBorrowReturnSlip(borrowId);
            if (findBorrowSlip == null) {
                System.out.println("Không tìm thấy mã phiếu mượn sách!");
                continue;
            }
            break;
        }

        // Input reader ID
        String readerId;
        while (true) {
            readerId = InputValidator.inputValidString("Mã độc giả: ", InputValidator::isValidReaderId);
            Reader findReader = readerService.findCurrentReader(readerId);
            if (findReader == null) {
                System.out.println("Không tìm thấy mã độc giả!");
                continue;
            }
            break;
        }

        // Input actual return date
        LocalDate actualReturnDate;
        while (true) {
            String inputActualReturnDate = InputValidator.inputValidString("Ngày trả thực tế: ", InputValidator::isValidDate);
            actualReturnDate = DateUtil.parseLocalDate(inputActualReturnDate, "yyyy-MM-dd");
            boolean isValidReturnDate = InputValidator.isValidReturnDate(findBorrowSlip.getBorrowDate(), actualReturnDate);
            if (!isValidReturnDate) {
                System.out.println("Ngày thực tế trả phải sau ngày hoặc bằng ngày mượn sách!");
                continue;
            }
            break;
        }

        // Display borrowed ISBN list
        List<String> borrowBookIsbnList = findBorrowSlip.getBorrowBookIsbns();
        System.out.println("Danh sách mã sách mượn: " + borrowBookIsbnList);

        // Input lost ISBN list
        List<String> lostBookIsbnList = new ArrayList<>();

        while (true) {
            char ans = InputUtil.readYesNo("Có sách bị mất không?");
            if (ans == 'y') continue;
            if (ans == 'n') break;

            boolean isSuccess = addLostBookIsbn("Nhập 1 mã sách bị mất: ", lostBookIsbnList, borrowBookIsbnList);
            if (!isSuccess) continue;

            char answer = InputUtil.readYesNo("Có tiếp tục nhập mã sách không?");
            if (answer == 'y') continue;
            if (answer == 'n') break;
        }

        // Create return slip object
        return new BorrowReturnSlip(
                borrowId,
                readerId,
                actualReturnDate,
                lostBookIsbnList
        );
    }

}
