package parsing;

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
        DateFinderInTextBeginning dateFinder = new DateFinderInTextBeginning();
        expensesSeparator = new ExpensesSeparator(dateFinder);
    }

    @ParameterizedTest
    @MethodSource(value = "getTestDataWithOneLine")
    void givenTextsWithOneLine_whenSeparating_thenReturnOneLineExpenses(String text, List<SeparatedExpense> expectedExpenses) {
        List<SeparatedExpense> separatedExpenses = expensesSeparator.separate(text);
        Assertions.assertEquals(expectedExpenses, separatedExpenses);
    }

    @ParameterizedTest
    @MethodSource(value = "getTestDataWithTwoLines")
    void givenTextsWithTwoLines_whenSeparating_thenReturnTwoLineExpenses(String text, List<SeparatedExpense> expectedExpenses) {
        List<SeparatedExpense> separatedExpenses = expensesSeparator.separate(text);
        Assertions.assertEquals(expectedExpenses, separatedExpenses);
    }

    @ParameterizedTest
    @MethodSource(value = "getTestDataWithThreeLines")
    void givenTextsWithThreeLines_whenSeparating_thenReturnThreeLineExpenses(String text, List<SeparatedExpense> expectedExpenses) {
        List<SeparatedExpense> separatedExpenses = expensesSeparator.separate(text);
        Assertions.assertEquals(expectedExpenses, separatedExpenses);
    }

    private static Stream<Arguments> getTestDataWithOneLine() {
        String text = "23.02.2021 TEST1 ZAKUP PRZY UŻYCIU KARTY-8,242 914,76" +
                "15.02.2021 TEST2 P£ATNOŚĆ WEB - KOD MOBILNY-15,853 508,69" +
                "18.02.2021 TEST3 PRZELEW WYCHODZ¥CY-371,373 137,32";

        List<SeparatedExpense> expenses = Arrays.stream(text.split("\n"))
                .map(SeparatedExpense::new)
                .collect(Collectors.toList());
        return Stream.of(
                Arguments.of(text, expenses)
        );
    }

    private static Stream<Arguments> getTestDataWithTwoLines() {
        String text = "18.02.2021 CHIPSY I INNE   12 2334 7868 1111 3333 4444 5555   KATARZYNA TESTOWA WS." +
                "POMORSKA 12 15-223 NOWE P£OTOWO" +
                "22.02.2021 1231233   2347238472837423   EORO NET Sp. z o.o. UL." +
                "MUSZKIETERÓW 15 02-273 POZNAN   Data dokumentu: 22.02.2021" +
                "27.02.2021 Tel.:123217387 Godz.22:05:10 Lokalizacja: https://www.oleole.pl Nr ref:" +
                "2347264236326";

        return Stream.of(
                Arguments.of(text, getParsedExpenseLines(text, 2))
        );
    }

    private static Stream<Arguments> getTestDataWithThreeLines() {
        String text = "11.02.2021 wynagrodzenie z tytulu umowy (umowa12)" +
                "124832428364234623483   TESTOS SP. Z O O. UL. TESTOWA 3 20-522" +
                "GDAŃSK   Data dokumentu: 10.02.2021" +
                "19.02.2021 Karta:1243243******1243 Godz.16:12:56 Lokalizacja: GDANSK-MURENA PL" +
                " DROWSCY Nr ref: 12423423432234" +
                " Kwota oryg.: 6,25 PLN" +
                " 24.02.2021 Karta:142343******2133 Godz.18:57:04 Lokalizacja: GDANSK PL MAX BAVIER" +
                " D.BLINSKA Nr ref: 124234234234332" +
                " Kwota oryg.: 59,11 PLN";

        return Stream.of(
                Arguments.of(text, getParsedExpenseLines(text, 3))
        );
    }

    private static List<SeparatedExpense> getParsedExpenseLines(String text, int numberOfLines) {
        List<SeparatedExpense> expenses = new ArrayList<>();
        String[] lines = text.split("\n");
        SeparatedExpense expense = null;

        for (int i = 0; i < lines.length; i++) {
            if (i % numberOfLines == 0) {
                expense = new SeparatedExpense(lines[i]);
                expenses.add(expense);
            } else {
                expense.getLines().add(lines[i]);
            }
        }
        return expenses;
    }
}