package util;

import reader.Reader;
import user.User;

public class PrintUtil {
    // ================= MAIN MENU =================
    public static void printMainMenu() {
        System.out.println("\n======= MENU CHÍNH ======");
        System.out.println("1. Chức năng người dùng (đăng xuất, đổi MK, cập nhật TT, ...)");
        System.out.println("2. Quản lý độc giả");
        System.out.println("3. Quản lý sách");
        System.out.println("4. Lập phiếu mượn sách");
        System.out.println("5. Lập phiếu trả sách");
        System.out.println("6. Thống kê");
    }

    // ================= USER MENU =================
    public static void printUserMenu() {
        System.out.println("\n====== MENU NGƯỜI DÙNG ======");
        System.out.println("1. Đăng xuất");
        System.out.println("2. Thay đổi mật khẩu");
        System.out.println("3. Cập nhật thông tin cá nhân");
        System.out.println("4. Tạo người dùng");
        System.out.println("0. Quay lại");
    }

    // ================= READER MENU =================
    public static void printReaderMenu() {
        System.out.println("\n====== MENU ĐỘC GIẢ ======");
        System.out.println("1. Xem danh độc giả trong thư viện");
        System.out.println("2. Thêm độc giả");
        System.out.println("3. Chỉnh sửa thông tin một độc giả");
        System.out.println("4. Xóa thông tin một độc giả");
        System.out.println("5. Tìm kiếm độc giả theo CMND");
        System.out.println("6. Tìm kiếm độc giả theo họ tên");
        System.out.println("0. Quay lại");
    }

    // ================= PRINT USER INFO =================
    public static void printUserInfo(User user, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN NGƯỜI DÙNG ======");
        }
        System.out.println("1. Tên đăng nhập: " + user.getUserName());
        System.out.println("2. Họ Tên: " + user.getFullName());
        System.out.println("3. Ngày sinh: " + user.getBirthDate());
        System.out.println("4. CMND: " + user.getNationalId());
        System.out.println("5. Địa chỉ: " + user.getAddress());
        System.out.println("6. Giới tính: " + user.getGender().getDisplayName());
        System.out.println("7. Tình trạng: " + user.getStatus().getDisplayName());
        System.out.println("8. Loại người dùng: " + user.getUserType().getDisplayName());
    }

    // ================= PRINT USER INFO =================
    public static void printReaderInfo(Reader reader, boolean isUpdate) {
        if (isUpdate) {
            System.out.println("\n====== THÔNG TIN ĐỘC GIẢ ĐÃ ĐƯỢC CẬP NHẬP ======");
        } else {
            System.out.println("\n====== THÔNG TIN ĐỘC GIẢ ======");
        }
        System.out.println("1. Họ Tên: " + reader.getFullName());
        System.out.println("2. CMND: " + reader.getNationalId());
        System.out.println("3. Ngày sinh: " + reader.getBirthDate());
        System.out.println("4. Giới tính: " + reader.getGender().getDisplayName());
        System.out.println("5. Email: " + reader.getEmail());
        System.out.println("6. Ngày lập thẻ: " + reader.getCreatedDate());
        System.out.println("7. Ngày hết hạn của thẻ (48 tháng kể từ ngày lập thẻ): " + reader.getExpiredDate());
    }

}
