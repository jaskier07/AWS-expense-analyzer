package pl.kania.extraction.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperProvider {

    public ObjectMapper get() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }
}
