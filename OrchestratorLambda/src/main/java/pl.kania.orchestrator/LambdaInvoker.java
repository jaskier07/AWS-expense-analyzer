package pl.kania.orchestrator;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Optional;

@Slf4j
public abstract class LambdaInvoker<T> {

    private static final int STATUS_OK = 200;
    private final AWSLambda lambdaClient;
    private final Base64.Decoder decoder;
    @Getter
    private final ObjectMapper objectMapper;

    public LambdaInvoker() {
        this.lambdaClient = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
        this.decoder = Base64.getDecoder();
        this.objectMapper = new ObjectMapper();
    }

    protected abstract String getLambdaName();

    protected abstract Optional<byte[]> handleInvocationError(T requestBody, Throwable throwable);

    protected abstract String getNonOkErrorMessage(T requestBody, int statusCode);

    protected abstract String getPayload(T requestBody) throws Exception;

    protected Optional<byte[]> invoke(T requestBody) {
        return Try.<Optional<byte[]>>of(() -> {
            InvokeRequest request = getInvokeRequest(requestBody);
            InvokeResult result = lambdaClient.invoke(request);

            if (result.getStatusCode().equals(STATUS_OK)) {
                byte[] payloadBase64 = result.getPayload().array();

                log.info(new String(payloadBase64));

                byte[] decodedPayload = decoder.decode(payloadBase64);
                return Optional.of(decodedPayload);
            }

            log.error(getNonOkErrorMessage(requestBody, result.getStatusCode()));
            return Optional.empty();
        }).recover(e -> {
            return handleInvocationError(requestBody, e);
        }).get();
    }

    private InvokeRequest getInvokeRequest(T requestBody) throws Exception {
        InvokeRequest request = new InvokeRequest();
        request.setFunctionName(getLambdaName());
        request.setPayload(getPayload(requestBody));
        return request;
    }

}
