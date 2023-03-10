package pl.kania.expensesCounter.grouping.purchase.processor;

import pl.kania.expensesCounter.grouping.search.ExpenseMappingsWordListReductionSearch;

public class IncomingTransferPurchaseProcessor extends SimpleDescriptionKeywordsProcessor {

    public IncomingTransferPurchaseProcessor() {
        super(new ExpenseMappingsWordListReductionSearch());
    }
}
