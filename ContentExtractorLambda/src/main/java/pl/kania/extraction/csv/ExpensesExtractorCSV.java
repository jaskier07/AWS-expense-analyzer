package pl.kania.extraction.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import pl.kania.extraction.model.ParsedExpense;

import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public Set<ParsedExpense> extract(Reader reader) throws IOException {
        List<CSVRecord> records = csvFormat.parse(reader).getRecords();
        return records.stream()
                .map(extractor::extract)
                .collect(Collectors.toSet());
    }
}
