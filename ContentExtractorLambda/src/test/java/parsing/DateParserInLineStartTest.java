package parsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.stream.Stream;

class DateParserInLineStartTest {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @ParameterizedTest
    @MethodSource(value = "getLineStartingWithDateAndDateToParse")
    void givenLineStartingWithDate_whenParsingDate_thenParseProperDate(String line, LocalDate expectedDate) {
        Optional<LocalDate> parsedDate = new DateParserInLineStart().find(line);
        Assertions.assertTrue(parsedDate.isPresent());
        Assertions.assertEquals(expectedDate, parsedDate.get());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "1554 ZAKUP PRZY UŻYCIU KARTY-8,242 914,76",
            "There is no date here"
    })
    @NullAndEmptySource
    void givenLineNotStartingWithDate_whenParsingDate_returnEmptyOptional(String line) {
        Optional<LocalDate> parsedDate = new DateParserInLineStart().find(line);
        Assertions.assertTrue(parsedDate.isEmpty());
    }

    private static Stream<Arguments> getLineStartingWithDateAndDateToParse() {
        return Stream.of(
                Arguments.of("23.02.2021 1554 ZAKUP PRZY UŻYCIU KARTY-8,242 914,76", LocalDate.parse("23.02.2021", DATE_FORMATTER)),
                Arguments.of("  23.02.2021 1554 ZAKUP PRZY UŻYCIU KARTY-8,242 914,76", LocalDate.parse("23.02.2021", DATE_FORMATTER))
        );
    }

}