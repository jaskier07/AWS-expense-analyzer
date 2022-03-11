package pl.kania.expensesCounter.expenseMappingsInserter.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.BOMInputStream;
import pl.kania.expensesCounter.commons.dto.db.ExpenseMapping;
import pl.kania.expensesCounter.commons.util.Base64RequestReader;
import pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingDao;
import pl.kania.expensesCounter.expenseMappingsInserter.ExpenseMappingsCsvParser;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@AllArgsConstructor
public class ExpensesMappingInserterRequestHandler implements RequestHandler<String, String> {

    private static final String BASE_64_PREFIX = "base64;";
    private final Base64RequestReader requestReader;
    private final ExpenseMappingDao expenseMappingDao;
    private final ExpenseMappingsCsvParser csvParser;

    public ExpensesMappingInserterRequestHandler() {
        this.requestReader  = new Base64RequestReader();
        this.expenseMappingDao = new ExpenseMappingDao();
        this.csvParser  = new ExpenseMappingsCsvParser();
    }

    @Override
    public String handleRequest(String input, Context context) {
        log.info(input);

        return Optional.ofNullable(input)
                .map(this::decodeIfNecessary)
                .map(this::readMappings)
                .map(expenseMappingDao::saveMappings)
                .map(savedSize -> format("Saved %d mappings", savedSize))
                .orElseThrow();
    }

    private String decodeIfNecessary(String value) {
        if (value.startsWith(BASE_64_PREFIX)) {
            value = value.substring(BASE_64_PREFIX.length());
            value = requestReader.readStringBase64Encoded(value).orElseThrow();
        }
        log.info(value);
        return value;
    }

    private List<ExpenseMapping> readMappings(String value) {
        try (CharArrayReader reader = new CharArrayReader(value.toCharArray())) {
            return csvParser.parseCsv(reader);
        }
    }
}
