package util;

public class Validator {

    // Returns true if the string is null or blank (after trimming)
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    // Username: 3-20 characters, letters, numbers, underscore only
    public static boolean isValidUsername(String username) {
        if (isEmpty(username)) {
            return false;
        }
        String trimmed = username.trim();
        if (trimmed.length() < 3 || trimmed.length() > 20) {
            return false;
        }
        return trimmed.matches("[A-Za-z0-9_]+");
    }
}
