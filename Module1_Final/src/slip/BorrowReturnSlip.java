package slip;

import java.time.LocalDate;
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
}
