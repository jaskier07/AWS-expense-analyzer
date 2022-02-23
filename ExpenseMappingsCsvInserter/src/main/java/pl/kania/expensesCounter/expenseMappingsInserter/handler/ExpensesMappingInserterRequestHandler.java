package pl.kania.expensesCounter.expenseMappingsInserter.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingDao;
import pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingsCsvParser;

import java.io.CharArrayReader;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
public class ExpensesMappingInserterRequestHandler implements RequestHandler<String, String> {

    private final ExpenseMappingDao expenseMappingDao = new ExpenseMappingDao();
    private final ExpenseMappingsCsvParser csvParser = new ExpenseMappingsCsvParser();

    @Override
    public String handleRequest(String input, Context context) {
        return Optional.ofNullable(input)
                .map(this::readMappings)
                .map(expenseMappingDao::saveMappings)
                .map(savedSize -> format("Saved %d mappings", savedSize))
                .orElseThrow();
    }

    private List<ExpenseMapping> readMappings(String value) {
        try (CharArrayReader reader = new CharArrayReader(value.toCharArray())) {
            List<ExpenseMapping> mappings = csvParser.parseCsv(reader);
            log.info("Mappings: {}", mappings);
            return mappings;
        }
    }
}
