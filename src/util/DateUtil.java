package util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtil {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static Date parseDate(String dateText) {
        if (dateText == null || dateText.trim().isEmpty()) {
            return null;
        }
        try {
            FORMAT.setLenient(false);
            java.util.Date parsed = FORMAT.parse(dateText.trim());
            return new Date(parsed.getTime());
        } catch (ParseException e) {
            return null;
        }
    }
}