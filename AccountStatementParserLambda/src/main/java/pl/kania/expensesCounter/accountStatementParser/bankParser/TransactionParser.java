package pl.kania.expensesCounter.accountStatementParser.bankParser;

import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import java.util.List;

public interface TransactionParser {

    List<Transaction> parseTransactions(String accountStatements);
}
