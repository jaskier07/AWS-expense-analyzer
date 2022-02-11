package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.Builder;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;

@Builder
@Value
public class GroupingResultPerExpenseType {
    ExpenseType type;
    double sum;
}
