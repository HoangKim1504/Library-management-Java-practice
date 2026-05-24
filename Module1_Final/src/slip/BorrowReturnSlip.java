package slip;

import book.Book;
import book.BookService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BorrowReturnSlip {
    private String borrowId;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private List<String> borrowBookIsbns;
    private List<String> lostBookIsbns;

    public BorrowReturnSlip() {
    }

    public BorrowReturnSlip(String borrowId, String readerId, LocalDate borrowDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, List<String> borrowBookIsbns, List<String> lostBookIsbns) {
        this.borrowId = borrowId;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.borrowBookIsbns = borrowBookIsbns;
        this.lostBookIsbns = lostBookIsbns;
    }

    // ================= CREATE BORROW SLIP =================
    public BorrowReturnSlip(String borrowId, String readerId, LocalDate borrowDate, LocalDate expectedReturnDate, List<String> borrowBookIsbns) {
        this.borrowId = borrowId;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.borrowBookIsbns = borrowBookIsbns;
    }

    // ================= CREATE RETURN SLIP =================
    public BorrowReturnSlip(String borrowId, String readerId, LocalDate actualReturnDate, List<String> lostBookIsbns) {
        this.borrowId = borrowId;
        this.readerId = readerId;
        this.actualReturnDate = actualReturnDate;
        this.lostBookIsbns = lostBookIsbns;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public String getReaderId() {
        return readerId;
    }

    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getExpectedReturnDate() {
        return expectedReturnDate;
    }

    public void setExpectedReturnDate(LocalDate expectedReturnDate) {
        this.expectedReturnDate = expectedReturnDate;
    }

    public LocalDate getActualReturnDate() {
        return actualReturnDate;
    }

    public void setActualReturnDate(LocalDate actualReturnDate) {
        this.actualReturnDate = actualReturnDate;
    }

    public List<String> getBorrowBookIsbns() {
        return borrowBookIsbns;
    }

    public void setBorrowBookIsbns(List<String> borrowBookIsbns) {
        this.borrowBookIsbns = borrowBookIsbns;
    }

    public List<String> getLostBookIsbns() {
        return lostBookIsbns;
    }

    public void setLostBookIsbns(List<String> lostBookIsbns) {
        this.lostBookIsbns = lostBookIsbns;
    }

    @Override
    public String toString() {
        return "\n===== THÔNG TIN PHIẾU MƯỢN/TRẢ =====" +
                " Mã phiếu mượn: " + borrowId + '\'' +
                ", Mã độc giả: " + readerId + '\'' +
                ", Ngày mượn: " + borrowDate + '\'' +
                ", Ngày trả dự kiến: " + expectedReturnDate + '\'' +
                ", Ngày trả thực tế: " + actualReturnDate + '\'' +
                ", Danh sách ISBN sách mượn: " + borrowBookIsbns + '\'' +
                ", Danh sách ISBN sách mất: " + lostBookIsbns + '\'';
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

    // ================= CALCULATE LATE FEE =================
    public static double calculateLateFee(BorrowReturnSlip returnSlip, BorrowReturnSlip currentSlip,
                                          double lateFeePerDay) {
        // Validate slips
        if (returnSlip == null || currentSlip == null) {
            return 0;
        }

        // Calculate late days
        long lateDays = calculateLateDays(currentSlip.getExpectedReturnDate(), returnSlip.getActualReturnDate());

        // Calculate total late fee
        return lateDays * lateFeePerDay;
    }

    // ================= CALCULATE LOST BOOK FEE =================
    public static double calculateLostBookFee(BorrowReturnSlip returnSlip,
                                              double lostBookFeeRatio, BookService bookService) {
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

        double totalLostBookFee = 0;

        // Calculate lost book fee
        for (String isbn : lostBookIsbnList) {
            Book currentBook = bookService.findCurrentBook(isbn);

            // Skip invalid book
            if (currentBook == null) {
                System.out.println("Không tìm thấy sách có mã ISBN: " + isbn);
                continue;
            }

            double bookPrice = currentBook.getPrice();
            totalLostBookFee += bookPrice * lostBookFeeRatio;
        }

        return totalLostBookFee;
    }
}