package pl.kania.expensesCounter.commons.util;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@Slf4j
public class Base64RequestReader {

    public Optional<String> readInputStreamWithContentBase64Encoded(InputStream json) {
        return Try.of(() -> readStringBase64Encoded(new String(json.readAllBytes())))
                .onFailure(e -> log.error("Cannot read bytes from input stream", e))
                .getOrNull();
    }

    public Optional<String> readStringBase64Encoded(final String inputRequest) {
        return Try.of(() -> {
            String request = inputRequest.replaceAll("\"", "");
            log.info("Parsed request (base64): " + request);

            String decodedRequest = new String(Base64.getDecoder().decode(request.getBytes()), StandardCharsets.UTF_8);
            log.info("Parsed request (decoded): " + decodedRequest);

            return Optional.of(decodedRequest);
        }).onFailure(e -> log.error("Cannot read request: " + inputRequest, e))
                .getOrNull();
    }
}
