package pl.kania.expensesCounter.commons.dto.db;

import lombok.*;
import pl.kania.expensesCounter.commons.dto.TransactionType;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
public class Transaction {
    String operationId;
    LocalDate date;
    TransactionType type;
    Double amount;
    String description;
}
