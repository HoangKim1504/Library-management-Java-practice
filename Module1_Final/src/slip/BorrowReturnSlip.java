package slip;

import book.Book;

import java.time.LocalDate;
import java.util.List;

public class BorrowReturnSlip {
    private String borrowId;
    private String readerId;
    private LocalDate borrowDate;
    private LocalDate expectedReturnDate;
    private LocalDate actualReturnDate;
    private List<Book> borrowBookIsbns;
    private List<Book> lostBookIsbns;

    public BorrowReturnSlip() {
    }

    public BorrowReturnSlip(String borrowId, String readerId, LocalDate borrowDate, LocalDate expectedReturnDate, LocalDate actualReturnDate, List<Book> borrowBookIsbns, List<Book> lostBookIsbns) {
        this.borrowId = borrowId;
        this.readerId = readerId;
        this.borrowDate = borrowDate;
        this.expectedReturnDate = expectedReturnDate;
        this.actualReturnDate = actualReturnDate;
        this.borrowBookIsbns = borrowBookIsbns;
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

    public List<Book> getBorrowBookIsbns() {
        return borrowBookIsbns;
    }

    public void setBorrowBookIsbns(List<Book> borrowBookIsbns) {
        this.borrowBookIsbns = borrowBookIsbns;
    }

    public List<Book> getLostBookIsbns() {
        return lostBookIsbns;
    }

    public void setLostBookIsbns(List<Book> lostBookIsbns) {
        this.lostBookIsbns = lostBookIsbns;
    }

    @Override
    public String toString() {
        return "Thông tin phiếu mượn/trả:" +
                " Mã phiếu mượn: " + borrowId + '\'' +
                ", Mã độc giả: " + readerId + '\'' +
                ", Ngày mượn: " + borrowDate + '\'' +
                ", Ngày trả dự kiến: " + expectedReturnDate + '\'' +
                ", Ngày trả thực tế: " + actualReturnDate + '\'' +
                ", Danh sách ISBN sách mượn: " + borrowBookIsbns + '\'' +
                ", Danh sách ISBN sách mất: " + lostBookIsbns + '\'';
    }
}
