package pl.kania.extraction.csv;

import pl.kania.extraction.csv.pkobp.ExpensesExtractorPKOBP;
import pl.kania.extraction.model.BankType;
import pl.kania.extraction.pdf.extraction.*;

public class ExpensesExtractorFactory {

    public ExpensesExtractorCSV get(BankType type) {
        if (type == BankType.PKO_BP) {
            return new ExpensesExtractorPKOBP();
        } else {
            throw new IllegalArgumentException("Unknown bank type: " + type);
        }
    }
}
