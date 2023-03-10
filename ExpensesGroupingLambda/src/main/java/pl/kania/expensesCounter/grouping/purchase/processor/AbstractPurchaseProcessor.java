package pl.kania.expensesCounter.grouping.purchase.processor;

import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseError;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseCategory;
import pl.kania.expensesCounter.commons.dto.grouping.SingleExpense;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPurchaseProcessor<SEARCH_PARAM_TYPE> {

    private final ExpenseMappingsSearch<SEARCH_PARAM_TYPE> expenseMappingsSearch;

    public ExpenseGroupingResult process(List<ParsedExpense> expenses) {
        Map<ParsedExpense, SEARCH_PARAM_TYPE> searchParametersPerExpense = transformForSearch(expenses);
        log.info("Card search parameters: " + searchParametersPerExpense.toString());

        Map<String, GroupingResultPerExpenseCategory> expensesPerCategory = new HashMap<>();
        List<SingleExpense> expenseMappings = new ArrayList<>();
        List<ExpenseError> errors = new ArrayList<>();

        searchParametersPerExpense.entrySet()
                .stream()
                .map(entry -> {
                    SEARCH_PARAM_TYPE searchParamType = entry.getValue();
                    ParsedExpense expense = entry.getKey();
                    log.info(format("Searching for mappings with keywords %s for %s", searchParamType, expense));

                    Either<String, ExpenseMapping> expenseMapping = expenseMappingsSearch.search(searchParamType);
                    log.info("Found card expense mapping: " + expenseMapping);

                    return expenseMapping
                            .map(mapping -> new SingleExpense(expense.getAmount(), mapping))
                            .mapLeft(msg -> new ExpenseError(expense, msg));
                })
                .forEach(mapping -> addMappingToResults(mapping, expenseMappings, errors));

        expenseMappings.stream()
                .collect(groupingBy(mapping -> mapping.getMapping().getExpenseCategory()))
                .forEach((category, groupedExpenses) -> expensesPerCategory.put(category, new GroupingResultPerExpenseCategory(category, groupedExpenses)));

        return new ExpenseGroupingResult(expensesPerCategory, errors);
    }

    private void addMappingToResults(Either<ExpenseError, SingleExpense> mapping, List<SingleExpense> expenseMappings, List<ExpenseError> errors) {
        if (mapping.isRight()) {
            expenseMappings.add(mapping.get());
        } else {
            errors.add(mapping.getLeft());
        }
    }

    protected abstract Map<ParsedExpense, SEARCH_PARAM_TYPE> transformForSearch(List<ParsedExpense> expenses);
}
