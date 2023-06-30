package pl.kania.expensesCounter.accountStatementParser.bankParser.bnpParibas;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CsvHeaderBnpParibas {
    OPERATION_DATE("Data transakcji"),
    CURRENCY_DATE("Data zaksięgowania"),
    REJECTION_DATE("Data odrzucenia"),
    AMOUNT("Kwota"),
    CURRENCY("Waluta"),
    CONTRACTOR("Nadawca / odbiorca"),
    DESCRIPTION("Opis"),
    BANK_PRODUCT("Produkt"),
    TRANSACTION_TYPE("Typ transakcji"),
    ORDER_AMOUNT("Kwota zlecenia"),
    ORDER_CURRENCY("Waluta zlecenia"),
    STATUS("Status");

    public static final String EMPTY_HEADER = "";
    private final String name;

    public static String[] getHeaderValues() {
        return Arrays.stream(values())
                .map(CsvHeaderBnpParibas::getName)
                .toList()
                .toArray(new String[0]);
    }

}
