package pl.kania.extraction.util;

import pl.kania.dto.db.ExpenseType;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class TextEncodingMapper {

    public String mapToWindows1250(String text) {
        byte[] bytes = text.getBytes(Charset.forName("Windows-1250"));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
