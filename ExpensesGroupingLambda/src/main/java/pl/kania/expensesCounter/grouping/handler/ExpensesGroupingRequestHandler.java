package pl.kania.expensesCounter.grouping.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.db.ExpenseType;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.dto.grouping.GroupingResultPerExpenseType;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;
import pl.kania.expensesCounter.commons.util.RequestHelper;
import pl.kania.expensesCounter.grouping.ParsedExpensesGrouper;
import pl.kania.expensesCounter.grouping.model.ExpenseMapping;
import pl.kania.expensesCounter.grouping.purchase.PurchaseProcessorFacade;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ExpensesGroupingRequestHandler implements RequestHandler<String, String> {

    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();
    private final Base64RequestReader requestReader = new Base64RequestReader();
    private final RequestHelper requestHelper = new RequestHelper();
    private final ParsedExpensesGrouper expensesGrouper = new ParsedExpensesGrouper();
    private final PurchaseProcessorFacade purchaseProcessorFacade = new PurchaseProcessorFacade();

    @Override
    public String handleRequest(String encodeInput, Context context) {
        log.info(encodeInput);

        String input = requestReader.readStringBase64Encoded(encodeInput).orElseThrow();
        ContentExtractionResult parsedExpenses = Try.of(() -> objectMapper.readValue(input, ContentExtractionResult.class)).get();
        log.info(parsedExpenses.getExpenses().toString());

        Map<TransactionType, List<ParsedExpense>> expensesPerTransactionType = expensesGrouper.groupByExpenseTypes(parsedExpenses.getExpenses());
        log.info(expensesPerTransactionType.toString());
        List<ExpenseMapping> mappings = purchaseProcessorFacade.process(expensesPerTransactionType);
        log.info(mappings.toString());

        ExpenseGroupingResult results = getStub();

        return Try.of(() -> requestHelper.writeObjectAsBase64(results))
                .onFailure(e -> log.error("Error writing object as json base64 response", e))
                .getOrNull();
    }

    private ExpenseGroupingResult getStub() {
        return Optional.of(new ExpenseGroupingResult(Arrays.asList(
                GroupingResultPerExpenseType.builder()
                        .type(ExpenseType.FOOD)
                        .sum(23.)
                        .build(),
                GroupingResultPerExpenseType.builder()
                        .type(ExpenseType.FOOD)
                        .sum(25.11)
                        .build()
        ))).orElseThrow();
    }
}
