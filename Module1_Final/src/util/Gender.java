package util;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other"),
    NONE("none");

    private final String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
