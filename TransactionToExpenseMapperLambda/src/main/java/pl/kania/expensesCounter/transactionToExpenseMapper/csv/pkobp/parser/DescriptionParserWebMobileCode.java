package pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

class DescriptionParserWebMobileCode implements DescriptionParser {

    public String parse(CSVRecord record) {
        String description = record.get(CONTEXT_INDEX_LINE_1);
        return extractShopUrl(description);
    }

    private String extractShopUrl(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        description = description.replaceAll("Lokalizacja: Adres:", EMPTY_STRING);
        description = description.replaceAll("(http://)|(https://)|(www.)", EMPTY_STRING);
        return description.trim();
    }
}
