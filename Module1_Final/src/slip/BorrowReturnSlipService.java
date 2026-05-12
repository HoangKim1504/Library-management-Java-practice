package slip;

import book.Book;
import util.DateUtil;
import validator.InputValidator;

import java.time.LocalDate;

public class BorrowReturnSlipService {
    // ================= INPUT BORROW BOOK SLIP INFO =================
    public Book inputBookInfo() {
        // Generate borrowId
        String bookId = generateNewBorrowId();
        if (bookId == null) {
            System.out.println("Không thể tạo mã phiếu mượn!");
            return null;
        }

        // ReaderId
        String readerId = InputValidator.inputValidString("Mã độc giả: ", InputValidator::isValidReaderId);

        // Borrow date
        String inputBorrowDate = InputValidator.inputValidString("Ngày mượn: ", InputValidator::isValidDate);
        LocalDate borrowDate = DateUtil.parseLocalDate(inputBorrowDate, "yyyy-MM-dd");

        // Expected return date
        String inputExpectedReturnDate = InputValidator.inputValidString("Ngày trả dự kiến: ", InputValidator::isValidDate);
        LocalDate expectedReturnDate = DateUtil.parseLocalDate(inputBorrowDate, "yyyy-MM-dd");

        // Actual return date
        String inputActualReturnDate = InputValidator.inputValidString("Ngày trả thực tế: ", InputValidator::isValidDate);
        LocalDate actualReturnDate = DateUtil.parseLocalDate(inputBorrowDate, "yyyy-MM-dd");

        // Create borrow book slip object
        return new BorrowReturnSlip(
                bookId,
                readerId,
                borrowDate,
                expectedReturnDate,
                actualReturnDate
        );
    }
}
