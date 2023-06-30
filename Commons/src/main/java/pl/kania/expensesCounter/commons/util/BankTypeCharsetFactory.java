package pl.kania.expensesCounter.commons.util;

import pl.kania.expensesCounter.commons.dto.BankType;

import java.nio.charset.Charset;

public class BankTypeCharsetFactory {

    public Charset getByBankType(BankType bankType) {
        return switch (bankType) {
            case PKO_BP -> Charset.forName("Windows-1250");
            case TOYOTA_BANK -> Charset.forName("ISO-8859-2");
            default -> Charset.defaultCharset();
        };
    }
}
