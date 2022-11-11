package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseCategory;

import java.util.List;
import java.util.Map;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpenseGroupingResult {
    Map<ExpenseCategory, GroupingResultPerExpenseCategory> groupings;
    List<ExpenseError> errors;
}
