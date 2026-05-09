package book;

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
}
