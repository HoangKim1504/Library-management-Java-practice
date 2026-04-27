package util;

public enum Status {
    ACTIVATED("Hoạt động"),
    BLOCK("Khoá"),
    OTHER("Khác"),
    NONE("");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
