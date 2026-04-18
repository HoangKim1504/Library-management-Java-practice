package util;

public enum UserType {
    ADMIN("Admin"),
    MANAGER("Manager"),
    USER("User"),
    OTHER("Other"),
    NONE("None");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
