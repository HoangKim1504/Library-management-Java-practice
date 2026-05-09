package book;

import enums.BookCategory;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private int publishYear;
    private BookCategory category;
    private double price;
    private int quantity;

    public Book() {
    }

    public Book(String isbn, String title, String author, String publisher, int publishYear, BookCategory category, double price, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.publishYear = publishYear;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public BookCategory getCategory() {
        return category;
    }

    public void setCategory(BookCategory category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Thông tin sách:" +
                " ISBN: '" + isbn + '\'' +
                ", Tên sách: '" + title + '\'' +
                ", Tác giả: '" + author + '\'' +
                ", Nhà xuất bản: '" + publisher + '\'' +
                ", Năm xuất bản: '" + publishYear + '\'' +
                ", Thể loại: '" + category + '\'' +
                ", Giá sách: '" + price + "VNĐ" + '\'' +
                ", Số lượng: '" + quantity + '\'';
    }
}
