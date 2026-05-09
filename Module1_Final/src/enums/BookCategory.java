package enums;

public enum BookCategory {
    SCIENCE("Khoa học"),
    NOVEL("Tiểu thuyết"),
    HISTORY("Lịch sử"),
    PROGRAMMING("Lập trình"),
    OTHER("Khác"),
    NONE("");

    private final String displayName;

    BookCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
