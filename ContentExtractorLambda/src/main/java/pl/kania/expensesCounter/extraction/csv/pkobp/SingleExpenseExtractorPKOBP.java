package pl.kania.expensesCounter.extraction.csv.pkobp;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.extraction.csv.SingleExpenseExtractor;
import pl.kania.expensesCounter.extraction.csv.pkobp.parser.DescriptionParserFacade;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class SingleExpenseExtractorPKOBP implements SingleExpenseExtractor {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DescriptionParserFacade descriptionParser;

    public SingleExpenseExtractorPKOBP(DescriptionParserFacade descriptionParser) {
        this.descriptionParser = descriptionParser;
    }

    @Override
    public ParsedExpense extract(CSVRecord record) {
        log.info("Extracting record: " + record);

        TransactionType transactionType = TransactionTypeCSV_PKOBP.from(record.get("Typ transakcji")).getTransactionType();
        return ParsedExpense.builder()
                .operationDate(getDate(record, "Data operacji"))
                .currencyDate(getDate(record, "Data waluty"))
                .transactionType(transactionType)
                .amount(getDouble(record, "Kwota"))
                .balance(getDouble(record, "Saldo po transakcji"))
                .description(descriptionParser.parse(record, transactionType))
                .build();
    }

    private Double getDouble(CSVRecord record, String header) {
        String value = record.get(header);
        if (value != null) {
            return Double.parseDouble(value);
        }
        return null;
    }

    private LocalDate getDate(CSVRecord record, String header) {
        String value = record.get(header);
        if (value != null) {
            return LocalDate.parse(value, DATE_FORMATTER);
        }
        return null;
    }
}
