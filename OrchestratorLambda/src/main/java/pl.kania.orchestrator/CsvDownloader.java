package pl.kania.orchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.kania.orchestrator.handler.OrchestratorRequestHandler;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CsvDownloader extends LambdaInvoker<String> {

    private static final String LAMBDA_NAME = "expenses-counter-csv-downloader";

    @Override
    protected String getLambdaName() {
        return LAMBDA_NAME;
    }

    @Override
    protected Optional<byte[]> handleInvocationError(String filename, Throwable throwable) {
        log.error("Error downloading file " + filename, throwable);
        return Optional.empty();
    }

    @Override
    protected String getNonOkErrorMessage(String filename, int statusCode) {
        return "Problem with downloading a file " + filename + ": received non-OK status: " + statusCode;
    }

    @Override
    protected String getPayload(String filename) throws JsonProcessingException {
        Map<String, String> map = Map.of(OrchestratorRequestHandler.KEY_FILENAME, filename);
        return getObjectMapper().writeValueAsString(map);
    }

    public Optional<String> download(String filename) {
        return invoke(filename).map(bytes -> {
            String content = new String(bytes);
            log.info("Downloaded file: " + content);
            return content;
        });
    }
}
