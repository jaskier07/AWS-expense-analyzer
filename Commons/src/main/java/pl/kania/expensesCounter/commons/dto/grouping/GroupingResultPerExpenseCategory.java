package pl.kania.expensesCounter.commons.dto.grouping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.expensesCounter.commons.dto.db.ExpenseCategory;

import java.util.ArrayList;
import java.util.List;

@Builder
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class GroupingResultPerExpenseCategory {
    ExpenseCategory category;
    List<SingleExpense> expenses;

    public GroupingResultPerExpenseCategory(String expenseCategory, List<SingleExpense> expenses) {
        this.category = ExpenseCategory.valueOf(expenseCategory);
        this.expenses = new ArrayList<>(expenses);
    }

    public double sumExpenses() {
        return expenses.stream()
                .map(SingleExpense::getAmount)
                .reduce(Double::sum)
                .orElse(0.);
    }

    public void addExpenses(List<SingleExpense> newExpenses) {
        expenses.addAll(newExpenses);
    }
}
