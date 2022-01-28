package pl.kania.expensesCounter.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ParsedExpense {
    LocalDate operationDate;
    LocalDate currencyDate;
    String operationId;
    TransactionType transactionType;
    Double amount;
    Double balance;
    String description;
}
