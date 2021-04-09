package extraction;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ExpenseContentRangeFinder {

    private final StartIndexOfExpensesPartFinder startIndexFinder;
    private final EndIndexOfExpensesPartFinder endIndexFinder;

    ExpenseContentRange findRangeInPageContent(String pageContent) throws PageContentException {
        int indexFrom = startIndexFinder.find(pageContent);
        int indexTo = endIndexFinder.find(pageContent);

        if (indexFrom > indexTo) {
            String message = String.format("Bad indexes (%d - %d). Start index cannot be after end index", indexFrom, indexTo);
            throw new PageContentException(message);
        }

        return new ExpenseContentRange(indexFrom, indexTo);
    }
}
