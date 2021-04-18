package parsing;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
class DateParserInLineStart extends ValueInLineParser<LocalDate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    protected LocalDate parseValue(String line) {
        line = line.trim();
        String firstPhrase = line.split(" ")[0];

        try {
            return LocalDate.parse(firstPhrase, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            log.debug("Cannot parse date {}", firstPhrase);
            return null;
        }
    }

}
