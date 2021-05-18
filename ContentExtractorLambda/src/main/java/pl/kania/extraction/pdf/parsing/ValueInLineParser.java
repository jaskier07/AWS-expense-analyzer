package pl.kania.extraction.pdf.parsing;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class ValueInLineParser<T> {

    Optional<T> find(String line) {
        return Optional.ofNullable(line)
                .filter(Predicate.not(String::isBlank))
                .map(this::parseValue);
    }

    protected abstract T parseValue(String line);
}
