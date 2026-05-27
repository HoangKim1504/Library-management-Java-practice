package slip;

import book.Book;
import book.BookService;
import reader.Reader;
import reader.ReaderService;
import util.DateUtil;
import util.FileUtil;
import util.InputUtil;
import validator.InputValidator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BorrowReturnSlipService {
    // ================= STORE ALL BORROW RETURN SLIPS =================
    private final List<BorrowReturnSlip> borrowReturnList;

    // ================= CONSTRUCTOR =================
    public BorrowReturnSlipService() {
        borrowReturnList = FileUtil.loadBorrowSlipsFromFile();
    }

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

        // Save updated data
        boolean isSaved = FileUtil.saveBorrowSlipsToFile(borrowReturnList);

        // Check save result
        if (!isSaved) {
            System.out.println("Lưu phiếu mượn thất bại!");
            return false;
        }

        return true;
    }

    // ================= CALCULATE LATE DAYS =================
    public static long calculateLateDays(LocalDate expectedReturnDate, LocalDate actualReturnDate) {
        // Invalid return dates
        if (expectedReturnDate == null || actualReturnDate == null) {
            return 0;
        }

        // Return on time
        if (!actualReturnDate.isAfter(expectedReturnDate)) {
            return 0;
        }

        // Calculate late days
        return ChronoUnit.DAYS.between(expectedReturnDate, actualReturnDate);
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

    // ================= CALCULATE LATE FEE =================
    public long calculateLateFee(BorrowReturnSlip returnSlip, BorrowReturnSlip currentSlip,
                                        long lateFeePerDay) {
        // Validate slips
        if (returnSlip == null || currentSlip == null) {
            return 0;
        }

        // Calculate late days
        long lateDays = calculateLateDays(currentSlip.getExpectedReturnDate(), returnSlip.getActualReturnDate());

        // Calculate total late fee
        return lateDays * lateFeePerDay;
    }

    // ================= ADD LOST BOOK ISBN =================
    public boolean addLostBookIsbn(String lostBookIsbn, List<String> lostBookIsbnList, List<String> borrowedBookIsbnList) {
        // Check borrowed ISBN exists
        boolean isBorrowedBook = false;

        for (String borrowedIsbn : borrowedBookIsbnList) {
            if (lostBookIsbn.equals(borrowedIsbn)) {
                isBorrowedBook = true;
                break;
            }
        }

        // ISBN not found in borrowed list
        if (!isBorrowedBook) {
            System.out.println("Không tìm thấy mã sách trong danh sâch đã mượn!");
            return false;
        }

        // Check duplicate lost ISBN
        if (lostBookIsbnList.contains(lostBookIsbn)) {
            System.out.println("Mã sách bị mất đã tồn tại!");
            return false;
        }

        // Add lost ISBN
        lostBookIsbnList.add(lostBookIsbn);

        return true;
    }

    // ================= CALCULATE LOST BOOK FEE =================
    public long calculateLostBookFee(BorrowReturnSlip returnSlip,
                                            long lostBookFeeRatio, BookService bookService) {
        // Validate return slip
        if (returnSlip == null) {
            return 0;
        }

        // Get lost book ISBN list
        List<String> lostBookIsbnList = returnSlip.getLostBookIsbns();

        // No lost books
        if (lostBookIsbnList == null || lostBookIsbnList.isEmpty()) {
            return 0;
        }

        long totalLostBookFee = 0;

        // Calculate lost book fee
        for (String isbn : lostBookIsbnList) {
            Book currentBook = bookService.findCurrentBook(isbn);

            // Skip invalid book
            if (currentBook == null) {
                System.out.println("Không tìm thấy sách có mã ISBN: " + isbn);
                continue;
            }

            long bookPrice = currentBook.getPrice();
            totalLostBookFee += bookPrice * lostBookFeeRatio;
        }

        return totalLostBookFee;
    }

    // ================= CREATE NEW RETURN SLIP =================
    public BorrowReturnSlip updateBorrowSlip(BorrowReturnSlip returnSlip, long lateFee, long lostBookFee) {
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
        if (lateFee != 0) {
            currentSlip.setLateFee(lateFee);
        }
        if (lostBookFee != 0) {
            currentSlip.setLostBookFee(lostBookFee);
        }
        if (lateFee != 0 || lostBookFee != 0) {
            currentSlip.setTotalPenaltyFee(lateFee + lostBookFee);
        }

        // Save updated data
        FileUtil.saveBorrowSlipsToFile(borrowReturnList);

        return currentSlip;
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
            boolean isValidExpectedReturnDate = InputValidator.isValidExpectedReturnDate(borrowDate, expectedReturnDate);
            if (!isValidExpectedReturnDate) {
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
                null,
                borrowBookIsbnList,
                new ArrayList<>(),
                0,
                0,
                0
        );
    }

    // ================= INPUT RETURN SLIP INFO =================
    public BorrowReturnSlip inputReturnSlipInfo(ReaderService readerService) {
        // Find current borrow slip
        BorrowReturnSlip currentBorrowSlip;
        String borrowId;

        while (true) {
            borrowId = InputValidator.inputValidString("Mã phiếu mượn sách: ", InputValidator::isValidBorrowId);
            currentBorrowSlip = findCurrentBorrowReturnSlip(borrowId);

            // Borrow slip not found
            if (currentBorrowSlip == null) {
                System.out.println("Không tìm thấy mã phiếu mượn sách!");
                continue;
            }

            // Borrow slip already returned
            if (currentBorrowSlip.getActualReturnDate() != null) {
                System.out.println("Phiếu mượn sách đã được trả. Vui lòng chọn phiếu khác!");
                continue;
            }

            break;
        }

        // Input reader ID
        String readerId;

        while (true) {
            readerId = InputValidator.inputValidString("Mã độc giả: ", InputValidator::isValidReaderId);

            // Reader does not match borrow slip
            if (!readerId.equals(currentBorrowSlip.getReaderId())) {
                System.out.println("Mã độc giả không khớp với phiếu mượn!");
                continue;
            }

            break;
        }

        // Input actual return date
        LocalDate actualReturnDate;

        while (true) {
            String inputActualReturnDate = InputValidator.inputValidString("Ngày trả thực tế: ", InputValidator::isValidDate);
            actualReturnDate = DateUtil.parseLocalDate(inputActualReturnDate, "yyyy-MM-dd");
            boolean isValidActualReturnDate = InputValidator.isValidActualReturnDate(currentBorrowSlip.getBorrowDate(), actualReturnDate);

            if (!isValidActualReturnDate) {
                continue;
            }

            break;
        }

        // Display borrowed ISBN list
        List<String> borrowBookIsbnList = currentBorrowSlip.getBorrowBookIsbns();
        System.out.println("Danh sách mã sách mượn: " + borrowBookIsbnList);

        // Store lost book ISBN list
        List<String> lostBookIsbnList = new ArrayList<>();

        // Ask user whether books are lost
        char ans = InputUtil.readYesNo("Có sách bị mất không?");

        // Input lost book ISBN list
        if (ans == 'y') {
            while (true) {
                // Input lost book ISBN
                String lostIsbn = InputValidator.inputValidString("Nhập 1 mã sách bị mất: ", InputValidator::isValidBookId);

                // Add lost ISBN
                boolean isSuccess = addLostBookIsbn(lostIsbn, lostBookIsbnList, borrowBookIsbnList);

                if (!isSuccess) {
                    continue;
                }

                // Ask continue input
                char continueAnswer = InputUtil.readYesNo("Có tiếp tục nhập mã sách không?");

                if (continueAnswer == 'n') {
                    break;
                }
            }
        }

        // Create return slip object
        return new BorrowReturnSlip(
                borrowId,
                readerId,
                actualReturnDate,
                lostBookIsbnList,
                0,
                0,
                0
        );
    }

    // ================= COUNT BORROWING BOOK QUANTITY =================
    public int countBorrowingBookQuantity() {
        int totalBorrowingBooks = 0;

        for (BorrowReturnSlip slip : borrowReturnList) {
            // Skip returned slips
            if (slip.getActualReturnDate() != null) {
                continue;
            }

            // Add quantity of borrowed books in current slips
            totalBorrowingBooks += slip.getBorrowBookIsbns().size();
        }

        return totalBorrowingBooks;
    }

    // ================= COUNT OVERDUE READER LATE DAYS =================
    public Map<String, Long> countOverDueReaderLateDays() {
        // Store overdue reader late days
        Map<String, Long> overdueReaderLateDaysMap = new LinkedHashMap<>();

        // Find overdue readers from borrow slips
        for (BorrowReturnSlip slip : borrowReturnList) {
            LocalDate expectedReturnDate = slip.getExpectedReturnDate();
            LocalDate actualReturnDate = slip.getActualReturnDate();

            // Skip unreturned or invalid slips
            if (expectedReturnDate == null || actualReturnDate == null) {
                continue;
            }

            // Skip on-time returns
            if (!actualReturnDate.isAfter(expectedReturnDate)) {
                continue;
            }

            String readerId = slip.getReaderId();
            Long lateDays = calculateLateDays(expectedReturnDate, actualReturnDate);

            // Store overdue reader late days
            overdueReaderLateDaysMap.put(readerId, lateDays);
        }

        return overdueReaderLateDaysMap;
    }
}
