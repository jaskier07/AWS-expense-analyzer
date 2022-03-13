package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;

import java.util.List;

@Builder
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GroupingResultPerExpenseType {
    ExpenseType type;
    List<SingleExpense> expenses;
    double expensesSum;

    public GroupingResultPerExpenseType(String expenseType, List<SingleExpense> mappings) {
        this.type = ExpenseType.valueOf(expenseType);
        this.expenses = List.copyOf(mappings);
        this.expensesSum = sumExpenses(expenses);
    }

    private double sumExpenses(List<SingleExpense> expenseMappings) {
        return expenseMappings.stream()
                .map(SingleExpense::getAmount)
                .reduce(Double::sum)
                .orElse(0.);
    }
}
