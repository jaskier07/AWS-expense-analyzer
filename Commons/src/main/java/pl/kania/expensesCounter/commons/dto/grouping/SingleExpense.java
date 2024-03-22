package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;

@Value
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SingleExpense {
    double amount;
    ExpenseMapping mapping;

    public SingleExpense(ExpenseMapping mapping) {
        this.mapping = mapping;
        this.amount = Double.parseDouble(mapping.getExpenseCategory()); // FIXME doesn't make sense
    }
}
