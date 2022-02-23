package pl.kania.expensesCounter.commons.dto.db;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

import static java.util.Arrays.stream;

public enum MappingType {
    KEYWORD,
    INTERNET_ADDRESS;

    public static MappingType parse(String value) {
        return Optional.ofNullable(value)
                .map(v -> stream(MappingType.values())
                        .filter(mapping -> value.equalsIgnoreCase(mapping.name()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unknown mapping type: " + value))
                )
                .orElseThrow();
    }
}
