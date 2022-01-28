package pl.kania.extraction.handler;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;
import pl.kania.dto.BankType;

@Value
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ExpensesExtractionRequest {
    BankType bankType;
    String content;
}
