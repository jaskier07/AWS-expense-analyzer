package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;

@Builder
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GroupingResultPerExpenseType {
    ExpenseType type;
    double sum;
}
