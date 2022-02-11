package pl.kania.expensesCounter.orchestrator;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.dto.grouping.ExpenseGroupingResult;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
public class ExpensesGrouper extends LambdaInvoker<ContentExtractionResult> {

    public ExpenseGroupingResult group(List<ParsedExpense> parsedExpenses) {
        log.info("Grouping started...");

        return invoke(new ContentExtractionResult(parsedExpenses))
                .map(groups -> {
                    log.info(new String(groups));
                    return groups;
                })
                .map(groups -> Try.of(() -> getObjectMapper().readValue(groups, ExpenseGroupingResult.class))
                        .onFailure(e -> log.error("Error creating ExpenseGroupingResult for " + new String(groups), e))
                        .getOrNull()
                )
                .orElseThrow();
    }

    @Override
    protected String getLambdaName() {
        return "expenses-counter-expenses-grouper";
    }

    @Override
    protected Optional<byte[]> handleInvocationError(ContentExtractionResult requestBody, Throwable throwable) {
        log.error("Error invoking expenses grouper for request body " + requestBody.toString(), throwable);
        return Optional.empty();
    }

    @Override
    protected String getNonOkErrorMessage(ContentExtractionResult requestBody, int statusCode) {
        return "Problem with grouping expenses, received non-OK status: " + statusCode + " for request body " + requestBody.toString();
    }

    @Override
    protected String getPayload(ContentExtractionResult requestBody) throws Exception {
        return Optional.of(getObjectMapper().writeValueAsString(requestBody))
                .map(value -> Base64.getEncoder().encodeToString(value.getBytes()))
                .orElseThrow();
    }
}
