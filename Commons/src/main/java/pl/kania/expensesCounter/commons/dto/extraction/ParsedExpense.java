package pl.kania.expensesCounter.commons.dto.extraction;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.TransactionType;

import java.time.LocalDate;

@Value
@Builder
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ParsedExpense {
    LocalDate operationDate;
    LocalDate currencyDate;
    String operationId;
    TransactionType transactionType;
    Double amount;
    Double balance;
    String description;
}
