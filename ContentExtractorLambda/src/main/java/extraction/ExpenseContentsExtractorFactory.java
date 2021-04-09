package extraction;

public class ExpenseContentsExtractorFactory {

    public ExpenseContentsExtractor get(ExpenseContentExtractorType type) {
        if (type == ExpenseContentExtractorType.PKO_BP) {
            return getPkoBp();
        } else {
            throw new IllegalArgumentException("Unknown extractor: " + type);
        }
    }

    private ExpenseContentsExtractor getPkoBp() {
        StartIndexOfExpensesPartFinder startIndexFinder = new StartIndexOfExpensesPartFinder();
        EndIndexOfExpensesPartFinder endIndexFinder = new EndIndexOfExpensesPartFinder();
        ExpenseContentRangeFinder rangeFinder = new ExpenseContentRangeFinder(startIndexFinder, endIndexFinder);
        return new ExpenseContentsExtractorPKOBP(rangeFinder);
    }
}

