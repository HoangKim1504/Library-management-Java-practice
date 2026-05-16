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

    // ================= CREATE NEW BORROW RETURN SLIP =================
    public boolean createBorrowReturnSlip(BorrowReturnSlip borrowReturnSlip) {
        // Validate borrow slip object
        if (borrowReturnSlip == null) {
            System.out.println("Phiếu mượn/ trả sách không hợp lệ!");
            return false;
        }

        // Check duplicate borrow ID
        if (findCurrentBorrowReturnSlip(borrowReturnSlip.getBorrowId()) != null) {
            System.out.println("Phiếu mượn/ trả sách đã tồn tại!");
            return false;
        }

        // Add new borrow slip
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
            boolean isValidReturnDate = InputValidator.isValidReturnDate(inputBorrowDate, inputExpectedReturnDate);
            if (!isValidReturnDate) {
                System.out.println("Ngày dự kiến trả phải sau ngày hoặc bằng ngày mượn sách!");
                continue;
            }
            expectedReturnDate = DateUtil.parseLocalDate(inputExpectedReturnDate, "yyyy-MM-dd");
            break;
        }

        // Input borrowed ISBN list
        List<String> borrowBookIsbnList = new ArrayList<>();
        while (true) {
            boolean isSuccess = addBorrowBookIsbn("Nhập 1 mã sách mượn: ", borrowBookIsbnList);
            if (!isSuccess) continue;

            char ans = InputUtil.readChar("Có tiếp tục nhập mã sách không?");
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
}
