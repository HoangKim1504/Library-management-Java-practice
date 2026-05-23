package library;

import book.Book;
import book.BookService;
import enums.AccountStatus;
import enums.BookCategory;
import enums.Gender;
import enums.UserType;
import reader.Reader;
import reader.ReaderService;
import slip.BorrowReturnSlip;
import slip.BorrowReturnSlipService;
import user.User;
import user.UserService;
import util.InputUtil;
import util.NumberUtil;
import util.PrintUtil;
import util.TextUtil;
import validator.InputValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LibraryManagement {

    private final Scanner sc = new Scanner(System.in);
    private final UserService userService = new UserService();
    private final ReaderService readerService = new ReaderService();
    private final BookService bookService = new BookService();
    private final BorrowReturnSlipService borrowReturnSlipService = new BorrowReturnSlipService();

    private static final String NOT_LOGIN = "0";
    private String userId = NOT_LOGIN;

    private static final String DEFAULT_USERNAME = "default";
    private static final String DEFAULT_PASSWORD = "default";
    private static final UserType ADMIN = UserType.ADMIN;
    private static final UserType MANAGER = UserType.MANAGER;
    private static final UserType USER = UserType.USER;

    private static final double LATE_FEE_PER_DAY = 5000;
    private static final double LOST_BOOK_FEE_RATIO = 2.0;

    public static void main(String[] args) {
        LibraryManagement app = new LibraryManagement();
        app.createInitData();
        app.run();
    }

    // ================= INIT USER DATA =================
    public void createInitData() {
        // User data
        User admin = new User(
                "admin", "admin", "Admin system", LocalDate.of(1990, 1, 1),
                "123456789875", "TP.HCM", Gender.MALE, AccountStatus.ACTIVATED, UserType.ADMIN, "0001");
        User manager = new User(
                "manager", "manager", "Manager", LocalDate.of(1995, 12, 1),
                "987654321954", "TP.HCM", Gender.FEMALE, AccountStatus.ACTIVATED, UserType.MANAGER, "0002");
        User user1 = new User(
                "user1", "user1", "User1", LocalDate.of(1994, 8, 17),
                "159753852851", "TP.Ha Noi", Gender.MALE, AccountStatus.ACTIVATED, UserType.USER, "0003");
        User user2 = new User(
                "user2", "user2", "User2", LocalDate.of(1999, 5, 20),
                "456789158487", "TP.Can Tho", Gender.FEMALE, AccountStatus.ACTIVATED, UserType.USER, "0004");
        User user3 = new User(
                "user3", "user3", "User3", LocalDate.of(2000, 1, 15),
                "789541259851", "TP.HCM", Gender.MALE, AccountStatus.BLOCK, UserType.USER, "0005");

        // Reader data
        Reader reader1 = new Reader(
                "R0001", "Nguyen Van An", "123654789852", LocalDate.of(2006, 8, 17),
                Gender.MALE, "an@gmail.com", "Ho Chi Minh City", LocalDate.of(2026, 5, 6));
        Reader reader2 = new Reader(
                "R0002", "Tran Thi Bich", "456987123654", LocalDate.of(2004, 3, 12),
                Gender.FEMALE, "bich@gmail.com", "Da Nang", LocalDate.of(2026, 5, 6));
        Reader reader3 = new Reader(
                "R0003", "Le Minh Khang", "789456123852", LocalDate.of(2002, 11, 25),
                Gender.MALE, "khang@gmail.com", "Can Tho", LocalDate.of(2026, 5, 6));
        Reader reader4 = new Reader(
                "R0004", "Pham Ngoc Ha", "321654987456", LocalDate.of(2005, 1, 5),
                Gender.FEMALE, "ha@gmail.com", "Binh Duong", LocalDate.of(2026, 5, 6));
        Reader reader5 = new Reader(
                "R0005", "Vo Thanh Khang", "852741963258", LocalDate.of(2001, 9, 30),
                Gender.MALE, "khang123@gmail.com", "Ha Noi", LocalDate.of(2026, 5, 6));

        // Book data
        Book book1 = new Book(
                "BK00001", "Lập Trình Java Cơ Bản", "Nguyễn Văn A", "NXB Giáo Dục", 2020,
                BookCategory.PROGRAMMING, 180000, 12);
        Book book2 = new Book(
                "BK00002", "Dế Mèn Phiêu Lưu Ký", "Tô Hoài", "NXB Kim Đồng", 2019,
                BookCategory.NOVEL, 75000, 15);
        Book book3 = new Book(
                "BK00003", "Lược Sử Việt Nam", "Trần Trọng Kim", "NXB Văn Học", 2021,
                BookCategory.HISTORY, 120000, 10);
        Book book4 = new Book(
                "BK00004", "Vũ Trụ Trong Vỏ Hạt Dẻ", "Stephen Hawking", "NXB Trẻ", 2018,
                BookCategory.SCIENCE, 210000, 8);
        Book book5 = new Book(
                "BK00005", "Nghệ Thuật Sống", "Nhiều tác giả", "NXB Tổng Hợp", 2022,
                BookCategory.OTHER, 95000, 20);
        Book book6 = new Book(
                "BK00006", "Sự im lặng của bầy cừu", "Thomas Harris", "NXB Nhã Nam", 2015,
                BookCategory.NOVEL, 115000, 30);

        // Borrow slip data
        BorrowReturnSlip borrowSlip1 = new BorrowReturnSlip(
                "BR0001", "R0001", LocalDate.of(2026, 5, 1), LocalDate.of(2026, 5, 8),
                null, List.of("BK00001", "BK00003"), List.of());
        BorrowReturnSlip borrowSlip2 = new BorrowReturnSlip(
                "BR0002", "R0002", LocalDate.of(2026, 5, 3), LocalDate.of(2026, 5, 10),
                LocalDate.of(2026, 5, 9), List.of("BK00002", "BK00004"), List.of());
        BorrowReturnSlip borrowSlip3 = new BorrowReturnSlip(
                "BR0003", "R0003", LocalDate.of(2026, 5, 5), LocalDate.of(2026, 5, 12),
                LocalDate.of(2026, 5, 15), List.of("BK00001", "BK00005"), List.of("BK00005"));
        BorrowReturnSlip borrowSlip4 = new BorrowReturnSlip(
                "BR0004", "R0004", LocalDate.of(2026, 5, 7), LocalDate.of(2026, 5, 14),
                null, List.of("BK00003"), List.of());
        BorrowReturnSlip borrowSlip5 = new BorrowReturnSlip(
                "BR0005", "R0005", LocalDate.of(2026, 5, 8), LocalDate.of(2026, 5, 15),
                LocalDate.of(2026, 5, 15), List.of("BK00002", "BK00004", "BK00005"), List.of());

        // Create users
        userService.createUser(admin);
        userService.createUser(manager);
        userService.createUser(user1);
        userService.createUser(user2);
        userService.createUser(user3);

        // Create readers
        readerService.createReader(reader1);
        readerService.createReader(reader2);
        readerService.createReader(reader3);
        readerService.createReader(reader4);
        readerService.createReader(reader5);

        // Create books
        bookService.createBook(book1);
        bookService.createBook(book2);
        bookService.createBook(book3);
        bookService.createBook(book4);
        bookService.createBook(book5);
        bookService.createBook(book6);

        // Create borrow slips
        borrowReturnSlipService.createBorrowSlip(borrowSlip1);
        borrowReturnSlipService.createBorrowSlip(borrowSlip2);
        borrowReturnSlipService.createBorrowSlip(borrowSlip3);
        borrowReturnSlipService.createBorrowSlip(borrowSlip4);
        borrowReturnSlipService.createBorrowSlip(borrowSlip5);
    }

    // ================= MAIN PROGRAM FLOW =================
    public void run() {
        while (true) {
            // Force user to login before using system
            requireLogin();

            // Main menu
            PrintUtil.printMainMenu();
            int choice = InputUtil.readNum("Chọn: ");

            switch (choice) {
                case 1:
                    userScreen();
                    break;
                case 2:
                    readerScreen();
                    break;
                case 3:
                    bookScreen();
                    break;
                case 4:
                    borrowBookSlipScreen();
                    break;
                case 5:
                    returnBookSlipScreen();
                    break;
                case 6:
                    basicStatisticsScreen();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= REQUIRE LOGIN =================
    private void requireLogin() {
        while (userId.equals(NOT_LOGIN)) {
            boolean isLogin = loginScreen();
            if (!isLogin) {
                System.out.println("Thoát chương trình...");
                System.exit(0); // exit program
            }
        }
    }

    // ================= LOGIN FUNCTION =================
    public boolean loginScreen () {
        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng nhập");
            System.out.println("0. Thoát chương trình");

            int choice = InputUtil.readNum("Chọn: ");

            if (choice == 0) return false;

            if (choice != 1) {
                System.out.println("Lựa chọn không hợp lệ!");
                continue;
            }

            // Input username & password
            System.out.print("Tên đăng nhập: ");
            String userName = sc.nextLine().trim();

            System.out.print("Mật khẩu: ");
            String password = sc.nextLine();

            // Call service to authenticate
            User user = userService.login(userName, password);

            if (user == null) {
                System.out.println("Đăng nhập thất bại (sai tài khoản/ mật khẩu hoặc tài khoản bị khoá).");
            } else {
                // Save logged-in userId
                this.userId = user.getUserId();

                System.out.println("Xin chào, " + user.getUserName() + " (" + user.getUserType().getDisplayName() + ").");
                return true;
            }
        }
    }

    // ================= USER MENU =================
    public void userScreen() {
        while (true) {
            // User menu
            PrintUtil.printUserMenu();
            int choice = InputUtil.readNum("Chọn: ");

            switch (choice) {
                case 1:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Logout current user
                    userService.logout(userId);

                    // Reset login state
                    userId = NOT_LOGIN;

                    System.out.println("Đăng xuất thành công!");

                    return;
                case 2:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Navigate to change password screen
                    changePassScreen();

                    // If password changed successfully, userId will be reset
                    // → exit this screen to trigger re-login
                    if (userId.equals(NOT_LOGIN)) {
                        return;
                    }

                    break;
                case 3:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Navigate to update user info screen
                    updateUserInfoScreen();

                    break;
                case 4:
                    // Only ADMIN can create user
                    if (userService.requireRole(userId, ADMIN)) continue;

                    // Navigate to create user screen
                    createUserScreen();

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= CHANGE PASSWORD FUNCTION =================
    public void changePassScreen () {
        while (true) {
            System.out.println("\n====== ĐỔI MẬT KHẨU ======");

            // Input old password
            System.out.print("Nhập mật khẩu cũ: ");
            String oldPass = sc.nextLine();

            // Password rules
            System.out.println("Mật khẩu mạnh là mật khẩu:");
            System.out.println("\uF0A7 Có ít nhất 8 ký tự");
            System.out.println("\uF0A7 Có ít nhất 1 chữ in hoa, 1 chữ thường, 1 số, 1 ký tự đặc biệt (!@#$%^&*)");

            // Input new password
            System.out.print("Nhập mật khẩu mới: ");
            String newPass = sc.nextLine();
            System.out.print("Nhập lại mật khẩu mới: ");
            String confirmPass = sc.nextLine();

            // Validate passwords
            if (!InputValidator.isValidPasswords(oldPass, newPass, confirmPass)) continue;

            // Call service to update password
            boolean isChanged = userService.changePassword(userId, oldPass, newPass);

            if (isChanged) {
                System.out.println("Thay đổi mật khẩu thành công!");
                userId = NOT_LOGIN; // force to log in again
                break;
            } else {
                System.out.println("Mật khẩu cũ không đúng!");
            }
        }
    }

    // ================= UPDATE USER INFO FUNCTION =================
    public void updateUserInfoScreen() {
        boolean isSuccess = false;

        // Find current user
        User user = userService.findCurrentUser(userId);

        if (user == null) {
            System.out.println("Không tìm thấy người dùng!");
            return;
        }

        // Print user info
        PrintUtil.printUserInfo(user, isSuccess);
        int choice = InputUtil.readNum("Chọn thông tin muốn cập nhật (1-8) hoặc chọn 0 để quay lại menu người dùng: ");

        if (choice == 0) return;

        switch (choice) {
            case 6: // Gender (use Menu)
                Gender gender = InputUtil.inputGender();
                if (gender == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, gender) != null;
                break;
            case 7: // AccountStatus (use Menu)
                AccountStatus accountStatus = InputUtil.inputStatus();
                if (accountStatus == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, accountStatus) != null;
                break;
            case 8: // UserType (use Menu)
                UserType userType = InputUtil.inputUserType();
                if (userType == null) return;

                isSuccess = userService.updateUserInfo(choice, userId, userType) != null;
                break;
            case 1, 2, 3, 4, 5:
                // Handle normal string input
                isSuccess = TextUtil.handleTextUpdateUser(choice, userId, userService);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }

        // Update fail
        if (!isSuccess) {
            System.out.println("Cập nhật thông tin thất bại. Vui lòng thử lại.");
            return;
        }

        // Update successfully
        System.out.println("Cập nhật thông tin thành công!");
        PrintUtil.printUserInfo(user, isSuccess);
    }

    // ================= CREATE USER FUNCTION =================
    public void createUserScreen() {
        while (true) {
            System.out.println("\n====== TẠO NGƯỜI DÙNG MỚI ======");

            // Input user info
            User user = userService.inputUserInfo(DEFAULT_USERNAME, DEFAULT_PASSWORD);
            if (user == null) continue;

            // Create new user
            boolean isSuccess = userService.createUser(user);

            // Create user fail
            if (!isSuccess) {
                System.out.println("Tạo người dùng thất bại!");
                continue;
            }

            // Create user successfully
            System.out.println("Tạo người dùng thành công!");
            PrintUtil.printUserInfo(user, false);
            return;
        }
    }

    // ================= READER MENU =================
    public void readerScreen() {
        while (true) {
            // Reader menu
            PrintUtil.printReaderMenu();
            int choice = InputUtil.readNum("Chọn: ");

            switch (choice) {
                case 1:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Show all readers
                    readerService.showReaderList();

                    break;
                case 2:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Navigate to create reader screen
                    createReaderScreen();

                    break;
                case 3:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Display all readers
                    readerService.showReaderList();
                    int readerIndexUp = InputUtil.readNum("Chọn số thứ tự của độc giả để chỉnh sửa thông tin: ");

                    // Find readerId based on selected index
                    String readerIdUp = readerService.findReaderIdByIndex(readerIndexUp);

                    // Navigate to update reader info screen
                    updateReaderInfoScreen(readerIdUp);

                    break;
                case 4:
                    // Allow ADMIN, MANAGER to delete reader
                    if (userService.requireRole(userId, ADMIN, MANAGER)) continue;

                    // Display all readers
                    readerService.showReaderList();
                    int readerIndexDel = InputUtil.readNum("Chọn số thứ tự của độc giả để xoá thông tin: ");

                    // Find readerId based on selected index
                    String readerIdDel = readerService.findReaderIdByIndex(readerIndexDel);

                    // Invalid reader index
                    if (readerIdDel == null) {
                        System.out.println("Không tìm thấy độc giả!");
                        break;
                    }

                    // Delete reader
                    boolean isDeleted = readerService.deleteReader(readerIdDel);

                    // Display updated reader list
                    if (isDeleted) {
                        readerService.showReaderList();
                    }

                    break;
                case 5:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Input reader national ID
                    String nationalId = InputValidator.inputValidString("Nhập số CMND: ", InputValidator::isValidId);

                    // Search reader by national ID
                    Reader foundReader = readerService.findReaderByNationalId(nationalId);

                    // Reader not found
                    if (foundReader == null) {
                        System.out.println("Không tìm thấy độc giả!");
                        continue;
                    }

                    // Display search result
                    System.out.println("\"===== KẾT QUẢ TÌM KIẾM =====");
                    System.out.println(foundReader);

                    break;
                case 6:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Input reader full name keyword
                    System.out.print("Nhập họ tên: ");
                    String keyword = sc.nextLine();

                    // Search readers by full name
                    List<Reader> foundReaders = readerService.findReaderByFullName(keyword);

                    // Reader not found
                    if (foundReaders.isEmpty()) {
                        System.out.println("Không tìm thấy độc giả!");
                        continue;
                    }

                    // Display search result
                    System.out.println("===== KẾT QUẢ TÌM KIẾM =====");
                    int index = 1;
                    for (Reader reader : foundReaders) {
                        System.out.println(index + ". " + reader);
                        index++;
                    }

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= CREATE READER FUNCTION =================
    public void createReaderScreen() {
        while (true) {
            System.out.println("\n====== TẠO ĐỘC GIẢ MỚI ======");

            // Input reader info
            Reader reader = readerService.inputReaderInfo();

            // Invalid reader information
            if (reader == null) {
                continue;
            }

            // Create new reader
            boolean isSuccess = readerService.createReader(reader);

            // Create reader fail
            if (!isSuccess) {
                System.out.println("Tạo độc giả thất bại!");
                continue;
            }

            // Create reader successfully
            System.out.println("Tạo độc giả thành công!");

            PrintUtil.printReaderInfo(reader, false);

            return;
        }
    }

    // ================= UPDATE READER INFO FUNCTION =================
    public void updateReaderInfoScreen(String readerId) {
        boolean isSuccess = false;

        // Find current reader
        Reader reader = readerService.findCurrentReader(readerId);

        if (reader == null) {
            System.out.println("Không tìm thấy độc giả!");
            return;
        }

        // Print reader info
        PrintUtil.printReaderInfo(reader, isSuccess);
        int choice = InputUtil.readNum("Chọn thông tin muốn cập nhật (1-7) hoặc chọn 0 để quay lại menu độc giả: ");

        if (choice == 0) return;

        if (choice == 4) {
            // Gender (use Menu)
            Gender gender = InputUtil.inputGender();
            if (gender == null) return;

            isSuccess = readerService.updateReaderInfo(choice, readerId, gender) != null;
        } else {
            // Handle normal string input
            isSuccess = TextUtil.handleTextUpdateReader(choice, readerId, readerService);
        }

        // Update fail
        if (!isSuccess) {
            System.out.println("Cập nhật thông tin thất bại. Vui lòng thử lại.");
            return;
        }

        // Update successfully
        System.out.println("Cập nhật thông tin thành công!");
        PrintUtil.printReaderInfo(reader, isSuccess);
    }

    // ================= BOOK MENU =================
    public void bookScreen() {
        while (true) {
            // Book menu
            PrintUtil.printBookMenu();
            int choice = InputUtil.readNum("Chọn: ");

            switch (choice) {
                case 1:
                    // Allow ADMIN, MANAGER to see book list
                    if (userService.requireRole(userId, ADMIN, MANAGER)) continue;

                    // Show all books
                    bookService.showBookList();

                    break;
                case 2:
                    // Allow ADMIN, MANAGER to create book
                    if (userService.requireRole(userId, ADMIN, MANAGER)) continue;

                    // Navigate to create book screen
                    createBookScreen();

                    break;
                case 3:
                    // Allow ADMIN, MANAGER to edit book info
                    if (userService.requireRole(userId, ADMIN, MANAGER)) continue;

                    // Display all books
                    bookService.showBookList();
                    int bookIndexUp = InputUtil.readNum("Chọn số thứ tự của sách để chỉnh sửa thông tin: ");

                    // Find bookId based on selected index
                    String bookIdUp = bookService.findIsbnByIndex(bookIndexUp);

                    // Navigate to update book info screen
                    updateBookInfoScreen(bookIdUp);

                    break;
                case 4:
                    // Allow ADMIN, MANAGER to delete book
                    if (userService.requireRole(userId, ADMIN, MANAGER)) continue;

                    // Display all books
                    bookService.showBookList();
                    int bookIndexDel = InputUtil.readNum("Chọn số thứ tự của sách để xoá thông tin: ");

                    // Find bookId based on selected index
                    String bookIdDel = bookService.findIsbnByIndex(bookIndexDel);

                    // Invalid book index
                    if (bookIdDel == null) {
                        System.out.println("Không tìm thấy sách!");
                        break;
                    }

                    // Delete book
                    boolean isDeleted = bookService.deleteBook(bookIdDel);

                    // Display updated book list
                    if (isDeleted) {
                        bookService.showBookList();
                    }

                    break;
                case 5:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Input book ISBN code
                    System.out.print("Nhập mã ISBN: ");
                    String bookId = sc.nextLine();

                    // Search book by ISBN code
                    Book foundBook = bookService.findCurrentBook(bookId);

                    // Reader not found
                    if (foundBook == null) {
                        System.out.println("Không tìm thấy sách!");
                        continue;
                    }

                    // Display search result
                    System.out.println("===== KẾT QUẢ TÌM KIẾM =====");
                    System.out.println(foundBook);

                    break;
                case 6:
                    // Allow all roles
                    if (userService.requireRole(userId, ADMIN, MANAGER, USER)) continue;

                    // Input book title keyword
                    System.out.print("Nhập tên sách: ");
                    String keyword = sc.nextLine();

                    // Search books by title
                    List<Book> foundBooks = bookService.findBookByTitle(keyword);

                    // Book not found
                    if (foundBooks.isEmpty()) {
                        System.out.println("Không tìm thấy sách!");
                        continue;
                    }

                    // Display search result
                    System.out.println("===== KẾT QUẢ TÌM KIẾM =====");
                    int index = 1;
                    for (Book book : foundBooks) {
                        System.out.println(index + ". " + book);
                        index++;
                    }

                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // ================= CREATE BOOK FUNCTION =================
    public void createBookScreen() {
        while (true) {
            System.out.println("\n====== TẠO SÁCH MỚI ======");

            // Input book info
            Book book = bookService.inputBookInfo();

            // Invalid book information
            if (book == null) {
                continue;
            }

            // Create new book
            boolean isSuccess = bookService.createBook(book);

            // Create book fail
            if (!isSuccess) {
                System.out.println("Tạo sách thất bại!");
                continue;
            }

            // Create book successfully
            System.out.println("Tạo sách thành công!");

            PrintUtil.printBookInfo(book, false);

            return;
        }
    }

    // ================= UPDATE BOOK INFO FUNCTION =================
    public void updateBookInfoScreen(String bookId) {
        boolean isSuccess = false;

        // Find current book
        Book book = bookService.findCurrentBook(bookId);

        if (book == null) {
            System.out.println("Không tìm thấy sách!");
            return;
        }

        // Print book info
        PrintUtil.printBookInfo(book, isSuccess);
        int choice = InputUtil.readNum("Chọn thông tin muốn cập nhật (1-7) hoặc chọn 0 để quay lại menu sách: ");

        if (choice == 0) return;

        isSuccess = switch (choice) {
            case 4, 6, 7 ->
                // Handle normal number input
                    NumberUtil.handleNumberUpdateBook(choice, bookId, bookService);
            case 1, 2, 3, 5 ->
                // Handle normal string input
                    TextUtil.handleTextUpdateBook(choice, bookId, bookService);
            default -> {
                System.out.println("Lựa chọn không hợp lệ!");
                yield false;
            }
        };

        // Update fail
        if (!isSuccess) {
            System.out.println("Cập nhật thông tin thất bại. Vui lòng thử lại.");
            return;
        }

        // Update successfully
        System.out.println("Cập nhật thông tin thành công!");
        PrintUtil.printBookInfo(book, isSuccess);
    }

    // ================= BORROW BOOK SLIP MENU =================
    public void borrowBookSlipScreen() {
        // Allow all roles
        if (userService.requireRole(userId, ADMIN, MANAGER, USER)) {
            return;
        }

        while (true) {
            System.out.println("\n====== TẠO PHIẾU MƯỢN SÁCH ======");

            // Input borrow slip info
            BorrowReturnSlip borrowSlip = borrowReturnSlipService.inputBorrowSlipInfo(readerService);

            // Invalid borrow slip
            if (borrowSlip == null) {
                System.out.println("Thông tin phiếu mượn không hợp lệ!");
                continue;
            }

            // Create borrow slip
            boolean isSuccess = borrowReturnSlipService.createBorrowSlip(borrowSlip);

            // Create fail
            if (!isSuccess) {
                System.out.println("Tạo phiếu mượn sách thất bại!");
                continue;
            }

            // Create borrow book slip successfully
            System.out.println("Tạo phiếu mượn sách thành công!");

            PrintUtil.printBorrowBookSlipInfo(borrowSlip, 0, 0);

            return;
        }
    }

    // ================= BORROW BOOK SLIP MENU =================
    public void returnBookSlipScreen() {
        // Allow all roles
        if (userService.requireRole(userId, ADMIN, MANAGER, USER)) {
            return;
        }

        while (true) {
            System.out.println("\n====== TẠO PHIẾU TRẢ SÁCH ======");

            // Input return slip info
            BorrowReturnSlip returnSlip = borrowReturnSlipService.inputReturnSlipInfo(readerService);

            // Invalid return slip
            if (returnSlip == null) {
                System.out.println("Thông tin phiếu trả không hợp lệ!");
                continue;
            }

            // Update current borrow slip
            BorrowReturnSlip updatedSlip = borrowReturnSlipService.updateBorrowSlip(returnSlip);

            // Update failed
            if (updatedSlip == null) {
                System.out.println("Tạo phiếu trả sách thất bại!");
                continue;
            }

            // Find current borrow slip
            BorrowReturnSlip currentSlip = borrowReturnSlipService.findCurrentBorrowReturnSlip(returnSlip.getBorrowId());

            // Calculate late fee
            double lateFee = BorrowReturnSlip.calculateLateFee(returnSlip, currentSlip, LATE_FEE_PER_DAY);

            // Calculate lost book fee
            double lostBookFee = BorrowReturnSlip.calculateLostBookFee(returnSlip, LOST_BOOK_FEE_RATIO, bookService);

            // Create return slip successfully
            System.out.println("Tạo phiếu trả sách thành công!");

            PrintUtil.printBorrowBookSlipInfo(updatedSlip, lateFee, lostBookFee);

            return;
        }
    }

    // ================= BASIC STATISTICS SCREEN =================
    public void basicStatisticsScreen() {
        // Only ADMIN and MANAGER can access statistics
        if (userService.requireRole(userId, ADMIN, MANAGER)) {
            return;
        }

        // Count total books
        int totalBooks = bookService.countTotalBooks();

        // Count total book quantity by category
        Map<BookCategory, Integer> bookQuantityByCategoryList = bookService.countBookQuantityByCategory();

        // Count total readers
        int totalReaders = readerService.countTotalReaders();

        // Display statistics information
        PrintUtil.printBasicStatisticsInfo(totalBooks, bookQuantityByCategoryList, totalReaders);

    }
}
