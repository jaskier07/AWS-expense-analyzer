package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas.dataExtraction;

import io.vavr.control.Try;
import org.apache.commons.lang3.StringUtils;

public class BankProductAccountNumberExtractorBnpParibas {

    public static final String PERSONAL_ACCOUNT = "Konto Osobiste";

    public String extract(String contractor) {
        return Try.of(() -> contractor)
                .filter(StringUtils::isNotBlank)
                .map(text -> {
                    if (text.contains(PERSONAL_ACCOUNT)) {
                        return extractPersonalAccount(text);
                    }
                    throw new IllegalArgumentException("Unknown BNP Paribas bank product");
                })
                .get();
    }

    private String extractPersonalAccount(String text) {
        return Try.of(() -> text)
                .map(t -> t.split(PERSONAL_ACCOUNT)[1])
                .map(String::trim)
                .get();
    }
}
