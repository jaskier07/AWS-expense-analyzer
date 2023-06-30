package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParser;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class TransactionParserBnpParibas implements TransactionParser {

    private final CsvTransactionsParserBnpParibas transactionsParser;

    public TransactionParserBnpParibas() {
        this.transactionsParser = new CsvTransactionsParserBnpParibas();
    }

    @Override
    public List<Transaction> parseTransactions(String accountStatements) throws IOException {
        try (CharArrayReader reader = new CharArrayReader(accountStatements.toCharArray())) {
            return transactionsParser.parse(reader);
        }
    }
}
