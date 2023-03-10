package pl.kania.expensesCounter.grouping.purchase.processor;

import pl.kania.expensesCounter.grouping.search.ExpenseMappingsWordListReductionSearch;

public class WebMobileCodePurchaseProcessor extends SimpleDescriptionKeywordsProcessor {

    public WebMobileCodePurchaseProcessor() {
        super(new ExpenseMappingsWordListReductionSearch());
    }
}
