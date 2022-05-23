package pl.kania.expensesCounter.commons.util;

import java.time.format.DateTimeFormatter;

public class DateFormatterProvider {

    public static DateTimeFormatter getLocalDateFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }
}
