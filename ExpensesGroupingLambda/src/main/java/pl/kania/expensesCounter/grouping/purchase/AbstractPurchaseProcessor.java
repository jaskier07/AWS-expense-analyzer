package pl.kania.expensesCounter.grouping.purchase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.grouping.model.ExpenseMapping;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractPurchaseProcessor<SEARCH_PARAM_TYPE> {

    private final ExpenseMappingsSearch<SEARCH_PARAM_TYPE> expenseMappingsSearch;

    public List<ExpenseMapping> process(List<ParsedExpense> expenses) {
        SEARCH_PARAM_TYPE searchParameters = transformForSearch(expenses);
        log.info("Card search parameters: " + searchParameters.toString());

        List<ExpenseMapping> expenseMappings = expenseMappingsSearch.search(searchParameters);
        log.info("Found card expense mappings: " + expenseMappings);

        return expenseMappings;
    }

    protected abstract SEARCH_PARAM_TYPE transformForSearch(List<ParsedExpense> expenses);
}
