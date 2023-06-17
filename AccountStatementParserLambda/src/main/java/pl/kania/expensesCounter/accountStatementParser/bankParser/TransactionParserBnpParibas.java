package pl.kania.expensesCounter.accountStatementParser.bankParser;

import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.util.List;

class TransactionParserBnpParibas implements TransactionParser {
    @Override
    public List<Transaction> parseTransactions(String accountStatements) {
        return null;
    }
}
