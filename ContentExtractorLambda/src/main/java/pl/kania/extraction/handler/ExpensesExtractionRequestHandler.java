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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class ExpensesExtractionRequestHandler implements RequestHandler<InputStream, Set<ParsedExpense>> {
    @Override
    public Set<ParsedExpense> handleRequest(InputStream json, Context context) {
        return getRequest(json)
                .map(request -> {
                    ExpensesExtractorCSV extractor = new ExpensesExtractorFactory().get(request.getBankType());
                    try (
                            InputStream textStream = new ByteArrayInputStream(request.getContent().getBytes());
                            Reader reader = new InputStreamReader(textStream)
                    ) {
                        Set<ParsedExpense> expenses = extractor.extract(reader);
                        expenses.forEach(e -> log.debug(e.toString()));
                        return expenses;
                    } catch (Exception e) {
                        log.error("Error processing extraction request: " + request, e);
                        return new HashSet<ParsedExpense>();
                    }
                }).orElse(new HashSet<>());
    }

    private Optional<ExpensesExtractionRequest> getRequest(InputStream json) {
        try {
            return Optional.of(new ObjectMapper().readValue(json, ExpensesExtractionRequest.class));
        } catch (IOException e) {
            log.error("Cannot read request");
            return Optional.empty();
        }
    }
}
