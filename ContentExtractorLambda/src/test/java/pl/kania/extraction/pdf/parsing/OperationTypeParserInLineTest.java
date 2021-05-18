package pl.kania.extraction.pdf.parsing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pl.kania.extraction.pdf.OperationTypePDF;
import pl.kania.extraction.pdf.parsing.OperationTypeParserInLine;

import java.util.stream.Stream;

class OperationTypeParserInLineTest {

    @ParameterizedTest
    @MethodSource(value = "getLineWithSingleOperationType")
    void givenLineContainingSingleOperationType_whenParsing_thenParseProperOperationType(String line, OperationTypePDF expectedOperation) {
        OperationTypePDF parsedOperation = new OperationTypeParserInLine().parseValue(line);
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
        OperationTypePDF parsedOperation = new OperationTypeParserInLine().parseValue(line);
        Assertions.assertNull(parsedOperation);
    }

    static Stream<Arguments> getLineWithSingleOperationType() {
        return Stream.of(
                Arguments.of("23.02.2021 1231231434234 ZAKUP PRZY UŻYCIU KARTY-8,242 914,76", OperationTypePDF.PURCHASE_CARD),
                Arguments.of("22.02.2021 1231231434234 PRZELEW PRZYCHODZĄCY24,992 923,00", OperationTypePDF.INCOMING_TRANSFER),
                Arguments.of("01.03.2021 1231231434234 PRZELEW WYCHODZĄCY-972,161 700,08", OperationTypePDF.OUTGOING_TRANSFER),
                Arguments.of("01.03.2021 1231231434234 PŁATNOŚĆ WEB - KOD MOBILNY-177,101 522,98", OperationTypePDF.PURCHASE_WEB_MOBILE_CODE),
                Arguments.of("11.02.2021 1231231434234 PRZELEW PRZYCH. SYSTEMAT. WPŁYW3 193,794 524,54", OperationTypePDF.OTHER),
                Arguments.of("11.02.2021 1231231434234 ZLECENIE NABYCIA JEDNOSTEK TFI-1 000,003 524,54", OperationTypePDF.OTHER)
        );
    }

    static Stream<Arguments> getLinesWithMultipleOperationTypes() {
        return Stream.of(
                Arguments.of("23.02.2021 1231231434234 ZAKUP PRZY UŻYCIU KARTY-PRZELEW WYCHODZĄCY8,242 914,76")
        );
    }

    static Stream<Arguments> getLinesWithoutOperationType() {
        return Stream.of(
                Arguments.of("POMORSKA 12 15-223 NOWE PŁOTOWO"),
                Arguments.of("18.02.2021 CHIPSY I INNE   12 2334 7868 1111 3333 4444 5555   KATARZYNA TESTOWA WS."),
                Arguments.of("27.02.2021 Tel.:123217387 Godz.22:05:10 Lokalizacja: https://www.oleole.pl Nr ref:")
        );
    }

}