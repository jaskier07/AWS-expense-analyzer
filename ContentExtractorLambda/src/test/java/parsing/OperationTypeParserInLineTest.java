package parsing;

import lombok.Value;
import model.OperationType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class OperationTypeParserInLineTest {

    @ParameterizedTest
    @MethodSource(value = "getLineWithSingleOperationType")
    void givenLineContainingSingleOperationType_whenParsing_thenParseProperOperationType(String line, OperationType expectedOperation) {
        OperationType parsedOperation = new OperationTypeParserInLine().parseValue(line);
        Assertions.assertEquals(expectedOperation, parsedOperation);
    }

    @ParameterizedTest
    @MethodSource(value = "getLinesWithMultipleOperationTypes")
    void givenLineContainingMultipleOperationTypes_whenParsing_throwIllegalStateException(String line) {
        Assertions.assertThrows(IllegalStateException.class, () -> new OperationTypeParserInLine().parseValue(line));
    }

    @ParameterizedTest
    @MethodSource(value = "getLinesWithoutOperationType")
    void givenLineNotContainingAnyOperationType_whenParsing_returnNull(String line) {
        OperationType parsedOperation = new OperationTypeParserInLine().parseValue(line);
        Assertions.assertNull(parsedOperation);
    }

    static Stream<Arguments> getLineWithSingleOperationType() {
        return Stream.of(
                Arguments.of("23.02.2021 1231231434234 ZAKUP PRZY UŻYCIU KARTY-8,242 914,76", OperationType.PURCHASE_CARD),
                Arguments.of("22.02.2021 1231231434234 PRZELEW PRZYCHODZ¥CY24,992 923,00", OperationType.INCOMING_TRANSFER),
                Arguments.of("01.03.2021 1231231434234 PRZELEW WYCHODZ¥CY-972,161 700,08", OperationType.OUTGOING_TRANSFER),
                Arguments.of("01.03.2021 1231231434234 P£ATNOŚĆ WEB - KOD MOBILNY-177,101 522,98", OperationType.PURCHASE_WEB_MOBILE_CODE),
                Arguments.of("11.02.2021 1231231434234 PRZELEW PRZYCH. SYSTEMAT. WP£YW3 193,794 524,54", OperationType.OTHER),
                Arguments.of("11.02.2021 1231231434234 ZLECENIE NABYCIA JEDNOSTEK TFI-1 000,003 524,54", OperationType.OTHER)
        );
    }

    static Stream<Arguments> getLinesWithMultipleOperationTypes() {
        return Stream.of(
                Arguments.of("23.02.2021 1231231434234 ZAKUP PRZY UŻYCIU KARTY-PRZELEW WYCHODZ¥CY8,242 914,76")
        );
    }

    static Stream<Arguments> getLinesWithoutOperationType() {
        return Stream.of(
                Arguments.of("POMORSKA 12 15-223 NOWE P£OTOWO"),
                Arguments.of("18.02.2021 CHIPSY I INNE   12 2334 7868 1111 3333 4444 5555   KATARZYNA TESTOWA WS."),
                Arguments.of("27.02.2021 Tel.:123217387 Godz.22:05:10 Lokalizacja: https://www.oleole.pl Nr ref:")
        );
    }

}