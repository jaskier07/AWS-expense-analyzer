package pl.kania.expensesCounter.extraction.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import pl.kania.expensesCounter.dto.ParsedExpense;

import java.io.IOException;
import java.io.Reader;

@Slf4j
public abstract class ExpensesExtractorCSV {

    private final CSVFormat csvFormat;
    private final SingleExpenseExtractor extractor;

    public ExpensesExtractorCSV(SingleExpenseExtractor extractor) {
        this.extractor = extractor;
        this.csvFormat = CSVFormat.DEFAULT
                .withHeader(getHeaderValues())
                .withDelimiter(getDelimiter())
                .withFirstRecordAsHeader()
                .withAllowMissingColumnNames();
    }

    protected abstract char getDelimiter();

    protected abstract String[] getHeaderValues();

    public ParsedExpense[] extract(Reader reader) throws IOException {
        log.info("Extracting started...");

        return csvFormat.parse(reader).getRecords()
                .stream()
                .map(extractor::extract)
                .toArray(ParsedExpense[]::new);
    }
}