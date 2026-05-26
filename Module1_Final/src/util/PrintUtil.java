package util;

import book.Book;
import enums.BookCategory;
import enums.Gender;
import reader.Reader;
import slip.BorrowReturnSlip;
import user.User;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

public class PrintUtil {
    // ================= MAIN MENU =================
    public static void printMainMenu() {
        int index = 1;

        System.out.println("\n======= MENU CHÍNH ======");
        System.out.println(index++ + ". Chức năng người dùng (đăng xuất, đổi MK, cập nhật TT, ...)");
        System.out.println(index++ + ". Quản lý độc giả");
        System.out.println(index++ + ". Quản lý sách");
        System.out.println(index++ + ". Lập phiếu mượn sách");
        System.out.println(index++ + ". Lập phiếu trả sách");
        System.out.println(index++ + ". Thống kê");
    }

    // ================= USER MENU =================
    public static void printUserMenu() {
        int index = 1;

        System.out.println("\n====== MENU NGƯỜI DÙNG ======");
        System.out.println(index++ + ". Đăng xuất");
        System.out.println(index++ + ". Thay đổi mật khẩu");
        System.out.println(index++ + ". Cập nhật thông tin cá nhân");
        System.out.println(index++ + ". Tạo người dùng");
        System.out.println("0. Quay lại");
    }

    // ================= READER MENU =================
    public static void printReaderMenu() {
        int index = 1;
        System.out.println("\n====== MENU ĐỘC GIẢ ======");
        System.out.println(index++ + ". Xem danh độc giả trong thư viện");
        System.out.println(index++ + ". Thêm độc giả");
        System.out.println(index++ + ". Chỉnh sửa thông tin một độc giả");
        System.out.println(index++ + ". Xóa thông tin một độc giả");
        System.out.println(index++ + ". Tìm kiếm độc giả theo CMND");
        System.out.println(index++ + ". Tìm kiếm độc giả theo họ tên");
        System.out.println("0. Quay lại");
    }

    // ================= BOOK MENU =================
    public static void printBookMenu() {
        int index = 1;

        System.out.println("\n====== MENU SÁCH ======");
        System.out.println(index++ + ". Xem danh sách các sách trong thư viện");
        System.out.println(index++ + ". Thêm sách");
        System.out.println(index++ + ". Chỉnh sửa thông tin một quyển sách");
        System.out.println(index++ + ". Xóa thông tin sách");
        System.out.println(index++ + ". Tìm kiếm sách theo ISBN");
        System.out.println(index++ + ". Tìm kiếm sách theo tên sách");
        System.out.println("0. Quay lại");
    }

    // ================= PRINT USER INFO =================
    public static void printUserInfo(User user, boolean isUpdate) {
        if (user == null) {
            System.out.println("Không có thông tin người dùng");
            return;
        }

        int index = 1;

        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ======");
        }

