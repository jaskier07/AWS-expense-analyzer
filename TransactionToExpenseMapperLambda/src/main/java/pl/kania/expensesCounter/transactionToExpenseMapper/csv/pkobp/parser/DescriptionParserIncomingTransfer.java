package pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

class DescriptionParserIncomingTransfer implements DescriptionParser {
    @Override
    public String parse(CSVRecord record) {
        String description = record.get(CONTEXT_INDEX_LINE_0);
        description += SEPARATOR + record.get(CONTEXT_INDEX_LINE_2);
        description = removeUnnecessaryLines(description);
        return description.toLowerCase();
    }

    private String removeUnnecessaryLines(String description) {
        if (description == null) {
            return EMPTY_STRING;
        }
        description = description.replaceAll("Nazwa nadawcy: ", EMPTY_STRING);
        description = description.replaceAll("Tytu�: ", EMPTY_STRING);
        return description.trim();
    }
}
