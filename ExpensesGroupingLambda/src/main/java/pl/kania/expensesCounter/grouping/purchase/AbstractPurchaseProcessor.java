package pl.kania.expensesCounter.grouping.purchase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseType;
import pl.kania.expensesCounter.commons.dto.grouping.SingleExpense;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.stream.Collectors.groupingBy;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPurchaseProcessor<SEARCH_PARAM_TYPE> {

    private final ExpenseMappingsSearch<SEARCH_PARAM_TYPE> expenseMappingsSearch;

    public ExpenseGroupingResult process(List<ParsedExpense> expenses) {
        Map<ParsedExpense, SEARCH_PARAM_TYPE> searchParametersPerExpense = transformForSearch(expenses);
        log.info("Card search parameters: " + searchParametersPerExpense.toString());

        return new ExpenseGroupingResult(searchParametersPerExpense.entrySet()
                .stream()
                .map(entry -> {
                    SEARCH_PARAM_TYPE searchParamType = entry.getValue();
                    ParsedExpense expense = entry.getKey();
                    log.info(format("Searching for mappings with keywords %s for %s", searchParamType, expense));

                    ExpenseMapping expenseMapping = expenseMappingsSearch.search(searchParamType);
                    log.info("Found card expense mapping: " + expenseMapping);

                    return new SingleExpense(expense.getAmount(), expenseMapping);
                })
                .collect(null));//TODO groupingBy(SingleExpense e -> e.getMapping().getExpenseType())));
    }

    protected abstract Map<ParsedExpense, SEARCH_PARAM_TYPE> transformForSearch(List<ParsedExpense> expenses);
}
