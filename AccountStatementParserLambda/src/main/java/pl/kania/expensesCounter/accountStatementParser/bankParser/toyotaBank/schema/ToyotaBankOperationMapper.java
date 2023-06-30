package pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.schema;

import pl.kania.expensesCounter.accountStatementParser.bankParser.AccountStatementTransactionType;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.Transaction;

import static pl.kania.expensesCounter.commons.dto.BankType.TOYOTA_BANK;

public class ToyotaBankOperationMapper {

    public Transaction mapOperationToTransaction(ToyotaBankOperation toyotaOperation) {
        TransactionType type = AccountStatementTransactionType.from(toyotaOperation.getType(), TOYOTA_BANK).getTransactionType();

        return Transaction.builder()
                .id(toyotaOperation.getId())
                .type(type)
                .affectedBankType(TOYOTA_BANK)
                .destAccountNumber(toyotaOperation.getDestAccountNumber())
                .sourceAccountNumber(toyotaOperation.getSourceAccountNumber())
                .operationDate(toyotaOperation.getOperationDate())
                .currencyDate(toyotaOperation.getCurrencyDate())
                .amount(toyotaOperation.getAmount())
                .description(toyotaOperation.getDescription())
                .contractor(toyotaOperation.getContractor())
                .expenseId(null) // will be determined in different place
                .build();
    }
}
