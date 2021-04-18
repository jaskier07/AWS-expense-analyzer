package parsing;

import model.BankType;

public class ExpensesParserFactory {

    public ExpensesParser get(BankType bank) {
        if (bank == BankType.PKO_BP) {
            OperationTypeParserInLine operationParser = new OperationTypeParserInLine();
            ExpensesSeparator expensesSeparator = new ExpensesSeparator(operationParser);
            return new ExpensesParserPKOBP(expensesSeparator);
        }
        throw new IllegalArgumentException("Unknown bank type: " + bank);
    }
}
