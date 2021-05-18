package pl.kania.extraction.csv.pkobp;

import pl.kania.extraction.csv.ExpensesExtractorCSV;

public class ExpensesExtractorPKOBP extends ExpensesExtractorCSV {

    private static final String[] CSV_HEADER_VALUES = {"Data operacji", "Data waluty", "Typ transakcji", "Kwota", "Waluta", "Saldo po transakcji", "Opis transakcji", "", "", "", ""};
    private static final char DELIMITER = ',';

    public ExpensesExtractorPKOBP() {
        super(new SingleExpenseExtractorPKOBP(new DescriptionParser()));
    }

    @Override
    protected char getDelimiter() {
        return DELIMITER;
    }

    @Override
    protected String[] getHeaderValues() {
        return CSV_HEADER_VALUES;
    }
}
