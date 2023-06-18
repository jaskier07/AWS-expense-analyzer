package pl.kania.expensesCounter.accountStatementParser.bankParser.pkobp;

import lombok.RequiredArgsConstructor;
import pl.kania.expensesCounter.accountStatementParser.bankParser.CsvTransactionsParser;
import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParser;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.io.CharArrayReader;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class TransactionParserPkoBp implements TransactionParser {

    private final CsvTransactionsParser transactionsParser;

    public TransactionParserPkoBp() {
        transactionsParser = new CsvTransactionsParserPkoBp();
    }

    @Override
    public List<Transaction> parseTransactions(String accountStatements) throws IOException {
        try (CharArrayReader reader = new CharArrayReader(accountStatements.toCharArray())) {
            return transactionsParser.parse(reader);
        }
    }
}
