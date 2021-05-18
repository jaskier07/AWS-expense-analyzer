package model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class ParsedExpense {
    LocalDate operationDate;
    LocalDate currencyDate;
    String operationId;
    OperationType operationType;
    Double amount;
    Double balance;
    String description;
}
