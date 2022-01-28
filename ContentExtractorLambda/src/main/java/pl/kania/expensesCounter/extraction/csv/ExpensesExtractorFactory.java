package pl.kania.expensesCounter.extraction.csv;

import pl.kania.expensesCounter.extraction.csv.pkobp.ExpensesExtractorPKOBP;
import pl.kania.expensesCounter.dto.BankType;

public class ExpensesExtractorFactory {

    public ExpensesExtractorCSV get(BankType type) {
        if (type == BankType.PKO_BP) {
            return new ExpensesExtractorPKOBP();
        } else {
            throw new IllegalArgumentException("Unknown bank type: " + type);
        }
    }
}
