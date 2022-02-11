package pl.kania.expensesCounter.orchestrator;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.kania.expensesCounter.commons.util.ObjectMapperProvider;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public abstract class LambdaInvoker<T> {

    private static final int STATUS_OK = 200;
    private final AWSLambda lambdaClient;
    @Getter
    private final ObjectMapper objectMapper;

    public LambdaInvoker() {
        this.lambdaClient = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
        this.objectMapper = new ObjectMapperProvider().get();
    }

    protected abstract String getLambdaName();

    protected abstract Optional<byte[]> handleInvocationError(T requestBody, Throwable throwable);

    protected abstract String getNonOkErrorMessage(T requestBody, int statusCode);

    protected abstract String getPayload(T requestBody) throws Exception;

    protected Optional<byte[]> invoke(T requestBody) {
        return Try.<Optional<byte[]>>of(() -> {
            InvokeRequest request = getInvokeRequest(requestBody);
            InvokeResult result = lambdaClient.invoke(request);
            log.info("InvokeResult: " + result);

            if (result.getStatusCode().equals(STATUS_OK)) {
                byte[] resultBytes = result.getPayload().array();

                log.info("Returned value: " + new String(resultBytes, StandardCharsets.UTF_8));

                return Optional.of(resultBytes);
            }

            log.error(getNonOkErrorMessage(requestBody, result.getStatusCode()));
            return Optional.empty();
        }).recover(e -> handleInvocationError(requestBody, e)).get();
    }

    private InvokeRequest getInvokeRequest(T requestBody) throws Exception {
        InvokeRequest request = new InvokeRequest();
        String lambdaName = getLambdaName();
        String payload = getPayload(requestBody);

        request.setFunctionName(lambdaName);
        request.setPayload(payload);

        log.info("Invoking lambda " + lambdaName + " with payload " + payload);
        return request;
    }

}
