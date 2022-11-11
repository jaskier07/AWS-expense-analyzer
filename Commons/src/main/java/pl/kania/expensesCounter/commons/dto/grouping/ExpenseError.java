package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.Value;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

@Value
public class ExpenseError {
    ParsedExpense expense;
    String errorMessage;
}
