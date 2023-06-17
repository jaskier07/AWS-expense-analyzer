package pl.kania.expensesCounter.transactionToExpenseMapper.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class TextEncodingMapper {

    public String mapToWindows1250(String text) {
        byte[] bytes = text.getBytes(Charset.forName("Windows-1250"));
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
