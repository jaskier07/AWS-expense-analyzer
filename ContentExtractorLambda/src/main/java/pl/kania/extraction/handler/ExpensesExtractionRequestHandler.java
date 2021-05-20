package pl.kania.extraction.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import pl.kania.extraction.csv.ExpensesExtractorCSV;
import pl.kania.extraction.csv.ExpensesExtractorFactory;
import pl.kania.extraction.model.ParsedExpense;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Set;

@Slf4j
public class ExpensesExtractionRequestHandler implements RequestHandler<ExpensesExtractionRequest, Set<ParsedExpense>> {
    @Override
    public Set<ParsedExpense> handleRequest(ExpensesExtractionRequest request, Context context) {

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
            return null;
        }
    }
}
