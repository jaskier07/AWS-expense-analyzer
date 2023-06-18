package pl.kania.expensesCounter.accountStatementParser.bankParser;

import pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.TransactionParserBnpParibas;
import pl.kania.expensesCounter.accountStatementParser.bankParser.pkobp.TransactionParserPkoBp;
import pl.kania.expensesCounter.accountStatementParser.bankParser.toyotaBank.TransactionParserToyotaBank;
import pl.kania.expensesCounter.commons.dto.BankType;

public class TransactionParserFactory {

    public TransactionParser get(BankType bankType) throws Exception {
        return switch (bankType) {
            case PKO_BP -> new TransactionParserPkoBp();
            case BNP_PARIBAS -> new TransactionParserBnpParibas();
            case TOYOTA_BANK -> new TransactionParserToyotaBank();
        };
    }

}
