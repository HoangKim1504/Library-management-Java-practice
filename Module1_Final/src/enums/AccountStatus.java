package enums;

public enum AccountStatus {
    ACTIVATED("Hoạt động"),
    BLOCK("Khoá"),
    OTHER("Khác"),
    NONE("");

    private final String displayName;

    AccountStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
