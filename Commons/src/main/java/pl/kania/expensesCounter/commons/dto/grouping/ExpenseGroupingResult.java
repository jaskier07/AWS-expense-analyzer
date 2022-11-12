package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpenseGroupingResult {
    Map<String, GroupingResultPerExpenseCategory> groupings;
    List<ExpenseError> errors;
}
