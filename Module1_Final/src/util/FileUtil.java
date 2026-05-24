package util;

import book.Book;
import enums.AccountStatus;
import enums.BookCategory;
import enums.Gender;
import enums.UserType;
import reader.Reader;
import user.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    private static final String USER_FILE = "Module1_Final/src/data/users.txt";
    private static final String READER_FILE = "Module1_Final/src/data/readers.txt";
    private static final String BOOK_FILE = "Module1_Final/src/data/books.txt";

    // ================= LOAD USERS FROM FILE =================
    public static List<User> loadUsersFromFile() {
        // Store all users loaded from file
        List<User> userList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;

            // Read line by line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Skip invalid user data
                if (data.length != 10) {
                    continue;
                }

                // Create user object from file data
                User user = new User(
                        data[0],
                        data[1],
                        data[2],
                        LocalDate.parse(data[3]),
                        data[4],
                        data[5],
                        Gender.valueOf(data[6]),
                        AccountStatus.valueOf(data[7]),
                        UserType.valueOf(data[8]),
                        data[9]
                );

                // Add user to list
                userList.add(user);
            }

            System.out.println("Đọc file người dùng thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file người dùng: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu người dùng: " + e.getMessage());
        }

        return userList;
    }

    // ================= SAVE USER LIST TO FILE =================
    public static void saveUsersToFile(List<User> userList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_FILE))) {
            // Save each user to file
            for (User user : userList) {
                String userData = user.getUserName() + ","
                        + user.getPassword() + ","
                        + user.getFullName() + ","
                        + user.getBirthDate() + ","
                        + user.getNationalId() + ","
                        + user.getAddress() + ","
                        + user.getGender() + ","
                        + user.getStatus() + ","
                        + user.getUserType() + ","
                        + user.getUserId();

                writer.write(userData);

                writer.newLine();
            }

            System.out.println("Lưu file người dùng thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi lưu file người dùng:  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu người dùng: " + e.getMessage());
        }
    }

    // ================= LOAD READERS FROM FILE =================
    public static List<Reader> loadReadersFromFile() {
        // Store all readers loaded from file
        List<Reader> readerList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(READER_FILE))) {
            String line;

            // Read line by line
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                // Skip invalid reader data
                if (data.length != 9) {
                    continue;
                }

                // Create reader object from file data
                Reader readerObj = new Reader(
                        data[0],
                        data[1],
                        data[2],
                        LocalDate.parse(data[3]),
                        Gender.valueOf(data[4]),
                        data[5],
                        data[6],
                        LocalDate.parse(data[7]),
                        LocalDate.parse(data[8])
                );

                // Add reader to list
                readerList.add(readerObj);
            }

            System.out.println("Đọc file độc giả thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi đọc file độc giả: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu độc giả: " + e.getMessage());
        }

        return readerList;
    }

    // ================= SAVE READER LIST TO FILE =================
    public static void saveReadersToFile(List<Reader> readerList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(READER_FILE))) {
            // Save each reader to file
            for (Reader reader : readerList) {
                String readerData = reader.getReaderId() + ","
                        + reader.getFullName() + ","
                        + reader.getNationalId() + ","
                        + reader.getBirthDate() + ","
                        + reader.getGender() + ","
                        + reader.getEmail() + ","
                        + reader.getAddress() + ","
                        + reader.getCreatedDate() + ","
                        + reader.getExpiredDate();

                writer.write(readerData);

                writer.newLine();
            }

            System.out.println("Lưu file độc giả thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi lưu file độc giả:  " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi xử lý dữ liệu độc giả: " + e.getMessage());
        }
    }

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
