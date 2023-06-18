package pl.kania.expensesCounter.accountStatementParser.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TextEncodingMapper {

    public String mapToWindows1250(String text) {
        byte[] bytes = text.getBytes(Charset.forName("Windows-1250"));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public String[] mapToWindows1250(String[] names) {
        return Arrays.stream(names)
                .map(this::mapToWindows1250)
                .toArray(String[]::new);
    }
}
