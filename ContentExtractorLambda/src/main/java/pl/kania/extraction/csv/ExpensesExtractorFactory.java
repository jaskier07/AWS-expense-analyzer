package pl.kania.extraction.csv;

import pl.kania.extraction.csv.pkobp.ExpensesExtractorPKOBP;
import pl.kania.extraction.model.BankType;

public class ExpensesExtractorFactory {

    public ExpensesExtractorCSV get(BankType type) {
        if (type == BankType.PKO_BP) {
            return new ExpensesExtractorPKOBP();
        } else {
            throw new IllegalArgumentException("Unknown bank type: " + type);
        }
    }
}
