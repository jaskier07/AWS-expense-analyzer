package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

@Value
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
public class ExpenseError {
    ParsedExpense expense;
    String errorMessage;
}
