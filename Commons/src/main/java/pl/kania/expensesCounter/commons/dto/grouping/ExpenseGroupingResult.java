package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;

import java.util.List;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpenseGroupingResult {
    List<GroupingResultPerExpenseType> groupings;
}
