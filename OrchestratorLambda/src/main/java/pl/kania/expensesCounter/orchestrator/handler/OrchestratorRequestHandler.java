package pl.kania.expensesCounter.orchestrator.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.orchestrator.ContentExtractor;
import pl.kania.expensesCounter.orchestrator.CsvDownloader;
import pl.kania.expensesCounter.orchestrator.ExpensesGrouper;

import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class OrchestratorRequestHandler implements RequestHandler<Map<String, String>, String> {

    public static final String KEY_FILENAME = "filename";
    private static final CsvDownloader csvDownloader = new CsvDownloader();
    private static final ContentExtractor contentExtractor = new ContentExtractor();
    private static final ExpensesGrouper expensesGrouper = new ExpensesGrouper();

    @Override
    public String handleRequest(Map<String, String> input, Context context) {
        input.forEach((key, value) -> log.info(key + " : " + value));

        String filename = Optional.ofNullable(input.get(KEY_FILENAME))
                .orElseThrow(() -> new IllegalArgumentException("Expected key " + KEY_FILENAME + " in request input"));

        csvDownloader.download(filename)
                .map(contentExtractor::extractContent)
                .map(expensesGrouper::group);


        return "OK";
    }
}
