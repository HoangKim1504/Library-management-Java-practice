package util;

import book.Book;
import enums.BookCategory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String BOOK_FILE = "Module1_Final/src/data/books.txt";

    // ================= LOAD BOOKS FROM FILE =================
    public static List<Book> loadBooksFromFile() {
        // Store all books loaded from file
        List<Book> bookList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(BOOK_FILE))) {
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Skip invalid book data
                if (data.length != 8) {
                    continue;
                }

                // Create book object from file data
                Book book = new Book(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Integer.parseInt(data[4]),
                        BookCategory.valueOf(data[5]),
                        Double.parseDouble(data[6]),
                        Integer.parseInt(data[7])
                );

                // Add book to list
                bookList.add(book);
            }

            System.out.println("Đọc file sách thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file sách: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu sách: " + e.getMessage());
        }

        return bookList;
    }

    // ================= SAVE BOOK LIST TO FILE =================
    public static void saveBooksToFile(List<Book> bookList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOK_FILE))) {
            // Save each book to file
            for (Book book : bookList) {
                String bookData = book.getIsbn() + ","
                        + book.getTitle() + ","
                        + book.getAuthor() + ","
                        + book.getPublisher() + ","
                        + book.getPublishYear() + ","
                        + book.getCategory() + ","
                        + book.getPrice() + ","
                        + book.getQuantity();

                writer.write(bookData);

                writer.newLine();
            }

            System.out.println("Lưu file sách thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi lưu file sách:  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu sách: " + e.getMessage());
        }
    }
}
