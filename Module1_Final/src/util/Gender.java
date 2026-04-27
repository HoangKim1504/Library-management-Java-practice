package util;

public enum Gender {
    MALE("Nam"),
    FEMALE("Nữ"),
    OTHER("Khác"),
    NONE("");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
