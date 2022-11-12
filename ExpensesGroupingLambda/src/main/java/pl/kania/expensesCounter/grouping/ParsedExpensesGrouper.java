package pl.kania.expensesCounter.grouping;

import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class ParsedExpensesGrouper {

    public Map<TransactionType, List<ParsedExpense>> groupByExpenseTypes(List<ParsedExpense> expenses) {
        return expenses.stream()
//                .filter(expense -> expense.getTransactionType().isExpense()) // FIXME is this needed?
                .collect(groupingBy(ParsedExpense::getTransactionType));
    }


}
