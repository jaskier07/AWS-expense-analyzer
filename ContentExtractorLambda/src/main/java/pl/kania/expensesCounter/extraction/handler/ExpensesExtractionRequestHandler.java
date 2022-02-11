package pl.kania.expensesCounter.extraction.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.commons.util.RequestHelper;
import pl.kania.expensesCounter.extraction.csv.ExpensesExtractorCSV;
import pl.kania.expensesCounter.extraction.csv.ExpensesExtractorFactory;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;
import pl.kania.expensesCounter.commons.dto.BankType;
import pl.kania.expensesCounter.commons.dto.extraction.ParsedExpense;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class ExpensesExtractionRequestHandler implements RequestHandler<String, String> {

    private final RequestHelper requestHelper = new RequestHelper();
    private final Base64RequestReader requestReader = new Base64RequestReader();

    @Override
    public String handleRequest(String json, Context context) {
        return requestReader.readInputStreamWithContentBase64Encoded(new ByteArrayInputStream(json.getBytes()))
                .map(request -> {
                    ExpensesExtractorCSV extractor = new ExpensesExtractorFactory().get(BankType.PKO_BP);

                    try (
                        InputStream textStream = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
                        Reader reader = new InputStreamReader(textStream)
                    ) {
                        List<ParsedExpense> expenses = extractor.extract(reader);
                        expenses.forEach(e -> log.info(e.toString()));
                        ContentExtractionResult result = new ContentExtractionResult(expenses);
                        return requestHelper.writeObjectAsBase64(result);
                    } catch (Exception e) {
                        log.error("Error processing extraction request: " + request, e);
                        return null;
                    }
                }).orElseThrow(() -> new IllegalStateException("Error extracting content"));
    }
}
