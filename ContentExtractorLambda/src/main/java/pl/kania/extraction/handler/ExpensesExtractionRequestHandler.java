package pl.kania.extraction.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.csv.ExpensesExtractorCSV;
import pl.kania.extraction.csv.ExpensesExtractorFactory;
import pl.kania.extraction.model.ParsedExpense;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class ExpensesExtractionRequestHandler implements RequestHandler<InputStream, ParsedExpense[]> {
    @Override
    public ParsedExpense[] handleRequest(InputStream json, Context context) {
        return getRequest(json)
                .map(request -> {
                    ExpensesExtractorCSV extractor = new ExpensesExtractorFactory().get(request.getBankType());

                    try (
                            InputStream textStream = new ByteArrayInputStream(request.getContent().getBytes());
                            Reader reader = new InputStreamReader(textStream)
                    ) {
                        ParsedExpense[] expenses = extractor.extract(reader);
                        Arrays.stream(expenses).forEach(e -> log.debug(e.toString()));
                        return expenses;
                    } catch (Exception e) {
                        log.error("Error processing extraction request: " + request, e);
                        return null;
                    }
                }).orElseThrow(() -> new IllegalStateException("Error extracting content"));
    }

    private Optional<ExpensesExtractionRequest> getRequest(InputStream json) {
        try {
            ExpensesExtractionRequest request = new ObjectMapper().readValue(json, ExpensesExtractionRequest.class);
            log.info("Parsed request: " + request);
            return Optional.of(request);
        } catch (IOException e) {
            log.error("Cannot read request", e);
            return Optional.empty();
        }
    }
}
