package pl.kania.extraction.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.csv.ExpensesExtractorCSV;
import pl.kania.extraction.csv.ExpensesExtractorFactory;
import pl.kania.dto.BankType;
import pl.kania.dto.ParsedExpense;
import pl.kania.extraction.util.ObjectMapperProvider;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ExpensesExtractionRequestHandler implements RequestHandler<String, String> {

    private final ObjectMapper objectMapperProvider = new ObjectMapperProvider().get();

    @Override
    public String handleRequest(String json, Context context) {
        return getRequest(new ByteArrayInputStream(json.getBytes()))
                .map(request -> {
                    ExpensesExtractorCSV extractor = new ExpensesExtractorFactory().get(BankType.PKO_BP);

                    try (
                        InputStream textStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
                        Reader reader = new InputStreamReader(textStream)
                    ) {
                        ParsedExpense[] expenses = extractor.extract(reader);
                        Arrays.stream(expenses).forEach(e -> log.info(e.toString()));
                        return objectMapperProvider.writeValueAsString(expenses);
                    } catch (Exception e) {
                        log.error("Error processing extraction request: " + request, e);
                        return null;
                    }
                }).orElseThrow(() -> new IllegalStateException("Error extracting content"));
    }

    private Optional<String> getRequest(InputStream json) {
        try {
            String request = new String(json.readAllBytes());
            request = request.replaceAll("\"", "");
            log.info("Parsed request (base64): " + request);

            String encodedRequest = new String(Base64.getDecoder().decode(request.getBytes()), StandardCharsets.UTF_8);
            log.info("Parsed request (encoded): " + encodedRequest);

            return Optional.of(encodedRequest);
        } catch (IOException e) {
            log.error("Cannot read request: " + json, e);
            return Optional.empty();
        }
    }
}
