package pl.kania.orchestrator;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.util.IOUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class CsvDownloader {

    private static final String LAMBDA_NAME = "expenses-counter-csv-downloader";
    private static final int STATUS_OK = 200;
    private final AWSLambda lambdaClient;
    private final ObjectMapper objectMapper;

    public CsvDownloader() {
        this.lambdaClient = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public Optional<byte[]> download(String filename) {
        return Try.<Optional<byte[]>>of(() -> {
            InvokeRequest request = getInvokeRequest(filename);
            InvokeResult result = lambdaClient.invoke(request);

            if (result.getStatusCode().equals(STATUS_OK)) {
                byte[] payload = result.getPayload().array();
                return Optional.of(payload);
            }
            log.error("Download result is not OK: " + result.getStatusCode());
            return Optional.empty();
        }).recover(e -> {
            log.error("Error downloading file " + filename, e);
            return Optional.empty();
        }).get();
    }

    private InvokeRequest getInvokeRequest(String filename) throws JsonProcessingException {
        InvokeRequest request = new InvokeRequest();
        request.setFunctionName(LAMBDA_NAME);
        request.setPayload(getPayload(filename));
        return request;
    }

    private String getPayload(String filename) throws JsonProcessingException {
        Map<String, String> map = Map.of(OrchestratorRequestHandler.KEY_FILENAME, filename);
        return objectMapper.writeValueAsString(map);
    }
}
