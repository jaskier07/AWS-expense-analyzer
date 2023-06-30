package pl.kania.expensesCounter.accountStatementParser.bankParser;

import io.vavr.control.Try;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.function.Function;

public interface SingleTransactionCsvParser {

    Transaction extract(CSVRecord record);

    DateTimeFormatter getDateTimeFormatter();

    default Double getDouble(CSVRecord record, String header) {
        Function<String, Double> parseToDouble = parsedText -> {
            String text = parsedText.replaceAll(",", ".")
                    .replaceAll(" ", "");
            return Double.parseDouble(text);
        };
        return parseCsvRecord(record, header, parseToDouble);
    }

    default LocalDate getDate(CSVRecord record, String header) {
        return parseCsvRecord(record, header, date -> LocalDate.parse(date, getDateTimeFormatter()));
    }

    default <T>T parseCsvRecord(CSVRecord record, String header, Function<String, T> mapper) {
        return Try.of(() -> record)
                .map(r -> r.get(header))
                .filter(Objects::nonNull)
                .map(mapper)
                .getOrNull();
    }

}
