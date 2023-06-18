package pl.kania.expensesCounter.accountStatementParser.bankParser.pkobp;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Getter
@RequiredArgsConstructor
public enum CsvHeaderPkoBp {
    OPERATION_DATE("Data operacji"),
    CURRENCY_DATE("Data waluty"),
    TRANSACTION_TYPE("Typ transakcji"),
    AMOUNT("Kwota"),
    CURRENCY("Waluta"),
    BALANCE_AFTER_TRANSACTION("Saldo po transakcji"),
    DESCRIPTION("Opis transakcji");

    public static final String EMPTY_HEADER = "";
    private final String name;

    public static String[] getHeaderValues() {
        List<String> values = Arrays.stream(values())
                .map(CsvHeaderPkoBp::getName)
                .collect(toList());
        IntStream.range(0, 4)
                .forEach(i -> values.add(EMPTY_HEADER));
        return values.toArray(new String[0]);
    }
}
