package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas;

import pl.kania.expensesCounter.accountStatementParser.bankParser.CsvTransactionsParser;

public class CsvTransactionsParserBnpParibas extends CsvTransactionsParser {

    public CsvTransactionsParserBnpParibas() {
        super(new SingleTransactionCsvParserBnpParibas());
    }

    @Override
    protected char getDelimiter() {
        return ';';
    }

    @Override
    protected String[] getHeaderValues() {
        return CsvHeaderBnpParibas.getHeaderValues();
    }
}
