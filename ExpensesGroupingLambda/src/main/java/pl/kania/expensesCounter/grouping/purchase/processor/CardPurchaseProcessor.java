package pl.kania.expensesCounter.grouping.purchase.processor;

import pl.kania.expensesCounter.grouping.search.ExpenseMappingsWordListReductionSearch;

public class CardPurchaseProcessor extends SimpleDescriptionKeywordsProcessor {

    public CardPurchaseProcessor() {
        super(new ExpenseMappingsWordListReductionSearch());
    }
}
