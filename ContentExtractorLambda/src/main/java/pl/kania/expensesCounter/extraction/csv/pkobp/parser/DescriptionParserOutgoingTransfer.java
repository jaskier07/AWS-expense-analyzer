package pl.kania.expensesCounter.extraction.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

class DescriptionParserOutgoingTransfer implements DescriptionParser {
    @Override
    public String parse(CSVRecord record) {
        String description = record.get(CONTEXT_INDEX_LINE_0);
        return extractReceiverName(description);
    }

    private String extractReceiverName(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        return description.replaceAll("Nazwa odbiorcy:", EMPTY_STRING).trim();
    }
}
