package pl.kania.extraction.csv;

import org.apache.commons.csv.CSVRecord;
import pl.kania.dto.ParsedExpense;

public interface SingleExpenseExtractor {

    ParsedExpense extract(CSVRecord record);

}
