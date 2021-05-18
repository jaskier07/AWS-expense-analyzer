package pl.kania.extraction.pdf.parsing;

import pl.kania.extraction.model.BankType;

public class ExpensesParserFactory {

    public ExpensesParser get(BankType bank) {
        if (bank == BankType.PKO_BP) {
            OperationTypeParserInLine operationParser = new OperationTypeParserInLine();
            ExpensesSeparator expensesSeparator = new ExpensesSeparator(operationParser);
            OutgoingTransferExpenseParser outgoingTransferParser = new OutgoingTransferExpenseParser();
            MobileCodeExpenseParser mobileCodeParser = new MobileCodeExpenseParser();
            CardPurchaseExpenseParser cardParser = new CardPurchaseExpenseParser();

            return new ExpensesParserPKOBP(expensesSeparator, outgoingTransferParser, mobileCodeParser, cardParser);
        }
        throw new IllegalArgumentException("Unknown bank type: " + bank);
    }
}
