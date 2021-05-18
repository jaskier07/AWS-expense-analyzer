package parsing;

import model.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ExpensesSeparatorTest {

    private static ExpensesSeparator expensesSeparator;

    @BeforeAll
    static void initExpensesSeparator() {
        OperationTypeParserInLine dateFinder = new OperationTypeParserInLine();
        expensesSeparator = new ExpensesSeparator(dateFinder);
    }

    @ParameterizedTest
    @MethodSource(value = "getTestDataWithTwoLines")
    void givenTextsWithOneLine_whenSeparating_thenReturnTwoLineExpenses(String text, List<SeparatedExpense> expectedExpenses) {
        List<SeparatedExpense> separatedExpenses = expensesSeparator.separate(text);
        Assertions.assertEquals(mapToLines(expectedExpenses), mapToLines(separatedExpenses));
    }

    @ParameterizedTest
    @MethodSource(value = "getTestDataWithThreeLines")
    void givenTextsWithTwoLines_whenSeparating_thenReturnTwoLineExpenses(String text, List<SeparatedExpense> expectedExpenses) {
        List<SeparatedExpense> separatedExpenses = expensesSeparator.separate(text);
        Assertions.assertEquals(mapToLines(expectedExpenses), mapToLines(separatedExpenses));
    }

    @ParameterizedTest
    @MethodSource(value = "getTestDataWithFourLines")
    void givenTextsWithThreeLines_whenSeparating_thenReturnThreeLineExpenses(String text, List<SeparatedExpense> expectedExpenses) {
        List<SeparatedExpense> separatedExpenses = expensesSeparator.separate(text);
        Assertions.assertEquals(mapToLines(expectedExpenses), mapToLines(separatedExpenses));
    }

    private List<List<String>> mapToLines(List<SeparatedExpense> separatedExpenses) {
        return separatedExpenses.stream()
                .map(SeparatedExpense::getLines)
                .collect(Collectors.toList());
    }

    private static Stream<Arguments> getTestDataWithTwoLines() {
        String text = " 15.02.2021 12345 PŁATNOŚĆ WEB - KOD MOBILNY-15,853 508,69\n" +
                "14.02.2021 Tel.:48123456789 Godz.19:20:08 Lokalizacja: furgonetka.pl Nr ref: 12345\n" +
                "25.02.2021 12345 PŁATNOŚĆ WEB - KOD MOBILNY-77,392 812,37\n" +
                "24.02.2021 Tel.:48123456789 Godz.22:09:37 Lokalizacja: allegro.pl Nr ref: 12345\n" +
                "08.03.2021 12345 PRZELEW WYCHODZĄCY-580,00849,32\n" +
                "08.03.2021 KATALIZATOR   78 1240 3800 1111 0010 4153 4350   MARIAN KOWALSKI\n";

        return Stream.of(
                Arguments.of(text, getParsedExpenseLines(text, 2))
        );
    }

    private static Stream<Arguments> getTestDataWithThreeLines() {
        String text = "18.02.2021 12345 PRZELEW WYCHODZĄCY-371,373 137,32\n" +
                "18.02.2021 CHIPSY I INNE   72 1020 3583 0000 3902 0140 2932   KATARZYNA TESTOWA WS.\n" +
                "POMORSKA 11 11-111 NOWE PŁOTOWO\n" +
                "22.02.2021 12345 PRZELEW PRZYCHODZĄCY24,992 923,00\n" +
                "22.02.2021 12345   12345   EURO NET Sp. z o.o. UL.\n" +
                "MUSZKIETERÓW 15 02-273 WARSZAWA   Data dokumentu: 22.02.2021\n" +
                "01.03.2021 12345 PRZELEW WYCHODZĄCY-972,161 700,08\n" +
                "01.03.2021 ZA MIESZKANIE NA PŁAZIEJ DZIELNICY   12345\n" +
                "KLAUDIA TESTOWA UL.POMORSKA 1A 11-111 MOKRY BĄK\n";

        return Stream.of(
                Arguments.of(text, getParsedExpenseLines(text, 3))
        );
    }

    private static Stream<Arguments> getTestDataWithFourLines() {
        String text = "11.02.2021 12345 PRZELEW PRZYCH. SYSTEMAT. WPŁYW13 193,794 524,54\n" +
                "11.02.2021 wynagrodzenie z tytulu umowy\n" +
                "12345   FIRMA SP. Z O O. UL. FIRMOWA 1 11-111\n" +
                "GDAŃSK   Data dokumentu: 10.02.2021\n" +
                "11.02.2021 12345 ZLECENIE NABYCIA JEDNOSTEK TFI-1 000,003 524,54\n" +
                "11.02.2021 PN181IPKO PKOBP 202102110078630D ALEKSANDER KANIA 08-31429394-13A-01\n" +
                "13 1030 1508 0000 0005 0430 2059   PKO TOWARZYSTWO FUNDUSZY\n" +
                "INWESTYCYJNYCH S.A. UL. CHŁODNA 52, 00-872 WARSZAWA   Ref. wł. zlec.: 12345\n" +
                "20.02.2021 12345 ZAKUP PRZY UŻYCIU KARTY-215,362 921,96\n" +
                "19.02.2021 Karta:123456******7890 Godz.18:18:30 Lokalizacja: Gdansk PL LIDL UPHAGENA Nr\n" +
                "ref: 12345\n" +
                "Kwota oryg.: 215,36 PLN\n";

        return Stream.of(
                Arguments.of(text, getParsedExpenseLines(text, 4))
        );
    }

    private static List<SeparatedExpense> getParsedExpenseLines(String text, int numberOfLines) {
        List<SeparatedExpense> expenses = new ArrayList<>();
        String[] lines = text.split("\n");
        SeparatedExpense expense = null;

        for (int i = 0; i < lines.length; i++) {
            if (i % numberOfLines == 0) {
                expense = new SeparatedExpense(OperationType.OTHER, lines[i]);
                expenses.add(expense);
            } else {
                expense.getLines().add(lines[i]);
            }
        }
        return expenses;
    }
}