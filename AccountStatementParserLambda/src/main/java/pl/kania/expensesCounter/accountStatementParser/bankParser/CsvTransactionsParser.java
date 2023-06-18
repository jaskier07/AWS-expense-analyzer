package pl.kania.expensesCounter.accountStatementParser.bankParser;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

@Slf4j
public abstract class CsvTransactionsParser {

    private final CSVFormat csvFormat;
    private final SingleTransactionCsvParser parser;

    public CsvTransactionsParser(SingleTransactionCsvParser parser) {
        this.parser = parser;
        this.csvFormat = CSVFormat.Builder.create()
                .setHeader(getHeaderValues())
                .setDelimiter(getDelimiter())
                .setAllowMissingColumnNames(true)
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .build();
    }

    protected abstract char getDelimiter();

    protected abstract String[] getHeaderValues();

    public List<Transaction> parse(Reader reader) throws IOException {
        log.info("Extracting started...");

        return csvFormat.parse(reader)
                .getRecords()
                .stream()
                .map(parser::extract)
                .toList();
    }
}
