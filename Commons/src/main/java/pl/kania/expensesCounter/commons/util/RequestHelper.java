package pl.kania.expensesCounter.commons.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import pl.kania.expensesCounter.commons.dto.extraction.ContentExtractionResult;

import java.util.Base64;
import java.util.Map;

@RequiredArgsConstructor
public class RequestHelper {

    private final ObjectMapper objectMapper;
    private final Base64.Encoder encoder;

    public RequestHelper() {
        this.objectMapper = new ObjectMapperProvider().get();
        this.encoder = Base64.getEncoder();
    }

    public <T>String writeObjectAsBase64(T object) throws JsonProcessingException {
        var expensesString= objectMapper.writeValueAsString(object);
        return encoder.encodeToString(expensesString.getBytes());
    }

    public String mapInputJsonToString(Object input) {
        if (input instanceof String) {
            return (String) input;
        } else if (input instanceof Map) {
            return new Gson().toJson(input);
        }
        throw new IllegalArgumentException("Cannot map input to JSON");
    }
}
