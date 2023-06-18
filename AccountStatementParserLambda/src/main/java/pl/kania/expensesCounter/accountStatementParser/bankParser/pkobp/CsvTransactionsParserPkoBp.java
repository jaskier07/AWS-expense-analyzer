package pl.kania.expensesCounter.accountStatementParser.bankParser.pkobp;


import pl.kania.expensesCounter.accountStatementParser.bankParser.CsvTransactionsParser;

public class CsvTransactionsParserPkoBp extends CsvTransactionsParser {

    private static final char DELIMITER = ',';

    public CsvTransactionsParserPkoBp() {
        super(new SingleTransactionCsvParserPkoBp());
    }

    @Override
    protected char getDelimiter() {
        return DELIMITER;
    }

    @Override
    protected String[] getHeaderValues() {
        return CsvHeaderPkoBp.getHeaderValues();
    }
}
