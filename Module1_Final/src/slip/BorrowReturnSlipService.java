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

    // ================= STORE ALL BORROW SLIPS =================
    private final List<BorrowReturnSlip> borrowReturnList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW BOOK =================
    public boolean createBorrowReturnSlip(BorrowReturnSlip borrowReturnSlip) {
        // Check null
        if (borrowReturnSlip == null) {
            System.out.println("Phiếu mượn/ trả sách không hợp lệ!");
            return false;
        }

        // Check duplicate ISBN code
        if (findCurrentBorrowReturnSlip(borrowReturnSlip.getBorrowId()) != null) {
            System.out.println("Phiếu mượn/ trả sách đã tồn tại!");
            return false;
        }

        borrowReturnList.add(borrowReturnSlip);
        return true;
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
            //  Match ISBN code
            if (slip.getBorrowId().equals(borrowId)) {
                return slip;
            }
        }

        // Book not found
        return null;
    }

    // ================= GENERATE NEW BORROW ID =================
    public String generateNewBorrowId() {
        // First borrow ID
        if (borrowReturnList.isEmpty()) {
            return "BR0001";
        }

        // Get last reader
        BorrowReturnSlip lastSlip = borrowReturnList.getLast();

        // Get last borrowId (Remove prefix BR)
        String lastBorrowId = lastSlip.getBorrowId().replace("BR", "");

        // Create new borrowId
        try {
            int id = Integer.parseInt(lastBorrowId);
            return String.format("BR%04d", id + 1); // keep format: BR0001, BR0002, ...
        } catch (NumberFormatException e) {
            System.out.println("BorrowId không hợp lệ!");
            return null;
        }
    }

    public boolean inputBookIsbn(String prompt, List<String> bookIsbnList) {
        // Validate input
        String bookIsbn = InputValidator.inputValidString(prompt, InputValidator::isValidBorrowId);

        // Empty list
        if (bookIsbnList.isEmpty()) {
            bookIsbnList.add(bookIsbn);
            return true;
        }

        // Check duplicate ISBN code
        for (String isbn : bookIsbnList) {
            if (bookIsbn.equals(isbn)) {
                System.out.println("Mã ISBN (mã sách) đã bị trùng!");
                return false;
            }
            bookIsbnList.add(bookIsbn);
            return true;
        }

        return false;
    }


    // ================= INPUT BORROW BOOK SLIP INFO =================
    public BorrowReturnSlip inputBorrowSlipInfo(ReaderService readerService) {
        // Generate borrowId
        String borrowId = generateNewBorrowId();
        if (borrowId == null) {
            System.out.println("Không thể tạo mã phiếu mượn sách!");
            return null;
        }

        // ReaderId
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

        // Borrow date
        String inputBorrowDate = InputValidator.inputValidString("Ngày mượn: ", InputValidator::isValidDate);
        LocalDate borrowDate = DateUtil.parseLocalDate(inputBorrowDate, "yyyy-MM-dd");

        // Expected return date
        LocalDate expectedReturnDate;
        while (true) {
            String inputExpectedReturnDate = InputValidator.inputValidString("Ngày trả dự kiến: ", InputValidator::isValidDate);
            boolean isValidReturnDate = InputValidator.isValidReturnDate(inputBorrowDate, inputExpectedReturnDate);
            if (!isValidReturnDate) {
                System.out.println("Ngày dự kiến trả phải sau ngày hoặc bằng ngày mượn sách!");
                continue;
            }
            expectedReturnDate = DateUtil.parseLocalDate(inputExpectedReturnDate, "yyyy-MM-dd");
            break;
        }

        // Actual return date
//        String inputActualReturnDate = InputValidator.inputValidString("Ngày trả thực tế: ", InputValidator::isValidDate);
//        LocalDate actualReturnDate = DateUtil.parseLocalDate(inputActualReturnDate, "yyyy-MM-dd");

        // Borrow list ISBN
        List<String> borrowBookIsbnList = new ArrayList<>();
        while (true) {
            boolean isSuccess = inputBookIsbn("Nhập 1 mã sách mượn: ", borrowBookIsbnList);
            if (!isSuccess) continue;

            char ans = InputUtil.readChar("Có tiếp tục nhập mã sách không?");
            if (ans == 'y') continue;
            if (ans == 'n') break;
        }

        // Lost list ISBN
//        List<String> lostBookList = new ArrayList<>();
//        char ans = InputUtil.readChar("Có sách bị mất không?");
//        if (ans == 'n') {
//            lostBookList = null;
//        }
//
//        while (true) {
//            inputBookIsbn("Nhập 1 mã sách bị mất: ", lostBookList);
//
//            char answer = InputUtil.readChar("Có tiếp tục nhập mã sách không?");
//            if (answer == 'y') continue;
//            if (answer == 'n') break;
//        }

        // Create borrow book slip object
        return new BorrowReturnSlip(
                borrowId,
                readerId,
                borrowDate,
                expectedReturnDate,
//                actualReturnDate,
                borrowBookIsbnList
//                lostBookList,
        );
    }
}
