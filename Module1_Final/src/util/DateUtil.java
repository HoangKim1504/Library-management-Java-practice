package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    // ================= CONVERT DATE FRON STRING TO LOCAL DATE =================
    public static LocalDate parseLocalDate(String date, String dateFormat) {
        // Date format
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);

        // Remove spaces on the beginning and the end
        date = date.trim();

        return LocalDate.parse(date, df);
    }
}
