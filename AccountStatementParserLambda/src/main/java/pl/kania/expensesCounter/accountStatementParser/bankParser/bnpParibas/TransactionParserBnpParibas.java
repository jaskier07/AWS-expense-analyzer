package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas;

import pl.kania.expensesCounter.accountStatementParser.bankParser.TransactionParser;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.util.List;

public class TransactionParserBnpParibas implements TransactionParser {
    @Override
    public List<Transaction> parseTransactions(String accountStatements) {
        return null;
    }
}
