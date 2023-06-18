package pl.kania.expensesCounter.accountStatementParser.bankParser;

import org.apache.commons.csv.CSVRecord;
import pl.kania.expensesCounter.commons.dto.db.Transaction;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

public interface SingleTransactionCsvParser {

    Transaction extract(CSVRecord record);

}
