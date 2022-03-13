package pl.kania.expensesCounter.grouping.purchase.card;

import com.amazonaws.services.dynamodbv2.xspec.S;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.grouping.purchase.AbstractPurchaseProcessor;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class CardPurchaseProcessor extends AbstractPurchaseProcessor<List<String>> {


    public CardPurchaseProcessor(ExpenseMappingsSearch<List<String>> expenseMappingsSearch) {
        super(expenseMappingsSearch);
    }

    @Override
    protected Map<ParsedExpense, List<String>> transformForSearch(List<ParsedExpense> expenses) {
        // TODO

        Map<ParsedExpense, List<String>> results = new HashMap<>();
        expenses.forEach(expense -> results.put(expense, splitKeywords(expense)));
        return results;
    }

    private List<String> splitKeywords(ParsedExpense expense) {
        return asList(expense.getDescription().split(" \\/"));
    }
}
