package pl.kania.expensesCounter.grouping.purchase.processor;

import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class SimpleDescriptionKeywordsProcessor extends AbstractPurchaseProcessor<List<String>> {

    private static final String ANY_WHITESPACE = "\\s+";

    public SimpleDescriptionKeywordsProcessor(ExpenseMappingsSearch<List<String>> expenseMappingsSearch) {
        super(expenseMappingsSearch);
    }

    @Override
    protected Map<ParsedExpense, List<String>> transformForSearch(List<ParsedExpense> expenses) {
        Map<ParsedExpense, List<String>> results = new HashMap<>();
        expenses.forEach(expense -> results.put(expense, splitDescriptionKeywords(expense)));
        return results;
    }

    private List<String> splitDescriptionKeywords(ParsedExpense expense) {
        return asList(expense.getDescription().split(ANY_WHITESPACE));
    }
}
