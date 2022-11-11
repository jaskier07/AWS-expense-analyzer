package pl.kania.expensesCounter.grouping.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.TransactionType;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;
import pl.kania.expensesCounter.commons.util.RequestHelper;
import pl.kania.expensesCounter.grouping.ParsedExpensesGrouper;
import pl.kania.expensesCounter.grouping.purchase.PurchaseProcessorFacade;

import java.util.List;
import java.util.Map;

@Slf4j
public class ExpensesGroupingRequestHandler implements RequestHandler<String, String> {

    private final ObjectMapper objectMapper = new ObjectMapperProvider().get();
    private final Base64RequestReader requestReader = new Base64RequestReader();
    private final RequestHelper requestHelper = new RequestHelper();
    private final ParsedExpensesGrouper expensesGrouper = new ParsedExpensesGrouper();
    private final PurchaseProcessorFacade purchaseProcessorFacade = new PurchaseProcessorFacade();

    @Override
    public String handleRequest(String encodedInput, Context context) {
        log.info(encodedInput);

        ContentExtractionResult parsedExpenses = readContentExtractionResult(encodedInput);
        Map<TransactionType, List<ParsedExpense>> expensesPerTransactionType = expensesGrouper.groupByExpenseTypes(parsedExpenses.getExpenses());
        log.info(expensesPerTransactionType.toString());

        ExpenseGroupingResult result = purchaseProcessorFacade.groupExpensesByExpenseCategories(expensesPerTransactionType);
        log.info(result.toString());

        return Try.of(() -> requestHelper.writeObjectAsBase64(result))
                .onFailure(e -> log.error("Error writing object as json base64 response", e))
                .getOrNull();
    }

    private ContentExtractionResult readContentExtractionResult(String encodedInput) {
        String input = requestReader.readStringBase64Encoded(encodedInput).orElseThrow();
        ContentExtractionResult parsedExpenses = Try.of(() -> objectMapper.readValue(input, ContentExtractionResult.class)).get();
        log.info(parsedExpenses.getExpenses().toString());
        return parsedExpenses;
    }
}
