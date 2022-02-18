package pl.kania.expensesCounter.grouping;

import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class ParsedExpensesGrouper {

    public Map<TransactionType, List<ParsedExpense>> groupByExpenseTypes(List<ParsedExpense> expenses) {
        return expenses.stream()
                .filter(expense -> expense.getTransactionType().isExpense())
                .collect(groupingBy(ParsedExpense::getTransactionType));
    }


}
