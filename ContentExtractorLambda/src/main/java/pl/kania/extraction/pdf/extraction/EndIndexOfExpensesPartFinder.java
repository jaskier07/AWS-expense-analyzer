package pl.kania.extraction.pdf.extraction;

class EndIndexOfExpensesPartFinder {

    private static final String END_OF_BALANCE_TEXT = "Saldo do przeniesienia";
    private static final String END_OF_BALANCE_TEXT_2 = "Saldo koæcowe";
    private static final int TEXT_NOT_FOUND = -1;

    int find(String pageContent) throws PageContentException {
        int endOfExpenses = pageContent.indexOf(END_OF_BALANCE_TEXT);
        if (endOfExpenses == TEXT_NOT_FOUND) {
            endOfExpenses = pageContent.indexOf(END_OF_BALANCE_TEXT_2);
        }
        if (endOfExpenses == TEXT_NOT_FOUND) {
            throw new PageContentException("Unable to find end of expenses");
        }
        return endOfExpenses;
    }
}
