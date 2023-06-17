package pl.kania.expensesCounter.transactionToExpenseMapper.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public abstract class ExpensesExtractorCSV {

    private final CSVFormat csvFormat;
    private final SingleExpenseExtractor extractor;

    public ExpensesExtractorCSV(SingleExpenseExtractor extractor) {
        this.extractor = extractor;
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

    public List<ParsedExpense> extract(Reader reader) throws IOException {
        log.info("Extracting started...");

        return csvFormat.parse(reader).getRecords()
                .stream()
                .map(extractor::extract)
                .collect(Collectors.toList());
    }
}
