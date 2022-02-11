package pl.kania.expensesCounter.grouping.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseType;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class ExpensesGroupingRequestHandler implements RequestHandler<String, String> {

    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();
    private final Base64RequestReader requestReader = new Base64RequestReader();

    @Override
    public String handleRequest(String encodeInput, Context context) {
        log.info(encodeInput);

        String input = requestReader.readStringBase64Encoded(encodeInput).orElseThrow();

        ParsedExpense[] parsedExpenses = Try.of(() -> objectMapper.readValue(input, ParsedExpense[].class)).get();
        log.info(Arrays.toString(parsedExpenses));


        log.info("Finished processing");
        return Optional.of(new ExpenseGroupingResult(Arrays.asList(
                GroupingResultPerExpenseType.builder()
                        .type(ExpenseType.FOOD)
                        .sum(23.)
                        .build(),
                GroupingResultPerExpenseType.builder()
                        .type(ExpenseType.FOOD)
                        .sum(25.11)
                        .build()
        ))).map(results -> Try.of(() -> objectMapper.writeValueAsString(results))
                .getOrNull()
        ).orElseThrow();
    }
}
