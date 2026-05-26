package util;

import book.Book;
import book.BookService;
import validator.BookValidator;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class NumberUtil {
    private static final Scanner sc = new Scanner(System.in);

    // ================= HANDLE NUMBER UPDATE BOOK DATA =================
    public static boolean handleNumberUpdateBook(int choice, String bookId, BookService bookService) {
        while (true) {
            System.out.print("Thông tin cập nhật mới: ");
            String newInfo = sc.nextLine().trim();

            // Valid input
            boolean isValid = BookValidator.isValidInput(choice, newInfo);
            if (!isValid) {
                System.out.println("Vui lòng nhập lại thông tin.");
                continue;
            }

            Book updatedBook = bookService.updateBookInfo(choice, bookId, newInfo);

            return updatedBook != null;
        }
    }

    // ================= FORMAT VIETNAMESE CURRENCY =================
    public static String formatCurrency(long price) {
        // Format number to Vietnamese currency style
        NumberFormat formatter = NumberFormat.getInstance(Locale.of("vi", "VN"));
        // Formatted price
        return formatter.format(price);
    }
}
