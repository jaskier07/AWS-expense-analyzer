package pl.kania.extraction.pdf.extraction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class StartIndexOfExpensesPartFinder {

    private static final String HEADER_FIRST_LINE = "Data operacjiIdentyfikator operacjiTYP OPERACJIKwota operacjiSaldo\n";
    private static final String HEADER_SECOND_LINE = "Data walutyOpis operacji\n";

    int find(String pageContent) throws PageContentException {
        int indexOfFirstHeader = pageContent.indexOf(HEADER_FIRST_LINE);
        int indexOfSecondHeader = pageContent.indexOf(HEADER_SECOND_LINE);

        if (!bothHeadersFoundInSequence(indexOfFirstHeader, indexOfSecondHeader)) {
            String message = String.format("Unable to find both headers in a sequence. Indexes: (%d - %d)", indexOfFirstHeader, indexOfSecondHeader);
            throw new PageContentException(message);
        }

        return getIndexOfEndOfSentence(indexOfSecondHeader, HEADER_SECOND_LINE);
    }

    private boolean bothHeadersFoundInSequence(int indexOfFirstHeader, int indexOfSecondHeader) {
        return indexOfSecondHeader - getIndexOfEndOfSentence(indexOfFirstHeader, HEADER_FIRST_LINE) <= 1;
    }

    private int getIndexOfEndOfSentence(int index, String sentence) {
        return index + sentence.length();
    }

}
