package pl.kania.expensesCounter.grouping.purchase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPurchaseProcessor<SEARCH_PARAM_TYPE> {

    private final ExpenseMappingsSearch<SEARCH_PARAM_TYPE> expenseMappingsSearch;

    public List<ExpenseMapping> process(List<ParsedExpense> expenses) {
        Map<ParsedExpense, SEARCH_PARAM_TYPE> searchParametersPerExpense = transformForSearch(expenses);
        log.info("Card search parameters: " + searchParametersPerExpense.toString());

        return searchParametersPerExpense.entrySet()
                .stream()
                .map(entry -> {
                    log.info(format("Searching for mappings with keywords %s for %s", entry.getValue(), entry.getKey()));

                    ExpenseMapping expenseMapping = expenseMappingsSearch.search(entry.getValue());
                    log.info("Found card expense mapping: " + expenseMapping);

                    return expenseMapping;
                })
                .collect(Collectors.toList());
    }

    protected abstract Map<ParsedExpense, SEARCH_PARAM_TYPE> transformForSearch(List<ParsedExpense> expenses);
}
