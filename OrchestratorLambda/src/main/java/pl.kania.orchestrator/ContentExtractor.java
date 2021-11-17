package pl.kania.orchestrator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.orchestrator.model.BankType;
import pl.kania.orchestrator.model.ExpensesExtractionRequest;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class ContentExtractor extends LambdaInvoker<ExpensesExtractionRequest> {

    public Void extractContent(byte[] file) {
        String content = new String(file);
        ExpensesExtractionRequest request = new ExpensesExtractionRequest(BankType.PKO_BP, content);
        invoke(request);
        return null;
    }

    @Override
    protected String getLambdaName() {
        return "expenses-counter-content-extractor";
    }

    @Override
    protected Optional<byte[]> handleInvocationError(ExpensesExtractionRequest requestBody, Throwable throwable) {
        log.error("Error extracting content from file. Request body: " + requestBody, throwable);
        return Optional.empty();
    }

    @Override
    protected String getNonOkErrorMessage(ExpensesExtractionRequest requestBody, int statusCode) {
        return "Problem with extracting file's content - received non-OK status code: " + statusCode + ". Request: " + requestBody;
    }

    @Override
    protected String getPayload(ExpensesExtractionRequest requestBody) throws Exception {
        return getObjectMapper().writeValueAsString(requestBody);
    }
}