        System.out.println(index++ + ". Tên đăng nhập: " + user.getUserName());
        System.out.println(index++ + ". Họ Tên: " + user.getFullName());
        System.out.println(index++ + ". Ngày sinh: " + user.getBirthDate());
        System.out.println(index++ + ". CMND: " + user.getNationalId());
        System.out.println(index++ + ". Địa chỉ: " + user.getAddress());
        System.out.println(index++ + ". Giới tính: " + user.getGender().getDisplayName());
        System.out.println(index++ + ". Tình trạng: " + user.getStatus().getDisplayName());
        System.out.println(index++ + ". Loại người dùng: " + user.getUserType().getDisplayName());
    }

    // ================= PRINT READER INFO =================
    public static void printReaderInfo(Reader reader, boolean isUpdate) {
        if (reader == null) {
            System.out.println("Không có thông tin độc giả");
            return;
        }

        int index = 1;

        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN ĐỘC GIẢ ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN ĐỘC GIẢ ======");
            System.out.println("Mã độc giả: " + reader.getReaderId());
        }

        System.out.println(index++ + ". Họ Tên: " + reader.getFullName());
        System.out.println(index++ + ". CMND: " + reader.getNationalId());
        System.out.println(index++ + ". Ngày sinh: " + reader.getBirthDate());
        System.out.println(index++ + ". Giới tính: " + reader.getGender().getDisplayName());
        System.out.println(index++ + ". Email: " + reader.getEmail());
        System.out.println(index++ + ". Ngày lập thẻ: " + reader.getCreatedDate());
        System.out.println(index++ + ". Ngày hết hạn của thẻ (48 tháng kể từ ngày lập thẻ): " + reader.getExpiredDate());
    }

    // ================= PRINT BOOK INFO =================
    public static void printBookInfo(Book book, boolean isUpdate) {
        if (book == null) {
            System.out.println("Không có thông tin sách");
            return;
        }

        int index = 1;

        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN SÁCH ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN SÁCH ======");
            System.out.println("Mã ISBN: " + book.getIsbn());
        }

        System.out.println(index++ + ". Tên sách: " + book.getTitle());
        System.out.println(index++ + ". Tác giả: " + book.getAuthor());
        System.out.println(index++ + ". Nhà xuất bản: " + book.getPublisher());
        System.out.println(index++ + ". Năm xuất bản: " + book.getPublishYear());
        System.out.println(index++ + ". Thể loại: " + book.getCategory().getDisplayName());
        System.out.println(index++ + ". Giá sách: " + book.getPrice() + " VNĐ");
        System.out.println(index++ + ". Số lượng: " + book.getQuantity());
    }

    // ================= PRINT BORROW SLIP INFO =================
    public static void printBorrowBookSlipInfo(BorrowReturnSlip borrowReturnSlip, long lateFee, long lostBookFee) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.of("vi", "VN"));

        if (borrowReturnSlip == null) {
            System.out.println("Không có thông tin phiếu mượn");
            return;
        }

        int index = 1;

        LocalDate actualReturnDate = borrowReturnSlip.getActualReturnDate();

        if (actualReturnDate == null) {
            System.out.println("\n====== THÔNG TIN PHIẾU MƯỢN SÁCH ======");
        } else {
            System.out.println("\n====== THÔNG TIN PHIẾU TRẢ SÁCH ======");
        }

        System.out.println(index++ + ". Mã phiếu mượn: " + borrowReturnSlip.getBorrowId());
        System.out.println(index++ + ". Mã độc giả: " + borrowReturnSlip.getReaderId());
        System.out.println(index++ + ". Ngày mượn: " + borrowReturnSlip.getBorrowDate());
        System.out.println(index++ + ". Ngày trả dự kiến: " + borrowReturnSlip.getExpectedReturnDate());

        if (actualReturnDate != null) {
            System.out.println(index++ + ". Ngày trả thực tế: " + borrowReturnSlip.getActualReturnDate());
        }

        System.out.println(index++ + ". Danh sách ISBN sách mượn: " + borrowReturnSlip.getBorrowBookIsbns());

        if (actualReturnDate != null) {
            System.out.println(index++ + ". Danh sách ISBN sách bị mất: " + borrowReturnSlip.getLostBookIsbns());
            System.out.println(index++ + ". Phí mượn sách quá hạn: " + formatter.format(lateFee) + " VNĐ");
            System.out.println(index++ + ". Phí làm mất sách: " + formatter.format(lostBookFee) + " VNĐ");

            double totalFee = lateFee + lostBookFee;
            System.out.println(index++ + ". Tổng phí phạt: " + formatter.format(totalFee) + " VNĐ");
        }
    }

    // ================= DISPLAY BASIC STATISTICS =================
    public static void printBasicStatisticsInfo(boolean canViewFullStatistics, int totalBooks, Map<BookCategory,
                                                        Integer> categoryQuantityMap, int totalReaders, Map<Gender, Integer> genderQuantityMap,
                                                int totalBorrowingBooks, Map<String, Long> overdueReaderLateDaysMap) {

        int index = 1;

        System.out.println("\n====== THỐNG KÊ CƠ BẢN ======");

        // ADMIN and MANAGER statistics
        if (canViewFullStatistics) {
            // Display total books in library
            System.out.println(index++ + ". Số lượng sách trong thư viện: " + totalBooks + " quyển");

            // Display book quantity grouped by category
            System.out.println(index++ + ". Số lượng sách theo thể loại: ");
            for (Map.Entry<BookCategory, Integer> entry : categoryQuantityMap.entrySet()) {
                System.out.println(" - " + entry.getKey().getDisplayName() + ": " + entry.getValue() + " quyển");
            }

            // Display total readers
            System.out.println(index++ + ". Số lượng độc giả trong thư viện: " + totalReaders + " người");

            // Display reader quantity grouped by gender
            System.out.println(index++ + ". Số lượng độc giả theo giới tính: ");
            for (Map.Entry<Gender, Integer> entry : genderQuantityMap.entrySet()) {
                System.out.println(" - " + entry.getKey().getDisplayName() + ": " + entry.getValue() + " người");
            }
        }

        // Display books currently being borrowed
        System.out.println(index++ + ". Số sách đang được mượn: " + totalBorrowingBooks + " quyển");

        // Display overdue readers
        System.out.println(index++ + ". Danh sách độc giả bị trễ hạn: ");
        for (Map.Entry<String, Long> entry : overdueReaderLateDaysMap.entrySet()) {
            System.out.println(" - Mã độc giả: " + entry.getKey() + ", số ngày trễ: " + entry.getValue() + " ngày");
        }
    }
}
