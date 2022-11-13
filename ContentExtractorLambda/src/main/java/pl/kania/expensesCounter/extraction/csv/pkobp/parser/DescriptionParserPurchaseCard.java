package pl.kania.expensesCounter.extraction.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

class DescriptionParserPurchaseCard implements DescriptionParser {

    @Override
    public String parse(CSVRecord record) {
        String description = record.get(CONTEXT_INDEX_LINE_0);
        return extractShop(description);
    }

    private String extractShop(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        String[] split = description.split("Adres:");
        if (split.length > 1) {
            return split[1].trim();
        }
        return EMPTY_STRING;
    }
}
