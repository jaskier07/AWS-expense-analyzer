package parsing;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

@Slf4j
class DateFinderInTextBeginning {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    Optional<LocalDate> find(String line) {
        return Optional.ofNullable(line)
                .filter(Predicate.not(String::isBlank))
                .map(this::parseDate);
    }

    private LocalDate parseDate(String line) {
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
