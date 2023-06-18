package pl.kania.expensesCounter.commons.dto.db;

import lombok.*;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.TransactionType;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class Transaction {
    String id;
    LocalDate operationDate;
    LocalDate currencyDate;
    TransactionType type;
    Double amount;
    String description;
    String contractor;
    BankType affectedBankType;
    String sourceAccountNumber;
    String destAccountNumber;
    String expenseId;
}
