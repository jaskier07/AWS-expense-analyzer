package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema;

import pl.kania.expensesCounter.accountStatementParser.bankParser.AccountStatementTransactionType;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import static pl.kania.expensesCounter.commons.dto.BankType.TOYOTA_BANK;

public class ToyotaBankOperationMapper {

    public Transaction mapOperationToTransaction(ToyotaBankOperation toyotaOperation) {
        TransactionType type = AccountStatementTransactionType.from(toyotaOperation.type(), TOYOTA_BANK).getTransactionType();

        return Transaction.builder()
                .id(toyotaOperation.id())
                .type(type)
                .affectedBankType(TOYOTA_BANK)
                .destAccountNumber(toyotaOperation.destAccountNumber())
                .sourceAccountNumber(toyotaOperation.sourceAccountNumber())
                .operationDate(toyotaOperation.operationDate())
                .currencyDate(toyotaOperation.currencyDate())
                .amount(toyotaOperation.amount())
                .description(toyotaOperation.description())
                .contractor(toyotaOperation.contractor())
                .expenseId(null) // will be determined in different place
                .build();
    }
}
