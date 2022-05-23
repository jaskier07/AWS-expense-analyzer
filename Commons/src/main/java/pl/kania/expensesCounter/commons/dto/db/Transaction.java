package pl.kania.expensesCounter.commons.dto.db;

import lombok.*;
import pl.kania.expensesCounter.commons.dto.TransactionType;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor(force = true)
public class Transaction {
    LocalDate operationDate;
    String operationId;
    TransactionType transactionType;
    Double amount;
    Double balance;
    String description;
}
