package pl.kania.expensesCounter.transactionToExpenseMapper.csv;

import pl.kania.expensesCounter.transactionToExpenseMapper.csv.pkobp.ExpensesExtractorPKOBP;
import pl.kania.expensesCounter.commons.dto.BankType;

public class ExpensesExtractorFactory {

    public ExpensesExtractorCSV get(BankType type) {
        if (type == BankType.PKO_BP) {
            return new ExpensesExtractorPKOBP();
        } else {
            throw new IllegalArgumentException("Unknown bank type: " + type);
        }
    }
}
