package pl.kania.expensesCounter.extraction.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.kania.expensesCounter.dto.BankType;
import pl.kania.expensesCounter.dto.ParsedExpense;
import pl.kania.expensesCounter.dto.TransactionType;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

class ExpensesExtractionRequestHandlerTest {

    @Test
    void givenExampleJson_whenHandlingRequest_createsValidExpensesExtractionRequest() {
        String s = "{\n" +
                "  \"bankType\": \"PKO_BP\",\n" +
                "  \"content\": \"\\\"Data operacji\\\",\\\"Data waluty\\\",\\\"Typ transakcji\\\",\\\"Kwota\\\",\\\"Waluta\\\",\\\"Saldo po transakcji\\\",\\\"Opis transakcji\\\",\\\"\\\",\\\"\\\",\\\"\\\",\\\"\\\"\\n\\\"2021-05-19\\\",\\\"2021-05-17\\\",\\\"Płatność kartą\\\",\\\"-1.60\\\",\\\"PLN\\\",\\\"+9411.16\\\",\\\"Tytuł: 000010150 05104071137017625459610\\\",\\\"Lokalizacja: Kraj: POLSKA Miasto: Gdansk Adres: Twoja Farmacja Zylewic\\\",\\\"Oryginalna kwota operacji: 1,60 PLN\\\",\\\"Numer karty: 123456******7890\\\",\\\"\\\"\"\n" +
                "}";
        Assertions.assertDoesNotThrow(() -> new ObjectMapper().readValue(s, ExpensesExtractionRequest.class));
    }

    @Test
    void givenExampleCsvRecordWithHeader_whenHandlingRequest_returnProperParsedExpenses() {
        String csvRecord = getExampleCsvRecordWithHeader();
        ExpensesExtractionRequest request = new ExpensesExtractionRequest(BankType.PKO_BP, csvRecord);
        ExpensesExtractionRequestHandler handler = new ExpensesExtractionRequestHandler();
//        Set<ParsedExpense> parsedExpenses = pl.kania.orchestrator.handler.handleRequest(request, null);
//        Set<ParsedExpense> expectedExpenses = getExpectedExpenses();
//        Assertions.assertEquals(expectedExpenses, parsedExpenses);
    }

    private Set<ParsedExpense> getExpectedExpenses() {
        return Set.of(ParsedExpense.builder()
                .amount(-1.6)
                .balance(9411.16)
                .currencyDate(LocalDate.of(2021, Month.MAY, 17))
                .operationDate(LocalDate.of(2021, Month.MAY, 19))
                .description("Twoja Farmacja Zylewic")
                .transactionType(TransactionType.PURCHASE_CARD)
                .build());
    }

    private String getExampleCsvRecordWithHeader() {
        return "\"Data operacji\",\"Data waluty\",\"Typ transakcji\",\"Kwota\",\"Waluta\",\"Saldo po transakcji\",\"Opis transakcji\",\"\",\"\",\"\",\"\"\n"
        + "\"2021-05-19\",\"2021-05-17\",\"Płatność kartą\",\"-1.60\",\"PLN\",\"+9411.16\",\"Tytuł: 000010150 05104071137017625459610\",\"Lokalizacja: Kraj: POLSKA Miasto: Gdansk Adres: Twoja Farmacja Zylewic\",\"Oryginalna kwota operacji: 1,60 PLN\",\"Numer karty: 516931******3971\",\"\"";
    }

}