package pl.kania.expensesCounter.grouping.purchase.processor;

import pl.kania.expensesCounter.grouping.search.ExpenseMappingsWordListReductionSearch;

public class OutgoingTransferPurchaseProcessor extends SimpleDescriptionKeywordsProcessor {

    public OutgoingTransferPurchaseProcessor() {
        super(new ExpenseMappingsWordListReductionSearch());
    }
}
