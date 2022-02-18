package pl.kania.expensesCounter.grouping.purchase.card;

import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.grouping.purchase.AbstractPurchaseProcessor;
import pl.kania.expensesCounter.grouping.search.ExpenseMappingsSearch;

import java.util.List;
import java.util.stream.Collectors;

public class CardPurchaseProcessor extends AbstractPurchaseProcessor<List<String>> {


    public CardPurchaseProcessor(ExpenseMappingsSearch<List<String>> expenseMappingsSearch) {
        super(expenseMappingsSearch);
    }

    @Override
    protected List<String> transformForSearch(List<ParsedExpense> expenses) {
        // TODO

        return expenses.stream()
                .map(ParsedExpense::getDescription)
                .collect(Collectors.toList());
    }
}
