package util;

public enum Status {
    ACTIVATED("Activated"),
    BLOCK("Block"),
    OTHER("Other"),
    NONE("None");

    private final String displayName;

    Status(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
