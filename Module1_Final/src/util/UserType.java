package util;

public enum UserType {
    ADMIN("Quản trị viên"),
    MANAGER("Quản lý"),
    USER("Người dùng"),
    OTHER("Khác"),
    NONE("");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
