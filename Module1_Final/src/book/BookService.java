package book;

import enums.BookCategory;
import util.InputUtil;
import validator.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class BookService {
    // ================= STORE ALL BOOKS =================
    private final List<Book> bookList = new ArrayList<>(); // Prevents accidental reassignment to the list

    // ================= CREATE NEW BOOK =================
    public boolean createBook(Book book) {
        // Check null
        if (book == null) {
            System.out.println("Sách không hợp lệ!");
            return false;
        }

        // Check duplicate ISBN code
        if (findCurrentBook(book.getIsbn()) != null) {
            System.out.println("Sách đã tồn tại!");
            return false;
        }

        bookList.add(book);
        return true;
    }

    // ================= FIND CURRENT BOOK =================
    public Book findCurrentBook(String bookId) {
        // Validate input
        if (bookId == null || bookId.isEmpty()) {
            return null;
        }

        // Remove spaces at beginning and end
        bookId = bookId.trim();

        for (Book book : bookList) {
            //  Match ISBN code
            if (book.getIsbn().equals(bookId)) {
                return book;
            }
        }

        // Book not found
        return null;
    }

    // ================= DISPLAY BOOK LIST =================
    public void showBookList() {
        // Check empty book list
        if (bookList.isEmpty()) {
            System.out.println("Danh sách của sách trống!");
            return;
        }

        System.out.println("Danh sách của sách trong thư viện: ");

        int index = 1;

        for (Book book : bookList) {
            System.out.println(index + ". " + book);
            index++;
        }
    }

    // ================= GENERATE NEW ISBN CODE =================
    public String generateNewIsbn() {
        // First book
        if (bookList.isEmpty()) {
            return "BK0001";
        }

        // Get last book
        Book lastBook = bookList.getLast();

        // Get last bookId (Remove prefix R)
        String lastBookId = lastBook.getIsbn().replace("BK", "");

        // Create new bookId
        try {
            int id = Integer.parseInt(lastBookId);
            return String.format("BK%05d", id + 1); // keep format: BK00001, R00002, ...
        } catch (NumberFormatException e) {
            System.out.println("Mã ISBN không hợp lệ!");
            return null;
        }
    }

    // ================= INPUT BOOK INFO =================
    public Book inputBookInfo() {
        // Generate bookId
        String bookId = generateNewIsbn();
        if (bookId == null) {
            System.out.println("Không thể tạo mã ISBN!");
            return null;
        }

        // Title
        String title = InputValidator.inputValidString("Tên sách: ", InputValidator::isValidBookTitle);

        // Author
        String author = InputValidator.inputValidString("Tác giả: ", InputValidator::isValidName);

        // Publisher
        String publisher = InputValidator.inputValidString("Nhà xuất bản: ", InputValidator::isValidPublisher);

        // PublishYear
        int publishYear = InputValidator.inputValidInt("Năm xuất bản: ", InputValidator::isValidYear);

        // Category
        BookCategory category = InputUtil.inputCategory();
        if (category == null) {
            System.out.println("Thể loại sách bị lỗi!");
            return null;
        }

        // Price
        double price = InputValidator.inputValidDouble("Giá sách: ", InputValidator::isValidPrice);

        // Quantity
        int quantity = InputValidator.inputValidInt("Số lượng: ", InputValidator::isValidQuantity);

        // Create book object
        return new Book(
                bookId,
                title,
                author,
                publisher,
                publishYear,
                category,
                price,
                quantity
        );
    }
}
