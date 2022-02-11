package pl.kania.expensesCounter.orchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class ContentExtractor extends LambdaInvoker<String> {

    public static final List<ParsedExpense> EMPTY_RESULT = Collections.emptyList();
    private final ObjectMapper objectMapper;
    private final Base64RequestReader requestReader;

    public ContentExtractor() {
        this.objectMapper = new ObjectMapperProvider().get();
        this.requestReader = new Base64RequestReader();
    }

    public List<ParsedExpense> extractContent(String content) {
        log.info("Extracting content...");
        log.info(content);
        return invoke(content)
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .map(str -> requestReader.readStringBase64Encoded(str)
                        .orElseThrow())
                .map(text -> Try.of(() -> objectMapper.readValue(text, ContentExtractionResult.class))
                        .recover(this::handleValueReadingException)
                        .map(ContentExtractionResult::getExpenses)
                        .getOrElse(EMPTY_RESULT)
                ).orElse(EMPTY_RESULT);
    }

    public static void main(String[] args) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapperProvider().get();
        System.out.println(objectMapper.readValue(
                "{\n    \"expenses\": [\n        {\n            \"operationDate\": [\n                2021,\n                11,\n                10\n            ],\n            \"currencyDate\": [\n                2021,\n                11,\n                8\n            ],\n            \"operationId\": null,\n            \"transactionType\": \"PURCHASE_CARD\",\n            \"amount\": -15.21,\n            \"balance\": 162486.12,\n            \"description\": \"JMP S.A. BIEDRONKA 420\"\n        },\n        {\n            \"operationDate\": [\n                2021,\n                11,\n                10\n            ],\n            \"currencyDate\": [\n                2021,\n                11,\n                8\n            ],\n            \"operationId\": null,\n            \"transactionType\": \"PURCHASE_CARD\",\n            \"amount\": -15.2,\n            \"balance\": 162501.33,\n            \"description\": \"KONKOL SKLEP 236\"\n        },\n        {\n            \"operationDate\": [\n                2021,\n                11,\n                10\n            ],\n            \"currencyDate\": [\n                2021,\n                11,\n                10\n            ],\n            \"operationId\": null,\n            \"transactionType\": \"INCOMING_TRANSFER\",\n            \"amount\": 13060.53,\n            \"balance\": 162516.53,\n            \"description\": \"IHS GLOBAL SP. Z O.O. UL. MARYNARKI POLSKIEJ 163 ; Tytu³: ¥êŒæ¿Ÿó³ WYNAGRODZENIE ZA PAZDZIERNIK 2021\"\n        }\n    ]\n}"
                , ContentExtractionResult.class));
    }

    private ContentExtractionResult handleValueReadingException(Throwable exception) {
        log.error("Error reading result of ContentExtractor invocation", exception);
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
