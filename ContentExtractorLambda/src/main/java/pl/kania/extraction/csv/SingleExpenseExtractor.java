package pl.kania.extraction.csv;

import org.apache.commons.csv.CSVRecord;
import pl.kania.extraction.model.ParsedExpense;

public interface SingleExpenseExtractor {

    ParsedExpense extract(CSVRecord record);

}
