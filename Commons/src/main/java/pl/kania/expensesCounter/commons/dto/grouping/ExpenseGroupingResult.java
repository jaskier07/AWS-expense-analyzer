package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.Builder;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;

import java.util.List;

@Value
public class ExpenseGroupingResult {
    List<GroupingResultPerExpenseType> groupings;
}
