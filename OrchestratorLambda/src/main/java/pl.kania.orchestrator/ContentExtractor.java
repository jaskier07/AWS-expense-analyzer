package pl.kania.orchestrator;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class ContentExtractor extends LambdaInvoker<String> {

    public Void extractContent(String content) {
        log.info("Extracting content...");

        invoke(content).ifPresent(bytes -> log.info(new String(bytes, StandardCharsets.UTF_8)));
        log.info("Extracting finished");

        return null;
    }

    @Override
    protected String getLambdaName() {
        return "expenses-counter-content-extractor";
    }

    @Override
    protected Optional<byte[]> handleInvocationError(String requestBody, Throwable throwable) {
        log.error("Error extracting content from file. Request body: " + requestBody, throwable);
        return Optional.empty();
    }

    @Override
    protected String getNonOkErrorMessage(String requestBody, int statusCode) {
        return "Problem with extracting file's content - received non-OK status code: " + statusCode + ". Request: " + requestBody;
    }

    @Override
    protected String getPayload(String requestBody) throws Exception {
        return requestBody;
    }
}
