package pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.parser;

import org.apache.commons.csv.CSVRecord;

class DescriptionParserOther implements DescriptionParser {
    @Override
    public String parse(CSVRecord record) {
        String description = record.get(CONTEXT_INDEX_TRANSACTION_TYPE);
        description += " ; " + record.get(CONTEXT_INDEX_TRANSACTION_DESCRIPTION);
        return description;
    }
}
